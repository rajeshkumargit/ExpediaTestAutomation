package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.logging.Logger;

public class ExpediaCommon {
    static Logger logger = Logger.getLogger(ExpediaCommon.class.getName());

    private static WebElement element =null;


    public static WebElement logoExpedia(WebDriver driver){
        new WebDriverWait(driver, Duration.ofSeconds(30)).until(ExpectedConditions.visibilityOfElementLocated(By.className("large-logo")));
        element= driver.findElement(By.className("large-logo"));
        return element;
    }

    public static WebElement tabStays(WebDriver driver){
        new WebDriverWait(driver, Duration.ofSeconds(30)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='uitk-tabs-button-container']//span[text()='Stays']")));
        element= driver.findElement(By.xpath("//*[@id='uitk-tabs-button-container']//span[text()='Stays']"));
        return element;
    }
    public static WebElement tabFlight(WebDriver driver){
        new WebDriverWait(driver, Duration.ofSeconds(30)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='uitk-tabs-button-container']//span[text()='Flights']")));
        element= driver.findElement(By.xpath("//*[@id='uitk-tabs-button-container']//span[text()='Flights']"));
        return element;
    }

    public static WebElement btnSearch(WebDriver driver){
        element = driver.findElement(By.cssSelector("button[data-testid='submit-button']"));
        return element;
    }
}
