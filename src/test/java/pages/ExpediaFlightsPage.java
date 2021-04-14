package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExpediaFlightsPage {
    static Logger logger = Logger.getLogger(ExpediaFlightsPage.class.getName());

    private static WebElement element =null;
    private static List<WebElement> elements=null;
    private static String btnFlightsListXPathStr="//ul[@class='uitk-typeahead-results no-bullet']/li/button//div/span/*[contains(text(),'%s')]/ancestor::button";


    public static WebElement inputFlightOrigin(WebDriver driver){
        element = driver.findElement(By.id("location-field-leg1-origin"));
        return element;
    }

    public static WebElement inputFlightDestination(WebDriver driver){
        element = driver.findElement(By.id("location-field-leg1-destination"));
        return element;
    }

    public static WebElement inputFlightFromClickable(WebDriver driver){
        element = driver.findElement(By.cssSelector("#location-field-leg1-origin-menu .uitk-faux-input"));
        return element;
    }

    public static WebElement inputFlightToClickable(WebDriver driver){
        element = driver.findElement(By.cssSelector("#location-field-leg1-destination-menu .uitk-faux-input"));
        return element;
    }


    public static WebElement btnFlightSelectFromList(WebDriver driver,String airport){
        element = driver.findElement(By.xpath(String.format(btnFlightsListXPathStr,airport)));
        return element;
    }

    public static WebElement btnSelectFlight(WebDriver driver){
        waitForPageLoaded(driver);
        element = driver.findElements(By.cssSelector("button[data-test-id='select-button']")).get(0);
        return element;
    }

    public static void waitForPageLoaded(WebDriver driver) {
        ExpectedCondition<Boolean> expectation = new
                ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
                    }
                };
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(expectation);
        } catch (Throwable error) {
            Assert.fail("Timeout waiting for Page Load Request to complete.");
        }
    }

    public static List<WebElement> btnSelectFlightFare(WebDriver driver){
        waitForPageLoaded(driver);
        List<WebElement> elements = driver.findElements(By.cssSelector("button[data-test-id='select-button-1']"));
        return elements;
    }

    public static WebElement textFlightBooking(WebDriver driver){
        waitForPageLoaded(driver);
        new WebDriverWait(driver, Duration.ofSeconds(30)).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".title-city-text")));
        element = driver.findElement(By.cssSelector(".title-city-text"));
        return element;
    }

    public static void assertTextOnFlightResultsPage(WebDriver driver,String text){
        element = textFlightBooking(driver);
        Assert.assertTrue(element.getText().contains(text),"Assert Error: Flight results doesn't contain the expected text:"+text);
    }


    public static void enterOrigin(WebDriver driver, String city) {
        try{
            WebElement flightFromEleClickable = inputFlightFromClickable(driver);
            logger.info("Entering origin in flight leaving from: "+city);
            new WebDriverWait(driver, Duration.ofSeconds(20)).until(ExpectedConditions.elementToBeClickable(inputFlightFromClickable(driver)));
            flightFromEleClickable.click();
            WebElement flightInputEle = inputFlightOrigin(driver);
            flightInputEle.sendKeys(city);
        }catch(NoSuchElementException ex){
            logger.log(Level.ALL,"Element not found or related",ex);
            Assert.fail("Could not enter flight origin data");
        }catch(Exception ex){
            logger.info("Exception thrown: "+ex);
            Assert.fail("Exception thrown while trying to enter flight origin");
        }
    }

    public static void enterDestination(WebDriver driver, String city) {
        try{
            WebElement flightToEleClickable = inputFlightToClickable(driver);
            logger.info("Entering origin in flight leaving from: "+city);
            new WebDriverWait(driver, Duration.ofSeconds(20)).until(ExpectedConditions.elementToBeClickable(inputFlightToClickable(driver)));
            flightToEleClickable.click();
            WebElement flightInputEle = inputFlightDestination(driver);
            flightInputEle.sendKeys(city);
        }catch(NoSuchElementException ex){
            logger.log(Level.ALL,"Element not found or related",ex);
            Assert.fail("Could not enter flight destination data");
        }catch(Exception ex){
            logger.info("Exception thrown: "+ex);
            Assert.fail("Exception thrown while trying to enter flight destination");
        }
    }

    public static void selectAirportFromList(WebDriver driver,String airport){
        try{
            WebElement element = btnFlightSelectFromList(driver,airport);
            element.click();
        }catch(NoSuchElementException ex){
            logger.log(Level.ALL,"Element not found or related",ex);
            Assert.fail("Could not select flight item from list of flights");
        }catch(Exception ex){
            logger.info("Exception thrown: "+ex);
            Assert.fail("Exception thrown while trying to click on flight item from list of flights");
        }
    }

    public static void clickOnSelectBtn(WebDriver driver){

        try{
            WebElement element = btnSelectFlight(driver);;
            element.click();
            if(btnSelectFlightFare(driver).size()>0){
                if(btnSelectFlightFare(driver).get(0).isDisplayed()) {
                    new WebDriverWait(driver, Duration.ofSeconds(20)).until(ExpectedConditions.elementToBeClickable(btnSelectFlightFare(driver).get(0)));
                    btnSelectFlightFare(driver).get(0).click();
                }
            }
        }catch(NoSuchElementException ex){
            logger.log(Level.ALL,"Element not found or related",ex);
            Assert.fail("Could not select flight");
        }catch(Exception ex){
            logger.info("Exception thrown: "+ex);
            Assert.fail("Exception thrown while trying to click on flight Select btn");
        }
    }
}
