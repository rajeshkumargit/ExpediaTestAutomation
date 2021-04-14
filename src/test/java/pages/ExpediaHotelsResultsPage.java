package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.logging.Logger;

public class ExpediaHotelsResultsPage {
    static Logger logger = Logger.getLogger(ExpediaHotelsResultsPage.class.getName());

    private static WebElement element =null;

    public static void assertResultsPage(WebDriver driver){
       element = driver.findElement(By.xpath("//div[contains(text(),'What we are paid impacts our sort order')]"));
       Assert.assertTrue(element.isDisplayed(),"Assertion Error:results page not displayed");
    }

    public static WebElement firstHotelToSelect(WebDriver driver){
        try {
            element = driver.findElements(By.cssSelector(".listing__link.uitk-card-link")).get(0);
        }catch (Exception ex) {
            logger.info("Exception thrown while selecting first hotel from search results:" + ex);
            Assert.fail("Assert Error: Hotel results not fetched correctly");
        }
        return element;
    }


}
