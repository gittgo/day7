

★ idea中是自动集成springBoot插件

★ eclipse 需要安装一个spring ide	

### SpringBoot的课程大纲(maven)

1. springBoot快速入门

​      @SpringBootApplication = @Configuration+@ComponentScan+@EnableAutoConfiguration

2. springBoot配置分析(属性和结构化)

​       a> 通过Enviroment 获取

​       b> 通过@Value获取

​	 ★> 配置文件的注意：

​		@PropertySource

​		@PropertySources

​		指定配置文件的名字

​		--spring.config.name=文件名称后缀名称可以省略

​		指定配置文件的目录

​		--spring.config.location=classpath:xxx/xxx.properties

​							    file:/

​	c>  通过@ConfigurationProperties(prefix="xx")

​		      @PropertySource("classpath:xxx.propeties")

​               ★ 注入普通属性

​		★注入集合或者数组

3. 按照条件自动配置

   Condition 接口

   	@Conditional


   ​	

4. springBoot @Enable*注解的工作原理

		@import

		importSelector

​        importBeanDefinitionRegistrar

​	注解装配监控器实现（BeanDefinitionProcessor回调）

5. springBoot @EnableAutoConfiguration项目应用

   1> 新建一个项目中需要提供配置类

   2> 在META-INF/spring.factorties在文件中配置

   ```
   org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
   第三方jar中提供配置类全路径
   ```

   ```
   @ConditionalOnClass 如果classpath下面存在Gson.class文件就装配该配置类的实例到spring容器中管理
   @ConditionalOnMissingBean 如果spring容器中不存在该bean对象就装配到spring容器管理
   ```

   3> 可以通过exclude或者excludeName方式排除不要被装配到spring容器中的类

6. SpringBoot事件监听

​       步骤:

​	1> 自定义事件一般是继承ApplicationEvent抽象类

​	2> 自定义事件监听器，一般是实现ApplicationListener接口

​	3> 配置监听器，启动的时候，需要把监听器加入到Spring容器中

​	4> 发布事件

​	配置监听器方式：

​	1> SpringApplication=>addListeners

​	2> 把监听器交给Spring容器管理

​	3> 使用配置项配置context.listener.classes = 监听器全路径

​	4> 使用@EventListener注解



7. SpringBoot Web（SpringMVC）



​	jsp配置

​	SpringBootServletInitializer 

​	spring.mvc.view.prefix=

​	spring.mvc.view.suffix=

​	tomcat-embed-jasper



​	freemarker配置

​	spring-boot-starter-freemarker

​	<#list personList as person>



​	thymeleaf配置

​	spring-boot-starter-thymeleaf

​	<html xmlns:th="http://www.thymeleaf.org">

​	th:object

​	th:each="person : ${personList}"

​	th:text



​	静态资源配置：

​	spring.resources.staticLocations=修改静态资源的路径



​	使用servletAPI

​	@ServletComponentScan

​	XxxRegistrationBean

​	ServeletListener



​	使用拦截器

​	配置类继承WebMvcConfigurationSupport=>addInterceptors



​	自定义错误页面处理：

```
默认读取的静态资源位置:"classpath:/META-INF/resources/", "classpath:/resources/",
"classpath:/static/", "classpath:/public/"
```

​	非模板： public/error/xxx.html

​	模板的:   templates/errror/xxx.html



​	异常处理：

​	局部@ExceptionHandler

​	全局@ControllerAdvice+@ExceptionHandler



8. springBoot的定制和优化内嵌的Tomcat

   实现 WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> 

   ConfigurableServletWebServerFactory 

   TomcatServletWebServerFactory 



9. SpringBoot JDBC/AOP

   jdbc

   JdbcTemplate（Spring jdbc）

   springBoot默认支持 数据源



​	AOP

​	启用aop加入starter-aop

1. 基于Spring的AOP写法

```
spring.aop.auto=true # 是否启用aop
spring.aop.proxy-target-class=false # 代理方式有接口使用jdk动态代理，如果没有接口使用cglib代理
```

2. 基于SpringBoot做法 @EnableAopProxyClass

