Word Ladder
===========
_Author:_ Higher Stark

### Brief Introduction
This Repository is a web providing word ladder service.  
Framework: Spring Boot, Maven, Thymeleaf  
Utilities: Log4j

### Progress
* v0.2
An embedded auth implemented.
Interceptor not completed.

* v0.1  
    实现前端登录界面和 Word Ladder 服务界面，后台利用 ModelAndView 实现页面跳转和数据动态注入  
    TODO -\> 前端登录加密传输，后台拦截Word Ladder服务，验证是否登录

#### Problem encountered
* Multiple session repository candidates are available
    Specify the `spring.session.store-type` in application.properties
    
* Log Configuration  
    To log in Spring, referencing the following website
    https://blog.csdn.net/xiejx618/article/details/41698913 .  
    A nice configuration for Log4j - https://blog.csdn.net/qq_27093465/article/details/62928590 .
    
    To decoupling, it's more elegant to configure log properties in the xml file.
* Resource file access  
    To access dictionary-1.txt file under src/main/resources/static directory, 
    take advantage of `org.springframework.core.io.ClassPathResource` to fetch the target file.
    
    Take advantage of XML file and Java Bean to set dictionary file path is more elegant in my option.
* Thymeleaf  
    ${...} is more likely to refer to variables, 
    \#{...} is more likely to refer to constants.