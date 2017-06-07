# 注意
* 引入example-spring-boot-starter依赖

```
 <dependency>
    <groupId>com.yangyang</groupId>
    <artifactId>example-spring-boot-starter</artifactId>
    <version>0.0.1-SNAPSHOT</version>
 </dependency>
```
* 创建application.properties，进行配置

```
example.service.enabled=true
example.service.prefix=####
example.service.suffix=@@@@
```

* 访问测试
http://localhost:8080/input?word=%E6%B5%8B%E8%AF%95