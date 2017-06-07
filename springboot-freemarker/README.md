# 1.spring boot使用mybatis连接mysql数据库
## 1.1.springboot引用关于mybatis的包

```
        <!-- Spring Boot Mybatis 依赖 -->
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>1.2.0</version>
        </dependency>

        <!-- MySQL 连接驱动依赖 -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.39</version>
        </dependency>
```
## 1.2 在application.properties中配置
```
## Mybatis 配置
mybatis.typeAliasesPackage=com.yangyang.springboot.domain
mybatis.mapperLocations=classpath:mapper/*.xml


## 数据源配置
spring.datasource.url=jdbc:mysql://localhost:3306/yangyang?useUnicode=true&characterEncoding=utf8&useSSL=false
spring.datasource.username=root
spring.datasource.password=123456
spring.datasource.driver-class-name=com.mysql.jdbc.Driver

```

## 1.3 在具体的mapper上添加@Mapper注解

# 2.spring boot使用freemarker

## 2.1 添加jar
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-freemarker</artifactId>
 </dependency>

```

## 2.2配置
在application.properties中配置
```
## freemarker相关配置
# 页面默认前缀目录
spring.mvc.view.prefix=/templates
spring.resources.static-locations=classpath:/static/
# 响应页面默认后缀
spring.mvc.view.suffix=.ftl
```

