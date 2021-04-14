# Expedia Test Automation
Selenium TestNG automation suite and framework for executing flight and hotel search scenarios in Expedia.com.au
This project is data driven and page object model compliant

## Packages

src/test/java/helper -> For helper methods
src/test/java/pages -> page objects separation
src/test/java/tests -> test methods driven by TestNG
src/test/resources/config -> configuration details
src/test/resources/data -> csv data kept here for multiple iterations
src/test/resources/driver -> Chrome and Firefox Gecko driver executables

## TestNG.xml

TestNG provides structure and test suite xml for executing and integrating with CI platforms.
TestNG.xml in the project context is where the testsuite and test class details are provided

## Test Data
Test data iterations could be found in the form of csv files in the directory src/test/resources/data

## Config

browser config: firefox or chrome could be updated here: src/test/resources/config
Also date format followed in the test methods is referrred here.
headless only for firefox. not working in Chrome
''' headless works only for Firefox'''

## Execution
It's bundled with maven for dependency management. Tests can be executed through testng from IDE
or by entering mvn command from your command line

```bash
mvn clean test
```

## Results

Results are generated in \target\surefire-reports

## Java
Tested in Java 1.8