```
exposeProxy属性表示如果使用true就可以使用AopContext对象获取当前代理对象，false则不能使用
proxyTargetClass true表示使用jdk的动态代理， false表示使用cglib代理
```



10. SpringBoot日志

- 简单配置实现

- springBoot默认的日志级别为info

  改变日志级别:

  ```
  --debug 这种对应springBoot内部日志设置生效，自定义日志不生效
  ```

  通过配置项: 

  ```
  
  日志级别有:ALL,TRACE,DEBUG,INFO,WARN,ERROR,FATAL(严重错误),OFF(关闭日志输出)
  
  loggin.file=  # 指定日志文件的位置和名称
  logging.path= # 指定日志文件的位置
  logging.pattern.console #配置控制台输出的日志pattern
  logging.file.connsole # 配置日志文件输出日志的pattern
  注意：springBoot的日志文件大于10M后就会分割。
  
  常规日志格式：
  %-20(%d{yyyy-MM-dd HH:mm:ss})  [%p]  %highlight(%C:%L)  : %m %n 高亮显示日志信息
  %d{HH:mm:ss.SSS} [boot-logging] [%t] %-5level %logger{30} - %msg%n 正常日志
  ```

  | 转换字符 | 表示的意思                                                   |
  | -------- | ------------------------------------------------------------ |
  | c        | 用于输出的记录事件的类别。例如，对于类别名称"a.b.c" 模式  %c{2} 会输出 "b.c" |
  | C        | 用于输出呼叫者发出日志请求的完全限定类名。例如，对于类名 "org.apache.xyz.SomeClass", 模式 %C{1} 会输出 "SomeClass". |
  | d        | 用于输出的记录事件的日期。例如， %d{HH:mm:ss,SSS} 或 %d{dd MMM yyyy HH:mm:ss,SSS}. |
  | F        | 用于输出被发出日志记录请求，其中的文件名                     |
  | l        | 用于将产生的日志事件调用者输出位置信息                       |
  | L        | 用于输出从被发出日志记录请求的行号                           |
  | m        | 用于输出使用日志事件相关联的应用程序提供的消息               |
  | M        | 用于输出发出日志请求所在的方法名称                           |
  | n        | 输出平台相关的行分隔符或文字                                 |
  | p        | 用于输出的记录事件的优先级                                   |
  | r        | 用于输出毫秒从布局的结构经过直到创建日志记录事件的数目       |
  | t        | 用于输出生成的日志记录事件的线程的名称                       |
  | x        | 用于与产生该日志事件的线程相关联输出的NDC（嵌套诊断上下文）  |
  | X        | 在X转换字符后面是键为的MDC。例如  X{clientIP} 将打印存储在MDC对键clientIP的信息 |
  | %        | 文字百分号 %%将打印％标志                                    |

- 手动配置日志

  - configuration

    scan:

    当此属性设置为true时，配置文件如果发生改变，将会被重新加载，默认值为true。

    scanPeriod:

    设置监测配置文件是否有修改的时间间隔，如果没有给出时间单位，默认单位是毫秒。当scan为true时，此属性生效。默认的时间间隔为1分钟。

    debug:

    当此属性设置为true时，将打印出logback内部日志信息，实时查看logback运行状态。默认值为false

  - logback

    ```
    <?xml version="1.0" encoding="UTF-8"?>
    <configuration debug="false">
        <!--定义日志文件的存储地址 勿在 LogBack 的配置中使用相对路径-->
        <property name="LOG_HOME" value="/home"/>
        <!-- 控制台输出 -->
        <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
            <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
                <!--格式化输出：%d表示日期，%thread表示线程名，%-5level：级别从左显示5个字符宽度%msg：日志消息，%n是换行符-->
                <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n</pattern>
            </encoder>
        </appender>
        <!-- 按照每天生成日志文件 -->
        <appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
            <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
                <!--日志文件输出的文件名-->
                <FileNamePattern>${LOG_HOME}/TestWeb.log.%d{yyyy-MM-dd}.log</FileNamePattern>
                <!--日志文件保留天数-->
                <MaxHistory>30</MaxHistory>
            </rollingPolicy>
            <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
                <!--格式化输出：%d表示日期，%thread表示线程名，%-5level：级别从左显示5个字符宽度%msg：日志消息，%n是换行符-->
                <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50} - %msg%n</pattern>
            </encoder>
            <!--日志文件最大的大小-->
            <triggeringPolicy class="ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy">
                <MaxFileSize>10MB</MaxFileSize>
            </triggeringPolicy>
        </appender>
    
        <!-- 日志输出级别 -->
        <root level="INFO">
            <appender-ref ref="STDOUT"/>
        </root>
    </configuration>
    ```

