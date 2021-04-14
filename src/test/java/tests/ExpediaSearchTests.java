package tests;

import helper.CSVReader;
import helper.ConfigReader;
import helper.FlightSearchData;
import helper.StaysSearchData;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ExpediaCommon;
import pages.ExpediaFlightsPage;
import pages.ExpediaHotelsResultsPage;
import pages.ExpediaStaysPage;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class ExpediaSearchTests {

	static Logger logger = Logger.getLogger(ExpediaSearchTests.class.getName());

	private WebDriver driver;
	private String baseURL;
	private String browser;
	private String dateFormat;
	private String headless;


	@BeforeMethod
	public void beforeMethod() {
		baseURL= "https://www.expedia.com.au";
		System.setProperty("webdriver.gecko.driver", "src/test/resources/driver/geckodriver.exe");
		System.setProperty("webdriver.chrome.driver", "src/test/resources/driver/chromedriver.exe");
		ConfigReader reader = new ConfigReader();
		dateFormat=reader.getProperty("date.format");
		headless=reader.getProperty("headless");
		browser = reader.getProperty("test.browser");
		if(browser.equalsIgnoreCase("firefox")){
			if(headless.equalsIgnoreCase("true")){
				FirefoxOptions options = new FirefoxOptions();
				options.addArguments("--headless");
				driver=new FirefoxDriver(options);
			}else {
				driver = new FirefoxDriver();
			}
		}else{
			if(headless.equalsIgnoreCase("true")){
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--headless");
				options.addArguments("--disable-gpu");
				options.addArguments("--window-size=1920,1200");
				driver=new ChromeDriver(options);
			}else {
				driver = new ChromeDriver();
			}
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}

	@AfterMethod
	public void afterTest() {
		driver.close();
	}

	@Test(dataProvider = "hotelData", dataProviderClass = CSVReader.class)
  public void TestHotelSearchTestCase(StaysSearchData hotelData) {
  	  String destination = hotelData.getDestination();
  	  String subtextDestination=hotelData.getDestinationSubText();
  	  String fromDate =	hotelData.getFrom();
  	  String toDate = hotelData.getTo();
	  driver.get(baseURL);
//	  Assert.assertEquals(ExpediaCommon.logoExpedia(driver).isEnabled(),true,"Error: Expedia logo didn't load or not displayed");
	  ExpediaCommon.tabStays(driver).click();
	 // Assert.assertEquals(ExpediaStaysPage.inputStaysLocation(driver).isEnabled(),true,"Error: Expedia Stays text box didn't load or not displayed");
	  ExpediaStaysPage.enterStaysDestination(driver,destination);
	  ExpediaStaysPage.selectStaysLocationFromList(driver,destination,subtextDestination);
	  Assert.assertTrue(ExpediaStaysPage.inputStaysLocationClickable(driver).getText().contains(destination),"Error: "+destination+" not selected in the stays location list");
	  logger.info("Entering check in date: "+fromDate);
		ExpediaStaysPage.selectDateFromCalendar(driver,fromDate,dateFormat,"from");
		ExpediaStaysPage.btnDone(driver).click();
	  logger.info("Entering check out date: "+toDate);
		ExpediaStaysPage.selectDateFromCalendar(driver,toDate,dateFormat,"to");
		ExpediaStaysPage.btnDone(driver).click();
	  driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	  ExpediaCommon.btnSearch(driver).click();
	  ExpediaHotelsResultsPage.assertResultsPage(driver);
	  ExpediaHotelsResultsPage.firstHotelToSelect(driver).click();
	  //driver.close();
  }

	@Test(dataProvider = "flightData", dataProviderClass = CSVReader.class)
	public void TestFlightSearchTestCase(FlightSearchData flightData) {
		String destination = flightData.getDestination();
		String destinationAirport=flightData.getDestinationAirport();
		String origin = flightData.getOrigin();
		String originAirport=flightData.getOriginAirport();
		String fromDate =	flightData.getFrom();
		String toDate = flightData.getTo();
		driver.get(baseURL);
//		Assert.assertEquals(ExpediaCommon.logoExpedia(driver).isDisplayed(),true,"Error: Expedia logo didn't load or not displayed");
		ExpediaCommon.tabFlight(driver).click();
		ExpediaFlightsPage.enterOrigin(driver,origin);
		ExpediaFlightsPage.selectAirportFromList(driver,originAirport);
		Assert.assertTrue(ExpediaStaysPage.inputFlightFromClickable(driver).getText().contains(origin),"Error: "+origin+" not selected in the flight from list");
		ExpediaFlightsPage.enterDestination(driver,destination);
		ExpediaFlightsPage.selectAirportFromList(driver,destinationAirport);
		Assert.assertTrue(ExpediaStaysPage.inputFlightToClickable(driver).getText().contains(destination),"Error: "+destination+" not selected in the flight from list");
		logger.info("Entering departure date: "+fromDate);
		ExpediaStaysPage.selectDateFromCalendar(driver,fromDate,dateFormat,"from");
		ExpediaStaysPage.btnDone(driver).click();
		logger.info("Entering return date: "+toDate);
		ExpediaStaysPage.selectDateFromCalendar(driver,toDate,dateFormat,"to");
		ExpediaStaysPage.btnDone(driver).click();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		ExpediaCommon.btnSearch(driver).click();
		ExpediaFlightsPage.assertTextOnFlightResultsPage(driver,"Select your departure");
		logger.info("Selecting flight departure");
		ExpediaFlightsPage.clickOnSelectBtn(driver);
		//ExpediaFlightsPage.assertTextOnFlightResultsPage(driver,"Select your return");
		logger.info("Selecting flight return");
		ExpediaFlightsPage.clickOnSelectBtn(driver);
		//driver.close();
	}





}
