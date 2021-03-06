# zooplus-assignment

## Project Description
This repo has the solutions to both the assignment questions. 

For the coding problem, the solution is done using Java and Selenium Webdriver and gradle to do the build management. TestNG is used as the test framework. The solution is designed to run on Windows, Linux and Mac platforms with Chrome as the test browser. However it can be easily configured to run with other browsers which can be found in more details in later sections.

I have used the Page Object model design pattern to design the tests. 

The repository has 3 main sections.
* <b>Framework:</b> This section manages common housekeeping like configuration management for the tests, common base page which contains reusable utility methods. It can also be used to house reusable utility classes that may be needed in future in different classes.

* <b>Data:</b> This section manages the test data that may be needed in various tests. For the assignment one such data is the Country and postcode to be used in the test. But we can think of data like customer data, address data etc which can be modularised in this section.

* <b>Page Objects:</b> This is the section where different page objects are represented for each page. Each page object contains the locator and the interaction methods for a given page.

<b>Note:</b> The test section also has a base class which needs to be extended by all the test classes. This base class has the configuration to initialise browser, control if the browser needs to run in headless or headed mode.

## Instructions to Run the Tests
After cloning the project the following commands can be run from the parent directory as per the Operating system.

<b><i>Note:</i></b> As part of the development of the script, I have tested the script to be running on Windows with Chrome browser. I have not been able to test the script with Mac or Linux. However, they should work. 

### Mac/Linux
<pre><code>./gradlew test</code></pre>

### Window 
<pre><code>.\gradlew.bat test</code></pre>

## Reports
The reports are generated under the build/reports/tests/test directory. The index.html can be opened in the browser for viewing the result.

## Miscellaneous

### Browser configuration
The browser configuration is mainly provided in the BaseTest class in the setup method. In this method we set which browser we want to run our test on and if we wish to run in headless mode. Based on these attributes, the WebDriverProvider class return a suitable WebDriver.

### Things which can be improved later
Given the time I have spent on the assignment, I have taken some conscious decisions to leave out certain things which can be improved later. They are as below. 

* A better test report integration (We can always integrate with third party report libraries like Allure)
* Better browser handling (For now I have implemented the chrome/firefox handling in WebDriverProvider. We can support more browsers)
* Screenshot for failed tests (We can implement automatic screenshots whenever a test fails)

## Manual Test Case Documentation
The manual tests in BDD style can be found under "src/test/resources" directory with file name "CheckoutOverview.feature"