- log4j

  加入依赖

  ```
  <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-web</artifactId>
      <exclusions>
          <exclusion>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-logging</artifactId>
          </exclusion>
      </exclusions>
  </dependency>
  
  <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-log4j2</artifactId>
  </dependency>
  ```

  在classpath目录下面创建一个log4j2.xml文件,配置模板如下:

  ```
  <?xml version="1.0" encoding="UTF-8"?>
  <!-- 设置log4j2的自身log级别为warn -->
  <!-- OFF > FATAL > ERROR > WARN > INFO > DEBUG > TRACE > ALL -->
  <configuration status="WARN" monitorInterval="30">
      <appenders>
          <console name="Console" target="SYSTEM_OUT">
              <PatternLayout pattern="[%d{HH:mm:ss:SSS}] [%p] - %l - %m%n"/>
          </console>
      </appenders>
  
      <loggers>
          <root level="all">
              <appender-ref ref="Console"/>
          </root>
      </loggers>
  
  </configuration>
  ```

  项目配置

  ```
  <?xml version="1.0" encoding="UTF-8"?>
  <!-- 设置log4j2的自身log级别为warn -->
  <!-- OFF > FATAL > ERROR > WARN > INFO > DEBUG > TRACE > ALL -->
  <configuration status="WARN" monitorInterval="30">
      <appenders>
          <console name="Console" target="SYSTEM_OUT">
              <PatternLayout pattern="[%d{HH:mm:ss:SSS}] [%p] - %l - %m%n"/>
          </console>
   
          <RollingFile name="RollingFileInfo" fileName="${sys:user.home}/logs/info.log"
                       filePattern="${sys:user.home}/logs/$${date:yyyy-MM}/info-%d{yyyy-MM-dd}-%i.log">
              <!--控制台只输出level及以上级别的信息（onMatch），其他的直接拒绝（onMismatch）-->        
              <Filters>
                  <ThresholdFilter level="INFO"/>
                  <ThresholdFilter level="WARN" onMatch="DENY" onMismatch="NEUTRAL"/>
              </Filters>
              <PatternLayout pattern="[%d{HH:mm:ss:SSS}] [%p] - %l - %m%n"/>
              <Policies>
                  <TimeBasedTriggeringPolicy/>
                  <SizeBasedTriggeringPolicy size="100 MB"/>
              </Policies>
          </RollingFile>
   
          <RollingFile name="RollingFileWarn" fileName="${sys:user.home}/logs/warn.log"
                       filePattern="${sys:user.home}/logs/$${date:yyyy-MM}/warn-%d{yyyy-MM-dd}-%i.log">
              <Filters>
                  <ThresholdFilter level="WARN"/>
                  <ThresholdFilter level="ERROR" onMatch="DENY" onMismatch="NEUTRAL"/>
              </Filters>
              <PatternLayout pattern="[%d{HH:mm:ss:SSS}] [%p] - %l - %m%n"/>
              <Policies>
                  <TimeBasedTriggeringPolicy/>
                  <SizeBasedTriggeringPolicy size="100 MB"/>
              </Policies>
          </RollingFile>
   
          <RollingFile name="RollingFileError" fileName="${sys:user.home}/logs/error.log"
                       filePattern="${sys:user.home}/logs/$${date:yyyy-MM}/error-%d{yyyy-MM-dd}-%i.log">
              <ThresholdFilter level="ERROR"/>
              <PatternLayout pattern="[%d{HH:mm:ss:SSS}] [%p] - %l - %m%n"/>
              <Policies>
                  <TimeBasedTriggeringPolicy/>
                  <SizeBasedTriggeringPolicy size="100 MB"/>
              </Policies>
          </RollingFile>
   
      </appenders>
   
      <loggers>
          <!--过滤掉spring和mybatis的一些无用的DEBUG信息-->
          <logger name="org.springframework" level="INFO"></logger>
          <logger name="org.mybatis" level="INFO"></logger>
          <root level="all">
              <appender-ref ref="Console"/>
              <appender-ref ref="RollingFileInfo"/>
              <appender-ref ref="RollingFileWarn"/>
              <appender-ref ref="RollingFileError"/>
          </root>
      </loggers>
   
  </configuration>
  ```

  > https://blog.csdn.net/lu8000/article/details/25754415

