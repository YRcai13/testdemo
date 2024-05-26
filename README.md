# 写在前面

demo链接：https://github.com/YRcai13/testdemo

# 需求

搭建一个基于maven的springcloud工程，包含网关模块、用户模块，服务模块。
实现功能：
1、登录接口（需要用户表、密码校验、数据来源不限制）
2、用户信息查询：写在服务模块，服务模块feign调用用户模块的接口得到结果
接口都需要经过网关鉴权

注册中心使用本地consul，会话保存在redis，数据库用mysql。

每个模块对应分工：

- 网关模块：负责路由转发，白名单维护，验证令牌以及鉴权
- 认证模块：负责登录认证，颁发令牌
- 用户模块：用户管理（增删改查）
- 服务模块：feign远程调用用户模块的接口
- 通用模块：通用响应类，全局异常处理

# 思路

认证模块：spring security + jwt + redis 实现

redis可以解决jwt注销问题，但旧的token仍可以使用问题

用户访问每个接口时，都需要经过网关鉴权。登录认证成功后，用户的权限存放在redis。后续网关通过userId访问redis获取权限列表。

网关鉴权：在网关层面编写过滤器，将用户对应授权的 url 全部查出，再和当前访问的路径做匹配操作鉴定权限。

微服务鉴权：对请求的授权操作在各个微服务进行，可做到细粒度分配接口权限，但每个微服务都需要引入springsecurity依赖和配置。

## 总体流程

1. 用户首次登录输入账号密码提交认证，认证通过后获取令牌，继续后续操作。
2. 用户携带令牌去访问资源服务，需要经过网关对jwt进行身份认证，用户权限鉴定，通过后进而访问资源。

![image-20240526175230535](C:\Users\caiyu\AppData\Roaming\Typora\typora-user-images\image-20240526175230535.png)

## 登录校验流程

![image-20240526171451364](C:\Users\caiyu\AppData\Roaming\Typora\typora-user-images\image-20240526171451364.png)

# spring security认证

参考链接：https://www.cnblogs.com/zlaoyao/p/16588950.html

SpringSecurity的原理其实就是一个过滤器链，内部包含了提供各种功能的过滤器。

![image-20240526204209374](C:\Users\caiyu\AppData\Roaming\Typora\typora-user-images\image-20240526204209374.png)

**UsernamePasswordAuthenticationFilter**:负责处理在登陆页面填写了用户名密码后的登陆请求，主要负责认证工作。

**ExceptionTranslationFilter：**处理过滤器链中抛出的任何AccessDeniedException和AuthenticationException 。

**FilterSecurityInterceptor：**负责权限校验的过滤器。

## 认证流程详解

![image-20240526204235907](C:\Users\caiyu\AppData\Roaming\Typora\typora-user-images\image-20240526204235907.png)

我们可以实现 UserDetailsService 接口来重写 loadUserByUsername 方法，将查内存的方式转为查数据库的方式。

## 思路分析

登录功能：

1.  自定义登录接口：AuthenticationManager authenticate进行用户认证， 如果认证通过生成jwt，并把用户信息存入redis中。
2.  自定义UserDetailsService：在这个实现类中去查询数据库

校验功能：

1. 定义Jwt认证过滤器： 获取token， 解析token获取其中的userid， 从redis中获取用户信息， 存入SecurityContextHolder

## 授权基本流程

在 SpringSecurity 中，会使用默认的 FilterSecurityInterceptor 来进行**权限校验**。在 FilterSecurityInterceptor 中会从 SecurityContextHolder 获取其中的 Authentication，然后获取其中的权限信息，判断当前用户是否拥有访问当前资源所需的权限。

所以只需要把当前登录用户的权限信息存入Authentication中，然后在接口处设置资源所需要的权限即可。

```java
UsernamePasswordAuthenticationToken authenticationToken =
       new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
SecurityContextHolder.getContext().setAuthentication(authenticationToken);
```

```java
@PreAuthorize("hasAuthority('system:test:list')")
```

# 遇到的问题

问题：spring-cloud-consul：配置文件中设置servlet context-path 导致 Consul 健康检查失败

原因：consul对微服务的健康检查路径是固定的。默认为 /actuator/health，没有自动加上 servlet.context-path中设置的 /config 作为前缀（应该算是一个小缺陷）。也就是说：/api/user/actuator/health 才是正确的！

解决办法：修改 consul 的 health-check-path：

```yml
spring:
  cloud:
    consul:
      discovery:
        health-check-path: ${server.servlet.context-path}/actuator/health
```

------

问题：找不到类型为RedisTemplate的bean

原因：没有找到RedisTemplate< String, Object>可以匹配的Bean

解决方法：1、在使用RedisTemplate< K, V>时不指定具体的类型；2、使用@Resource注解（官网有说明）

![image-20240519125749359](C:\Users\caiyu\AppData\Roaming\Typora\typora-user-images\image-20240519125749359.png)

参考链接：https://www.cnblogs.com/muxi0407/p/11800999.html

------

问题：user-service服务需要主动开启心跳机制，否则无法通过健康检查？会定时检查

------

问题：spring security接口自定义logout失效？

原因：spring security定义且推荐官方logout接口，而如果要自定义logout接口，需要用其它名字，如mylogout

![image-20240520145020828](C:\Users\caiyu\AppData\Roaming\Typora\typora-user-images\image-20240520145020828.png)

------

问题：实体类反序列化失败

原因：不同包下的同类，全类名是不同的。

解决方法：统一放到通用类中，或者导入依赖

------

问题：redis报错： WRONGTYPE Operation against a key holding the wrong kind of value

原因：翻译：key的类型不同，无法接收。原因是我修改了存放到redis上的key类型，从string到list，但是redis的key还是原来的string，而我用list来接收，就报错了。

------

问题：gateway与spring-boot-starter-web 依赖冲突。

原因：传统的spring-boot-starter-web，它的架构组合【spring-webmvc + Servlet + Tomcat】命令式的、同步阻塞的，响应式spring-webflux框架，它的架构组合【spring-webflux + Reactor + Netty】响应式的、异步非阻塞的。在一个web项目中存在基于两个不同的编程框架，互相冲突，从而导致项目启动报错。

解决方法：配置文件加 spring: main: web-application-type: reactive