# Contributor

## Description
Contributor is a project that primarily serves to practice and improve technology. I tried to use all the technologies I know, but also the ones I am just learning.  

The concept is based on the fact that developers can share their idea for an application or site.   
They could then connect with people who are interested in joining and supporting the idea.  
**It is allowed** to change and refine everything because the goal is learning.   
*Any suggestions or help are welcome.*

## Table of Contents
[Installation](#installation)  
[Database configuration](#database)  
[Dependencies](#dependencies)   
[Screenshots](#screenshots)   
[Contributing](#contributing)  
[Licence](#licence)  

<a name="installation" />  

## Instalation (Running Application Locally)   

git clone https://github.com/cfex/contributor.git  
cd contributor  
./mnw package  
java -jar target/*.jar  
  
or with Maven:  
./mvnw spring-boot:run  

**You can then access Contributor on:** http://localhost:8080/   
*Index page*  
![screencapture-localhost-8080-2021-06-06-17_52_10](https://user-images.githubusercontent.com/24775798/121085022-a3867880-c7e1-11eb-83fb-aab3ea7d6843.png)  

<a name="database" />  

## Database configuration:  
The application is configured to use MySQL when the default profile is active, and the h2 for the development profile.  
MySQL Database (application.yml):  
```yml  
datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/contributordb?useSSL=false&allowPublicKeyRetrieval=true
    username: #db_username
    password: #db_password
    sql-script-encoding: UTF-8
    tomcat:
      connection-properties: useUnicode=true;characterEncoding=utf-8;
    platform: org.hibernate.dialect.MySQL5InnoDBDialect
```  
or H2 (application-dev.yml):  
```yml
h2:
    console:
      enabled: true
      path: /h2
      settings:
        trace: false
        web-allow-others: false
  datasource:
    url: jdbc:h2:mem:testdb
    username: sa
    password:
    driver-class-name: org.h2.Driver
```  

<a name="dependencies" />   

## Dependencies  
- Java 11  
- Spring Boot 2.4.4  
- Spring Data  
- Spring MVC
- Spring Security  
- Thymeleaf  
- Slf4j
- ModelMapper
- Lombok
- H2/MySQL  

<a name="screenshots" />  

## Screenshots

![screencapture-localhost-8080-auth-registration-2021-06-06-17_52_35](https://user-images.githubusercontent.com/24775798/121087817-307f0100-c7e5-11eb-8750-5a72f6216522.png)
![screencapture-localhost-8080-auth-login-2021-06-06-17_52_52](https://user-images.githubusercontent.com/24775798/121087870-3d9bf000-c7e5-11eb-9aee-3398a2e6621e.png)
![screencapture-localhost-8080-users-me-2021-06-06-17_53_35](https://user-images.githubusercontent.com/24775798/121087904-4a204880-c7e5-11eb-800d-175a5998d0b1.png)
![screencapture-localhost-8080-projects-dOjDUENMhyh6LsuvBb0SX3TXZRUCy8-edit-2021-06-06-17_53_48](https://user-images.githubusercontent.com/24775798/121087950-5b695500-c7e5-11eb-927b-58f111890826.png)
![screencapture-localhost-8080-projects-5lIMtYuwc2yfhVSm1BCn0XNqPfF3uT-details-2021-06-06-17_54_41](https://user-images.githubusercontent.com/24775798/121087966-602e0900-c7e5-11eb-888e-06abfa4e7933.png)
![screencapture-localhost-8080-projects-create-2021-06-06-17_55_28](https://user-images.githubusercontent.com/24775798/121087973-62906300-c7e5-11eb-83cc-18de0993a295.png)
![screencapture-localhost-8080-users-6pbIo6H7PSMLmpl3dBsAUXmX01jqUf-profile-2021-06-06-20_48_50 (1)](https://user-images.githubusercontent.com/24775798/121088034-73d96f80-c7e5-11eb-9128-625428f3bab3.png)
![screencapture-localhost-8080-projects-explore-2021-06-06-20_49_24](https://user-images.githubusercontent.com/24775798/121088098-85bb1280-c7e5-11eb-9a7c-c2c365891cfa.png)  

<a name="contributing" />  

## Contributing  

The [issue tracker](https://github.com/cfex/contributor/issues) is the preferred channel for bug reports, features requests and submitting pull requests.  
*Fell free to post anything.*

<a name="licence" />  

## Licence
Released under MIT Licence


