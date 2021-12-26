#Derivate Measures

### Used tool in project :
- JDK 11
- Log4j
- Gson
- Lombok
- Spring Boot
- Swagger
- Opencsv

### Project Structure :
####Java Packages :
- com.derivatemeasure.portal.Business(Calculate the requirement)
- com.derivatemeasure.portal.Constant(Keep constant value)
- com.derivatemeasure.portal.Controller(Rest API call)
- com.derivatemeasure.portal.DerivateMeasures(Spring boot main class)
- com.derivatemeasure.portal.Listener(Start one time task)
- com.derivatemeasure.portal.Model(Java object fields)
- com.derivatemeasure.portal.Schedular(Schedule task)
- com.derivatemeasure.portal.Util(Project utility)

### Java Resource :
- application.properties(Configuration of project)
- banner.txt(Project startup logo)
- logback.xml(Maintain project logs)

### Input CSV Data
Read CSV data using input and output stream and convert
into java object and perform business requirement and
exposed API.

### Test :
Performed unit test every functionality and handle with
exception handling and test every rest API.