11. **SpringBoot打包(项目部署的时候讲解)**

		非主流

		dependency:copy-dependencies
		
		java -Djava.ext.dirs=依赖位置 主类路径
		
		专业打包:
		
		appassembler-maven-plugin
		
		官网地址：<http://www.mojohaus.org/appassembler/appassembler-maven-plugin/> 

```
	<build>
		<plugins>
			<plugin>
				<groupId>org.codehaus.mojo</groupId>
				<artifactId>appassembler-maven-plugin</artifactId>
				<version>2.0.0</version>
				<configuration>
					<!-- 生成linux, windows两种平台的执行脚本 -->
					<platforms>
						<platform>unix</platform>
						<platform>windows</platform>
					</platforms>
					<!-- 包存放的根目录 -->
					<assembleDirectory>${project.build.directory}/${project.name}</assembleDirectory>
					<!-- 打包的jar，以及maven依赖的jar存放目录 -->
					<repositoryName>lib</repositoryName>
					<!-- lib目录中jar的存放规则，默认是${groupId}/${artifactId}的目录格式，flat表示直接把jar放到lib目录 -->
					<!-- 可执行脚本的存放目录 -->
					<binFolder>bin</binFolder>
					<!-- 配置文件的存放目录 -->
					<configurationDirectory>conf</configurationDirectory>
					<!-- 拷贝配置文件到上面的目录中 -->
					<copyConfigurationDirectory>tr7e</copyConfigurationDirectory>
					<!-- 从哪里拷贝配置文件 (默认src/main/config) -->
					<configurationSourceDirectory>src/main/resesources</configurationSourceDirectory>
					<configurationSourceDirectory>src/main/resources</configurationSourceDirectory>
					<repositoryLayout>flat</repositoryLayout>
					<encoding>UTF-8</encoding>
					<logsDirectory>logs</logsDirectory>
					<tempDirectory>tmp</tempDirectory>
					<programs>
						<program>
							<!-- 启动类 -->
							<mainClass>com.pengjunlee.MyApplication</mainClass>
							<jvmSettings>
								<extraArguments>
									<extraArgument>-server</extraArgument>
									<extraArgument>-Xmx1G</extraArgument>
									<extraArgument>-Xms1G</extraArgument>
								</extraArguments>
							</jvmSettings>
						</program>
					</programs>
				</configuration>
			</plugin>
		</plugins>
	</build>
```

appassembler:assemble 命令





### 项目应用

- **整合SSM框架应用**

  spring + springmvc + mybatis(普通方式，整合tk.mybatis,**项目时mybatis-plus**)

  - 启用devtools插件

    - settings-build-compiler->勾选build project autoxxx选项

    - shift+alt+ctrl+/ ->registry->勾选compiler.automake.allow.when.app.running 

    - 在springboot插件里做如下配置

      ```
      <build>
              <plugins>
                  <plugin>
                      <groupId>org.springframework.boot</groupId>
                      <artifactId>spring-boot-maven-plugin</artifactId>
                      <configuration>
                          <!--必须配置devtools-->
                          <fork>true</fork>
                      </configuration>
                  </plugin>
              </plugins>
          </build>
      ```

  - 启用lombok

    - settings-plugins-搜索lombok-安装lombok插件
    - @Data，@Setter,@Getter,.......

- **整合Dubbo**

