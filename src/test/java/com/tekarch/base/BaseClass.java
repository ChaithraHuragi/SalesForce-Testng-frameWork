
package com.tekarch.base;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.tekarch.utilityforsaleforce.Constants;
import com.tekarch.utilityforsaleforce.ExtentReportsUtility;
import com.tekarch.utilityforsaleforce.PropertiesUtility;

import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {
	protected static WebDriver driver = null;
	protected static Logger logger = null;
	protected static ExtentReportsUtility extentReport = ExtentReportsUtility.getInstance();

	@BeforeTest
	public void setUpBeforeTest() {
		logger = LogManager.getLogger(BaseClass.class.getName());

	}
	public WebDriver returnDriverInstance() {
		return driver;
	}

	@BeforeMethod
	@Parameters("browsername")
	public void setupBeforeMethod(@Optional("Chrome") String browserName, Method method) {

		logger.info("Started test script:" + method.getName());
	//	extentReport.startSingleTestReport(method.getName());
	//	extentReport.logTestInfo("Started test script:" + method.getName());
		PropertiesUtility propertiesUtility = new PropertiesUtility();
		propertiesUtility.loadFile("loginDataProperties");
		String url = propertiesUtility.getPropertyData("url");
		getDrive(browserName);
		goToUrl(url);

	}

	@AfterMethod(alwaysRun=true)

	public void tearDownAfterMethod(Method method) {
		logger.info("Driver close:" + method.getName());
		extentReport.logTestInfo("Driver close:" + method.getName());
		driver.close();
	}

	public static void getDrive(String browserName) {
		System.out.println("inside select browser");
		switch (browserName) {
		case "Chrome":
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			break;
		case "Firefox":
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			driver.manage().window().maximize();
			break;
		case "Edge":
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
			break;
		case "Safari":
			WebDriverManager.safaridriver().setup();
			driver = new SafariDriver();
			driver.manage().window().maximize();
			break;
		default:
			System.out.println("DriverName not recognised");
			logger.info("driver name not recognized");
		}
	}

	public static void sendText(WebElement element, String text, String webElementName) {
		if (element.isDisplayed()) {
			System.out.println("Find Element: " + webElementName);
			element.clear();
			element.sendKeys(text);
		} else {
			System.out.println(webElementName + "WebElement is not found  ");
		}
	}

	public static void goToUrl(String url) {
		System.out.println("inside get url");
		driver.get(url);

	}

	public static void click(WebElement webElement) {
		logger.info("clicked" + webElement);
		webElement.click();
	}

	public static void waitForElementVisibility(WebElement element) {
		logger.info("waiting for element:" + element);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOf(element));

	}

	public static void action(WebElement element) {
		logger.info("action performing on:" + element);
		Actions action = new Actions(driver);
		action.moveToElement(element).build().perform();
	}

	public static void close() {
		driver.close();
	}

	public static void select(WebElement element, String value) {
		logger.info("Select by visible text on:" + element + "value:" + value);
		Select select = new Select(element);
		select.selectByVisibleText(value);

	}

	public static String returnSelectString(WebElement element, String value) {
		Select select = new Select(element);
		select.selectByVisibleText(value);
		return select.getFirstSelectedOption().getText();

	}

	public static void clear(WebElement element) {
		element.clear();
	}

	public static void getScreenshot() throws IOException {
		String date = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss").format(new Date());
		String curDir = System.getProperty("user.dir");
		TakesScreenshot screenshot = (TakesScreenshot) driver;
		File img = screenshot.getScreenshotAs(OutputType.FILE);
		File destFile = new File(curDir + "/Screenshots/img1.png" + date);
		try {
			FileHandler.copy(img, destFile);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public String getScreenshot(WebDriver driver) throws IOException {
		String date = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss").format(new Date());
	//	String curDir = System.getProperty("user.dir");
		TakesScreenshot screenshot = (TakesScreenshot) driver;
		File img = screenshot.getScreenshotAs(OutputType.FILE);
		File destFile = new File(Constants.SCREENSHOT_DIRECTORY_PATH + date);
		try{
			FileHandler.copy(img, destFile);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return destFile.getAbsolutePath();
	}
}
