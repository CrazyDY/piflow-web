![](https://github.com/cas-bigdatalab/piflow/blob/master/doc/piflow-logo2.png) 
## Requirements
* JDK 1.8 or newer
* Apache Maven 3.1.0 or newer
* Apache Tomcat 8.5 or newer
## Getting Started
- `how to configure application.yml`

server:
  port: 7001
  servlet:
    context-path: /piflow-web
    session:
      timeout: 600
syspara:
  imagesPath: /piflow/image/
  videosPath: /piflow/video/
  xmlPath: /piflow/xml/
  interfaceUrlHead : http://10.0.88.108:8002
spring:
########################################################
######  数据源
########################################################
  datasource:
    url: jdbc:mysql://10.0.88.109:3306/piflow_web?useUnicode=true&characterEncoding=UTF-8&useSSL=false&allowMultiQueries=true
    username: root
    password: 123456
    driver-class-name: com.mysql.jdbc.Driver
    #type: com.alibaba.druid.pool.DruidDataSource
########################################################
######  模板引擎thymeleaf
########################################################
  thymeleaf:
    prefix: classpath:/templates/
    suffix: .html
    mode: HTML5
    encoding: UTF-8
#热部署文件，页面不产生缓存，及时更新
    cache:  false
  resources:
    chain:
      strategy:
        content:
          enabled: true
          paths: /**
########################################################
######  配置静态资源
########################################################
  mvc:
    view:
      prefix: /templates/
      suffix: .html
    static-path-pattern: /**
########################################################
######  配置自动建表：updata: 没有表新建，有表更新操作,控制台显示建表语句
########################################################
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    open-in-view: true
#Springboot中Hibernate默认创建的mysql表为myisam引擎,因不支持事物所以指定为：innodb
    database-platform: org.hibernate.dialect.MySQL5InnoDBDialect

########################################################
### securityconfig
########################################################
#  security:
#    user:
#      name: soso
#      password: 1
########################################################
### 整合mybatis
########################################################
mybatis:
  type-aliases-package: com.nature.**.model

  configuration:
    #开启驼峰映射
    map-underscore-to-camel-case: true
    #开启懒加载
    #全局性设置懒加载。如果设为‘false'，则所有相关联的都会被初始化加载。
    lazyLoadingEnabled: true
    #当设置为‘true'的时候，懒加载的对象可能被任何懒属性全部加载。否则，每个属性都按需加载。
    aggressive-lazy-loading: false

#=========== 日志配置·简易（spring boot已经集成logback日志）=========
#controller层日志 WARN级别输出
#logging.level.com.liyan.controller=WARN
#mapper层 sql日志 DEBUG级别输出
#logging.level.com.liyan.mapper=DEBUG
#logging.file=logs/spring-boot-logging.log
#logging.pattern.console=%d{yyyy/MM/dd-HH:mm:ss} [%thread] %-5level %logger- %msg%n
#logging.pattern.file=%d{yyyy/MM/dd-HH:mm} [%thread] %-5level %logger- %msg%n
#打印运行时sql语句到控制台
#spring.jpa.show-sql=true

#==================== 日志配合·标准  ============================
logging:
  level:
    com.nature.mapper : debug
    root: info
    org:
      springframework:
        security: info
#==================== 日志配合·XML  ============================
#logging:
#  config: classpath:log4j2.xml