1. 引入必须依赖

   ```
   <dependency>
       <groupId>com.alibaba.spring.boot</groupId>
       <artifactId>dubbo-spring-boot-starter</artifactId>
       <version>2.0.0</version>
   </dependency>
   
   <dependency>
       <groupId>com.101tec</groupId>
       <artifactId>zkclient</artifactId>
       <version>0.10</version>
   </dependency>
   
   <dependency>
       <groupId>org.apache.zookeeper</groupId>
       <artifactId>zookeeper</artifactId>
       <version>3.4.10</version>
       <exclusions>
           <exclusion>
               <groupId>org.slf4j</groupId>
               <artifactId>slf4j-log4j12</artifactId>
           </exclusion>
           <exclusion>
               <groupId>log4j</groupId>
               <artifactId>log4j</artifactId>
           </exclusion>
       </exclusions>
   </dependency>
   ```

2. 配置dubbo配置项

   ```
   spring.dubbo.application.name=dubbo-provider
   spring.dubbo.registry.address=zookeeper://127.0.0.1:2181
   spring.dubbo.protocol.name=dubbo
   spring.dubbo.protocol.port=20880
   ```

3. 启动类上面启用dubbo自动配置

   ```
   @EnableDubboConfiguration
   ```

- **整合Redis**

1. 引入依赖

```
<dependency>
	 <groupId>org.springframework.boot</groupId>
	 <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency> 
<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-pool2</artifactId>
    <version>2.4.2</version>
</dependency>
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>fastjson</artifactId>
    <version>1.2.8</version>
</dependency> 
```

2. redis的配置项

```
# Redis数据库索引（默认为0）
spring.redis.database=0
# Redis服务器地址
spring.redis.host=172.31.19.222
# Redis服务器连接端口
spring.redis.port=6379
# Redis服务器连接密码（默认为空）
spring.redis.password=
# 连接池最大连接数（使用负值表示没有限制）
spring.redis.pool.max-active=8
# 连接池最大阻塞等待时间（使用负值表示没有限制）
spring.redis.pool.max-wait=-1
# 连接池中的最大空闲连接
spring.redis.pool.max-idle=8
# 连接池中的最小空闲连接
spring.redis.pool.min-idle=0
# 连接超时时间（毫秒）
spring.redis.timeout=0
```

3. redis序列化器(默认的jackson)

```
	@Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory connectionFactory{
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<Object, Object>();
        redisTemplate.setConnectionFactory(connectionFactory);
        RedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        redisTemplate.setDefaultSerializer(genericJackson2JsonRedisSerializer);

        return redisTemplate;
    }
```

使用fastjson

```
自定义序列化器
public class FastJsonRedisSerializer<T> implements RedisSerializer<T> {
 
    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
    private Class<T> clazz;
 
    public FastJsonRedisSerializer(Class<T> clazz) {
        super();
        this.clazz = clazz;
    }
 
    @Override
    public byte[] serialize(T t) throws SerializationException {
        if (null == t) {
            return new byte[0];
        }
        return JSON.toJSONString(t, SerializerFeature.WriteClassName).getBytes(DEFAULT_CHARSET);
    }
 
    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if (null == bytes || bytes.length <= 0) {
            return null;
        }
        String str = new String(bytes, DEFAULT_CHARSET);
        return (T) JSON.parseObject(str, clazz);
    }
 
}

装配序列化器
@Bean
public RedisTemplate<Object, Object> redisTemplate(
	            RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<Object, Object> template = new RedisTemplate<>();
		//使用fastjson序列化
		FastJsonRedisSerializer fastJsonRedisSerializer = new FastJsonRedisSerializer(Object.class);
		// value值的序列化采用fastJsonRedisSerializer
		template.setValueSerializer(fastJsonRedisSerializer);
		template.setHashValueSerializer(fastJsonRedisSerializer);
		// key的序列化采用StringRedisSerializer
		template.setKeySerializer(new StringRedisSerializer());
		template.setHashKeySerializer(new StringRedisSerializer());
		template.setConnectionFactory(redisConnectionFactory);
		return template;
}
```

















**作业：(使用springBoot技术完成) JDBCTemplate**

使用restFul风格方式实现一个完整的用户管理系统

1、预习下如何操作数据库（mysql)

2、完成用户信息的增，删，改，分页条件查询。



交作业方式： 发老师QQ邮箱