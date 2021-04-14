package pages;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExpediaStaysPage {
    static Logger logger = Logger.getLogger(ExpediaStaysPage.class.getName());

    private static WebElement element =null;
    private static String btnStaysLocationXPathStr="//ul[@class='uitk-typeahead-results no-bullet']/li/button//div/span/*[text()='%s']" +
            "/ancestor::button//div[@class='is-subText truncate' and text()='%s' ]/ancestor::button";
    private static String btnFlightsListXPathStr="//ul[@class='uitk-typeahead-results no-bullet']/li/button//div/span/*[contains(text(),'%s')]/ancestor::button";
    private static String btnDateXpathStr = "//h2[@class='uitk-date-picker-month-name uitk-type-medium' and text()='%s']";
    private static String btnDayOfMonthDateCtrlXpathStr="//parent::div//button[contains(@class,'uitk-date-picker-day') and @data-day='%s']";

    public static WebElement inputStaysLocation(WebDriver driver){
        element = driver.findElement(By.id("location-field-destination"));
        return element;
    }

    public static WebElement inputStaysLocationClickable(WebDriver driver){
        element = driver.findElement(By.cssSelector(".has-no-placeholder > .uitk-faux-input"));
        return element;
    }

    public static WebElement btnCheckIn(WebDriver driver){
        element =driver.findElement(By.id("d1-btn"));
        return element;
    }

    public static List<WebElement> textDivMonthDateElement(WebDriver driver,String monthYear){
        List<WebElement> elements = driver.findElements(By.xpath(String.format(btnDateXpathStr,monthYear)));
        return elements;
    }

    public static WebElement btnCheckOut(WebDriver driver){
        element =driver.findElement(By.id("d2-btn"));
        return element;
    }

    public static WebElement btnDone(WebDriver driver){
        element =driver.findElement(By.xpath("//button[@data-stid='apply-date-picker']"));
        return element;
    }


    public static WebElement btnListStaysLocation(WebDriver driver,String destination,String subtext){
        element = driver.findElement(By.xpath(String.format(btnStaysLocationXPathStr,destination,subtext)));
        return element;
    }

    public static WebElement btnDayOfMonth(WebDriver driver,String monthYear,String day){
        element = driver.findElement(By.xpath(String.format(btnDateXpathStr,monthYear)+String.format(btnDayOfMonthDateCtrlXpathStr,day)));
        return element;
    }

    public static WebElement btnDatePickerPaging(WebDriver driver){
        element = driver.findElements(By.cssSelector("button[data-stid='date-picker-paging']")).get(1);
        return element;
    }

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

    public static void enterStaysDestination(WebDriver driver,String destination){
        try{
            WebElement staysInputClickableEle = inputStaysLocationClickable(driver);
            logger.info("Entering destination in stays location element: "+destination);
            new WebDriverWait(driver, Duration.ofSeconds(20)).until(ExpectedConditions.elementToBeClickable(staysInputClickableEle));
            staysInputClickableEle.click();
            WebElement staysInputEle = inputStaysLocation(driver);
            staysInputEle.sendKeys(destination);
        }catch(NoSuchElementException ex){
            logger.log(Level.ALL,"Element not found or related",ex);
            Assert.fail("Could not enter destination data in stays location element");
        }catch(Exception ex){
            logger.info("Exception thrown: "+ex);
            Assert.fail("Exception thrown while trying to enter destination data in stays location element");
        }
    }

    public static void selectStaysLocationFromList(WebDriver driver,String destination,String subtext){
        try{
            WebElement element = btnListStaysLocation(driver,destination,subtext);
            logger.info("Entering destination in stays location element: "+destination);
            element.click();
        }catch(NoSuchElementException ex){
            logger.log(Level.ALL,"Element not found or related",ex);
            Assert.fail("Could not select destination list item in stays location select element");
        }catch(Exception ex){
            logger.info("Exception thrown: "+ex);
            Assert.fail("Exception thrown while trying to click on destination data in stays select list item element");
        }
    }

    public static void clickOnCheckInDate(WebDriver driver){
        btnCheckIn(driver).click();
    }

    public static void clickOnCheckOutDate(WebDriver driver){
        btnCheckOut(driver).click();
    }

    public static void clickOnDayOfMonth(WebDriver driver,String monthYear,String day){
        btnDayOfMonth(driver,monthYear,day).click();
    }

    public static void selectDateFromCalendar(WebDriver driver,String dateToEnter,String dateFormat,String fromTo){
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);

            LocalDate dateTime = LocalDate.parse(dateToEnter, formatter);
            String month = StringUtils.capitalize(String.valueOf(dateTime.getMonth()).toLowerCase());
            String year = String.valueOf(dateTime.getYear());
            String day = String.valueOf(dateTime.getDayOfMonth());
            if(fromTo.equalsIgnoreCase("from")) {
                clickOnCheckInDate(driver);
            }else{
                clickOnCheckOutDate(driver);
            }
            boolean dateSelectedFlag=false;
            driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            while(!dateSelectedFlag) {
                List<WebElement> monthContentCalendarDiv = textDivMonthDateElement(driver, month + " " + year);
                if (monthContentCalendarDiv.size() > 0) {
                    clickOnDayOfMonth(driver, month + " " + year, day);
                    dateSelectedFlag = true;
                    driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
                } else {
                    WebElement btnpickerPaging = btnDatePickerPaging(driver);
                    if (btnpickerPaging.isEnabled()) {
                        try {
                            btnpickerPaging.click();
                        } catch (Exception ex) {
                            logger.info("Exception thrown while clicking on btn picker paging element:" + ex);
                            Assert.fail("thrown while clicking on btn picker paging element");
                        }
                    } else if (!btnpickerPaging.isEnabled()) {
                        Assert.fail("please correct date data as we reached the maximum limit for reservation");
                        return;
                    }
                }
            }
        }catch(NoSuchElementException ex){
            logger.log(Level.ALL,"Element not found",ex);
            Assert.fail("Could not select date from calendar object");
        }catch(Exception ex){
            logger.info("Exception thrown: "+ex);
            Assert.fail("Exception thrown while trying to select date from calendar");
        }
    }

    public static void enterOrigin(WebDriver driver, String origin) {
        try{
            WebElement staysInputClickableEle = inputStaysLocationClickable(driver);
            logger.info("Entering origin in flight leaving from: "+origin);
            new WebDriverWait(driver, Duration.ofSeconds(20)).until(ExpectedConditions.elementToBeClickable(staysInputClickableEle));
            staysInputClickableEle.click();
            WebElement staysInputEle = inputStaysLocation(driver);
            staysInputEle.sendKeys(origin);
        }catch(NoSuchElementException ex){
            logger.log(Level.ALL,"Element not found or related",ex);
            Assert.fail("Could not enter destination data in stays location element");
        }catch(Exception ex){
            logger.info("Exception thrown: "+ex);
            Assert.fail("Exception thrown while trying to enter destination data in stays location element");
        }
    }

}
