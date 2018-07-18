##Cinema application

This is simple basic application for provide some test task from Course SpringMVC #37

####Original task

1. Based on the codebase of previous hometasks, create a web application, configure Spring MVC application context and dispatcher servlet.

2. For all Booking operations implement Spring MVC annotation-based controllers.

3. For operations that return one or several entites as a result (e.g. getUserByEmail, getUsersByName, getBookedTickets) implement simple views rendered via Freemarker template engine. Use FreeMarkerViewResolver for view resolving procedure.

4. For operations, that return list of booked tickets (by event, or by user) implement alternative controllers, that will return result as PDF document. Map this controller to a specific value of Accept request header  - Accept=application/pdf

5. Implement batch loading of users and events into system. In order to do this, create controller which accepts multipart file upload, parses it and calls all Booking functionality methods to add events and users into the system. The format of the file (JSON, XML, ...) is up to you as long as you can implement the correct parsing procedure.

~~6. Implement generic exception handler which should redirect all controller exceptions to simple Freemarker view, that just prints exception message.~~

###Instruction

* Use Maven to build application and Tomcat for run it
* To configuration:
  - There must be installed and running H2 server
  - Configure connect to H2 in file **'jdbc.properties'**
  ```
    spring.datasource.url=jdbc:h2:tcp://localhost/~/H2/courseSCA37/spring.datasource
    ```
  - After each redeploy the DB will be recreated
  
   
* To install:
```bash
mvn clean install
```
```bash
mvn clean package
```
* **note:** During installation and testing, an error may occur. In the logs there is a characteristic record: 
    ```
    INFO  DiscountServiceImpl:45 - <<< YOU WINN!!! >>>
    ```  
    and the error as this:
    ```
    java.lang.AssertionError: expected:<29.99> but was:<0.00>
	at org.junit.Assert.fail(Assert.java:88)
	at org.junit.Assert.failNotEquals(Assert.java:834)
	at org.junit.Assert.assertEquals(Assert.java:118)
    ```

    **This is a random calculation of the probability of winning a ticket.
  In this case, it is necessary to repeat the test until the process will be finished without errors**
  
* The application will be packaged into a WAR file in 'target' folder

* The war-file will be named "ROOT.war"

* After upload this file to Tomcat and deploy the Application - They will be available by root '/'. For example:
    ```
    http://localhost:8089/
    ```

* All available section have the links and navigation

* For test upload date use the prepared files in project's root directory:
    ```
    test-upload-events.xml (not complited - changes will be present on DB)
    test-upload-users.xml
    ```
* The download data from application to XML did not ready, but it done on jUnit tests (and you can see it there)

###NOTE:
- The application did not make any stylistic changes.
- Error handling is not fully completed
- Import events are not finished (there is no schedule binding)

#####All details on: [SCA-37-task](https://github.com/ivanina-ilya/SCA-37-task)