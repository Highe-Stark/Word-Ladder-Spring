Word Ladder
===========
_Author:_ Higher Stark

### Brief Introduction
This Repository is a web providing word ladder service.  
Framework: Spring Boot, Maven, Thymeleaf  
Utilities: Log4j

#### Problem encountered
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