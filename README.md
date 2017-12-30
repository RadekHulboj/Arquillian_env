# first theory

# Arquillian makes integration testing a breeze!

Arquillian brings the test to the runtime so you don&#39;t have to manage the runtime from the test (or the build). Arquillian eliminates this burden by covering all aspects of test execution, which entails:

- .Managing the lifecycle of the container (or containers)
- .Bundling the test case, dependent classes and resources into a ShrinkWrap archive (or archives)
- .Deploying the archive (or archives) to the container (or containers)
- .Enriching the test case by providing dependency injection and other declarative services
- .Executing the tests inside (or against) the container
- .Capturing the results and returning them to the test runner for reporting

To avoid introducing unnecessary complexity into the developer&#39;s build environment, Arquillian integrates seamlessly with familiar testing frameworks (e.g., JUnit 4, TestNG 5), allowing tests to be launched using existing IDE, Ant and Maven test plugins — without any add-ons.

[http://arquillian.org/](http://arquillian.org/)

# some practice


**git clone https://github.com/RadekHulboj/MBO_Axxiome.git** 

The project is built with maven build tool: run command **mvn clean package**

The tests are self-explanatory.
