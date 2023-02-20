package com.tekarch.SalesForce;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.tekarch.base.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.Assert;

import com.tekarch.utilityforsaleforce.PropertiesUtility;

@Listeners(com.tekarch.utilityforsaleforce.TestEventListners.class)
public class SalesForceWithFrame extends BaseClass {

	public static void login() {
		PropertiesUtility propertiesUtility = new PropertiesUtility();
		propertiesUtility.loadFile("loginDataProperties");
		String userid = propertiesUtility.getPropertyData("login.valid.userid");
		String pwd = propertiesUtility.getPropertyData("login.valid.password");

		WebElement userName = driver.findElement(By.id("username"));
		sendText(userName, userid, "username");
		click(userName);

		WebElement password = driver.findElement(By.id("password"));
		sendText(password, pwd, "password");
		click(password);

		WebElement login = driver.findElement(By.id("Login"));
		click(login);
	}

	public static void logout() throws InterruptedException {
		WebElement userMenu = driver.findElement(By.id("userNavLabel"));
		action(userMenu);
		click(userMenu);
		WebElement logout = driver.findElement(By.xpath("//a[text() ='Logout']"));
		waitForElementVisibility(logout);
		System.out.println("in logout");
		click(logout);
	}

	@Test(alwaysRun = true, priority = 1)
	public static void errorMessageBlankPassword() throws IOException {
		// tc01
		logger.info("test case 1 inside");
		extentReport.logTestInfo("test case 1 inside");
		PropertiesUtility propertiesUtility = new PropertiesUtility();
		propertiesUtility.loadFile("loginDataProperties");
		String userid = propertiesUtility.getPropertyData("login.valid.userid");

		WebElement userName = driver.findElement(By.id("username"));
		sendText(userName, userid, "username");
		click(userName);

		WebElement password = driver.findElement(By.id("password"));
		password.clear();
		click(password);

		WebElement login = driver.findElement(By.id("Login"));
		click(login);

		WebElement error = driver.findElement(By.xpath("//div[text() = 'Please enter your password.']"));
		if (error.isEnabled()) {
			logger.info("test Case 1 passed");
			extentReport.logTestpassed("errorMessageBlankPassword");

		} else {
			logger.error("test case 01 fail");
			extentReport.logTestFailed("ErrorMesssageBlankPassword");
			getScreenshot();
		}
	}

	@Test(alwaysRun = true, priority = 2)

	public static void loginPage() {
		// tc02

		PropertiesUtility propertiesUtility = new PropertiesUtility();
		propertiesUtility.loadFile("loginDataProperties");
		String userid = propertiesUtility.getPropertyData("login.valid.userid");
		String pwd = propertiesUtility.getPropertyData("login.valid.password");

		WebElement userName = driver.findElement(By.id("username"));
		sendText(userName, userid, "username");
		click(userName);

		WebElement password = driver.findElement(By.id("password"));
		sendText(password, pwd, "password");
		click(password);

		WebElement login = driver.findElement(By.id("Login"));
		click(login);

		WebElement homePage = driver
				.findElement(By.xpath("//title[text() = 'Home Page ~ Salesforce - Developer Edition']"));
		if (homePage.isEnabled()) {
			logger.info("testcase 02 passed");
			System.out.println("Login with valid data is passed");
			extentReport.logTestpassed("login");
		} else {
			logger.error("test case 02 failed");
			System.out.println("Login with valid data is Failed");
			extentReport.logTestFailed("login");
		}
	}

	@Test(alwaysRun = true, priority = 3)

	public static void validateCheck() throws InterruptedException {
		// tc03
		PropertiesUtility propertiesUtility = new PropertiesUtility();
		propertiesUtility.loadFile("loginDataProperties");
		String userid = propertiesUtility.getPropertyData("login.valid.userid");
		String pwd = propertiesUtility.getPropertyData("login.valid.password");

		WebElement userName = driver.findElement(By.id("username"));
		sendText(userName, userid, "username");
		click(userName);

		WebElement password = driver.findElement(By.id("password"));
		sendText(password, pwd, "password");
		click(password);

		WebElement rememberMe = driver.findElement(By.xpath("//input[@id='rememberUn']"));
		click(rememberMe);

		WebElement login = driver.findElement(By.id("Login"));
		click(login);
		WebElement userMenu = driver.findElement(By.id("userNavLabel"));
		action(userMenu);
		waitForElementVisibility(userMenu);
		click(userMenu);

		WebElement logout = driver.findElement(By.xpath("//a[text() ='Logout']"));
		waitForElementVisibility(logout);
		click(logout);
		Thread.sleep(3000);
		WebElement rememberme = driver.findElement(By.xpath("//*[@id='idcard-identity']"));
		if (rememberme.isEnabled()) {
			logger.info("testcase 03 passed");
			System.out.println("TestCase pass");
			extentReport.logTestpassed("login");
		} else {

			System.out.println("test case fail");
			logger.error("test case 03 failed");
			extentReport.logTestFailed("login");

		}
	}

	@Test(alwaysRun = true, priority = 4)
	public static void forgotPassword() throws IOException {
		// tc 4A
		String expected = "Check Your Email";
		PropertiesUtility propertiesUtility = new PropertiesUtility();
		propertiesUtility.loadFile("loginDataProperties");
		String userid = propertiesUtility.getPropertyData("login.valid.userid");
		String url = propertiesUtility.getPropertyData("url");

		WebElement forgot = driver.findElement(By.id("forgot_password_link"));
		click(forgot);

		WebElement usernameInforgot = driver.findElement(By.id("un"));
		waitForElementVisibility(usernameInforgot);
		sendText(usernameInforgot, userid, "username");
		click(usernameInforgot);

		WebElement submit = driver.findElement(By.id("continue"));
		click(submit);

		WebElement checkEmail = driver.findElement(By.className("mb12"));
		waitForElementVisibility(checkEmail);
		String actual = checkEmail.getText();
		Assert.assertEquals(actual, expected);
	}

	@Test(alwaysRun = true, priority = 5)
	public static void errorWIthInvalidCred() {
		// 4b
		PropertiesUtility propertiesUtility = new PropertiesUtility();
		propertiesUtility.loadFile("loginDataProperties");
		String userid = propertiesUtility.getPropertyData("login.invalid.userid");
		String pwd = propertiesUtility.getPropertyData("login.invalid.password");
		String url = propertiesUtility.getPropertyData("url");
		getDrive("Chrome");
		goToUrl(url);
		WebElement userName = driver.findElement(By.id("username"));
		sendText(userName, userid, "username");
		click(userName);

		WebElement password = driver.findElement(By.id("password"));
		sendText(password, pwd, "password");
		click(password);

		WebElement login = driver.findElement(By.id("Login"));
		click(login);

		WebElement errMsg = driver.findElement(By.id("chooser_error"));
		if (errMsg.isEnabled()) {
			System.out.println("Error message displaying test case pass");
			logger.info("testcase 4b passed");
			extentReport.logTestpassed("errorWIthInvalidCred");

		} else {

			System.out.println("Error message displaying test case failed");
			logger.info("testcase 4b failed");
			extentReport.logTestFailed("errorWIthInvalidCred");

		}

	}

	@Test(alwaysRun = true, priority = 7)
	public static void userMenuDropDown() {
		// tc05
		PropertiesUtility propertiesUtility = new PropertiesUtility();
		propertiesUtility.loadFile("loginDataProperties");
		String userid = propertiesUtility.getPropertyData("login.valid.userid");
		String pwd = propertiesUtility.getPropertyData("login.valid.password");

		WebElement userName = driver.findElement(By.id("username"));
		sendText(userName, userid, "username");
		click(userName);

		WebElement password = driver.findElement(By.id("password"));
		sendText(password, pwd, "password");
		click(password);

		WebElement rememberMe = driver.findElement(By.xpath("//input[@id='rememberUn']"));
		click(rememberMe);

		WebElement login = driver.findElement(By.id("Login"));
		click(login);
		WebElement userMenu = driver.findElement(By.id("userNavLabel"));
		action(userMenu);
		click(userMenu);
		WebElement dropdownMenu = driver.findElement(By.id("userNavMenu"));
		if (dropdownMenu.isEnabled()) {
			logger.info("testcase 05 passed");
			System.out.println("User Name dropdown menu test case pass");
			extentReport.logTestpassed("userMenuDropDown");
		} else {
			logger.error("test case 05 failed");
			System.out.println("User Name dropdown menu test case fail");
			extentReport.logTestFailed("userMenuDropDown");
		}

	}

	@Test(alwaysRun = true, priority = 39)
	public static void myProfileOptions() throws InterruptedException, AWTException {
		// tc06
		PropertiesUtility propertiesUtility = new PropertiesUtility();
		propertiesUtility.loadFile("loginDataProperties");
		String userid = propertiesUtility.getPropertyData("login.valid.userid");
		String pwd = propertiesUtility.getPropertyData("login.valid.password");

		WebElement userName = driver.findElement(By.id("username"));
		sendText(userName, userid, "username");
		click(userName);

		WebElement password = driver.findElement(By.id("password"));
		sendText(password, pwd, "password");
		click(password);

		WebElement login = driver.findElement(By.id("Login"));
		click(login);

		WebElement userMenu = driver.findElement(By.id("userNavLabel"));
		waitForElementVisibility(userMenu);
		action(userMenu);
		click(userMenu);

		WebElement myProfile = driver.findElement(By.xpath("//a[text()='My Profile']"));
		click(myProfile);
		Thread.sleep(2000);

		WebElement editProfile = driver.findElement(By.xpath("//a[@class='contactInfoLaunch editLink']"));
		// WebElement editProfile =
		// driver.findElement(By.xpath("//*[@id=\"chatterTab\"]/div[2]/div[2]/div[1]/h3/div/div/a/img"));
		// wait.until(ExpectedConditions.visibilityOf(editProfile));
		waitForElementVisibility(editProfile);
		click(editProfile);
		Thread.sleep(6000);
		driver.switchTo().frame("contactInfoContentId");
		Thread.sleep(3000);

		WebElement about = driver.findElement(By.partialLinkText("About"));
		click(about);

		WebElement lastName = driver.findElement(By.id("lastName"));
		lastName.clear();
		sendText(lastName, "Huragi", "LastNAme");

		WebElement save = driver.findElement(By.xpath("//body[1]/div[1]/div[1]/div[1]/div[2]/form[1]/div[1]/input[1]"));
		click(save);

		Thread.sleep(2000);

		WebElement post = driver.findElement(By.id("publisherAttachTextPost"));
		click(post);

		WebElement share = driver.findElement(By.id("publishersharebutton"));
		WebElement file = driver.findElement(By.xpath("//span[text() = 'File']"));
		click(file);
		WebElement upload = driver.findElement(By.id("chatterUploadFileAction"));
		click(upload);
		Thread.sleep(2000);

		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("document.getElementById('chatterFile' ).click();");

		Robot rb = new Robot();

		StringSelection str = new StringSelection(
				"F:\\Chaithra\\workspace\\SeleniumWithMaven\\src\\test\\resources\\version1.txt");
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(str, null);

		Thread.sleep(3000);
		rb.keyPress(KeyEvent.VK_CONTROL);
		rb.keyPress(KeyEvent.VK_V);

		rb.keyRelease(KeyEvent.VK_CONTROL);
		rb.keyRelease(KeyEvent.VK_V);

		rb.keyPress(KeyEvent.VK_ENTER);
		rb.keyRelease(KeyEvent.VK_ENTER);

		Thread.sleep(2000);
		WebElement shareFile = driver.findElement(By.id("publishersharebutton"));
		click(shareFile);

		Thread.sleep(5000);
		executor.executeScript("document.getElementById('uploadLink' ).click();");

		driver.switchTo().frame("uploadPhotoContentId");

		Thread.sleep(3000);
		WebElement choosePhoto = driver.findElement(By.id("j_id0:uploadFileForm:uploadInputFile"));

		choosePhoto.sendKeys("F:\\Chaithra\\workspace\\SeleniumWithMaven\\src\\test\\resources\\IMG_5053.jpg");

		WebElement savePhoto = driver.findElement(By.xpath("//input[@value='Save'][1]"));
		Thread.sleep(3000);

		JavascriptExecutor executor1 = (JavascriptExecutor) driver;
		executor1.executeScript("document.getElementById('j_id0:uploadFileForm:uploadBtn' ).click();");
		Thread.sleep(4000);
		WebElement size = driver.findElement(By.xpath("//*[@id=\"j_id0:outer\"]/div[1]/div/div/div/div[5]/div[9]"));
		size.getRect();

		executor1.executeScript("document.getElementById('j_id0:j_id7:save' ).click();");
		Thread.sleep(3000);
		WebElement profileupdate = driver
				.findElement(By.xpath("//span[@class='profileImage chatter-avatarFull chatter-avatar']"));
		if (profileupdate.isEnabled()) {
			logger.info("testcase 06 passed");
			System.out.println("My profile options test case pass");
			extentReport.logTestpassed("myProfileOptions");
		} else {
			logger.info("testcase 06 failed");
			System.out.println("myProfileOptions test case pass");
			extentReport.logTestFailed("myProfileOptions");
		}

	}

	@Test(alwaysRun = true, priority = 8)
	public static void mySettings() throws InterruptedException {
		// tc07
		PropertiesUtility propertiesUtility = new PropertiesUtility();
		propertiesUtility.loadFile("loginDataProperties");
		String userid = propertiesUtility.getPropertyData("login.valid.userid");
		String pwd = propertiesUtility.getPropertyData("login.valid.password");

		WebElement userName = driver.findElement(By.id("username"));
		sendText(userName, userid, "username");
		click(userName);

		WebElement password = driver.findElement(By.id("password"));
		sendText(password, pwd, "password");
		click(password);

		WebElement rememberMe = driver.findElement(By.xpath("//input[@id='rememberUn']"));
		click(rememberMe);

		WebElement login = driver.findElement(By.id("Login"));
		click(login);
		WebElement userMenu = driver.findElement(By.id("userNavLabel"));
		waitForElementVisibility(userMenu);
		action(userMenu);
		click(userMenu);
		WebElement mySettings = driver.findElement(By.xpath("//a[text() = 'My Settings']"));
		click(mySettings);
		Thread.sleep(4000);

		WebElement personal = driver.findElement(By.xpath("//span[text() = 'Personal']"));
		waitForElementVisibility(personal);
		click(personal);

		WebElement loginhistory = driver.findElement(By.xpath("//a[@id='LoginHistory_font']"));
		click(loginhistory);

		WebElement download = driver
				.findElement(By.xpath("//a[contains(text(),'Download login history for last six months, includ')]"));
		click(download);

		WebElement personal1 = driver.findElement(By.xpath("//span[text() = 'Personal']"));
		waitForElementVisibility(personal1);
		click(personal1);

		WebElement displayandLayout = driver.findElement(By.xpath("//span[@id='DisplayAndLayout_font']"));
		waitForElementVisibility(displayandLayout);
		click(displayandLayout);

		WebElement customizeTab = driver.findElement(By.xpath("//span[@id = 'CustomizeTabs_font']"));
		click(customizeTab);

		WebElement customApp = driver.findElement(By.id("p4"));
		select(customApp, "Salesforce Chatter");
		// Select ob = new Select(customApp);
		// ob.selectByVisibleText("Salesforce Chatter");

		WebElement selectAvailable = driver.findElement(By.xpath("//select[@id='duel_select_0']"));
		select(selectAvailable, "Reports");
		// Select ob1 = new Select(selectAvailable);
		// ob1.selectByVisibleText("Reports");

		WebElement rightArrow = driver.findElement(By.xpath("//img[@title='Add']"));
		click(rightArrow);

		WebElement selected = driver.findElement(By.xpath("//select[@id='duel_select_1']"));
		select(selected, "Reports");
		// Select ob2 = new Select(selected);
		// ob2.selectByVisibleText("Reports");
		WebElement displayandLayout1 = driver.findElement(By.xpath("//span[@id='DisplayAndLayout_font']"));
		// wait.until(ExpectedConditions.visibilityOf(displayandLayout1));
		waitForElementVisibility(displayandLayout1);
		click(displayandLayout1);

		WebElement email = driver.findElement(By.xpath("//span[@id='EmailSetup_font']"));
		click(email);

		WebElement myEmail = driver.findElement(By.xpath("//a[@id='EmailSettings_font']"));
		click(myEmail);

		WebElement emailName = driver.findElement(By.xpath("//input[@id='sender_name']"));
		waitForElementVisibility(emailName);
		sendText(emailName, "chaithra huragi", "Email Name");

		WebElement emailId = driver.findElement(By.id("sender_email"));
		sendText(emailId, "chaithrahuragi@tekarch.com", "emailid");

		WebElement save = driver.findElement(By.xpath("//input[@title='Save']"));
		if (save.isEnabled()) {
			logger.info("testcase 07 passed");
			System.out.println("My settings test case pass");
			extentReport.logTestpassed("mySettings");
		} else {
			logger.info("testcase 07 failed");
			System.out.println("mysettings  case pass");
			extentReport.logTestFailed("mySettings");
		}
		click(save);
		driver.switchTo().alert().accept();
		WebElement userMenu1 = driver.findElement(By.id("userNavLabel"));
		action(userMenu1);
		click(userMenu1);
		WebElement logout = driver.findElement(By.xpath("//a[text() ='Logout']"));
		waitForElementVisibility(logout);
		System.out.println("in logout");
		click(logout);
	}

	@Test(alwaysRun = true, priority = 9)

	public static void devloperConsole() {
		// tc08
		PropertiesUtility propertiesUtility = new PropertiesUtility();
		propertiesUtility.loadFile("loginDataProperties");
		String userid = propertiesUtility.getPropertyData("login.valid.userid");
		String pwd = propertiesUtility.getPropertyData("login.valid.password");

		WebElement userName = driver.findElement(By.id("username"));
		sendText(userName, userid, "username");
		click(userName);

		WebElement password = driver.findElement(By.id("password"));
		sendText(password, pwd, "password");
		click(password);

		WebElement rememberMe = driver.findElement(By.xpath("//input[@id='rememberUn']"));
		click(rememberMe);

		WebElement login = driver.findElement(By.id("Login"));
		click(login);
		WebElement userMenu = driver.findElement(By.id("userNavLabel"));
		waitForElementVisibility(userMenu);
		action(userMenu);
		click(userMenu);
		WebElement devCons = driver.findElement(By.xpath("//a[contains(text(),'Developer Console')]"));
		click(devCons);

		Set<String> handles = driver.getWindowHandles();
		String current = driver.getWindowHandle();
		for (String handle : handles) {
			if (!current.equals(handle)) {
				driver.switchTo().window(handle);
				break;
			}

		}
		driver.close();
		driver.switchTo().window(current);
		String currentWindow = driver.getWindowHandle();
		Assert.assertEquals(current, currentWindow);
		WebElement userMenulogout = driver.findElement(By.id("userNavLabel"));
		action(userMenulogout);
		click(userMenulogout);
		WebElement logout = driver.findElement(By.xpath("//a[text() ='Logout']"));
		waitForElementVisibility(logout);
		System.out.println("in logout");
		click(logout);

	}

	@Test(alwaysRun = true, priority = 10)
	public static void createAccount() throws InterruptedException {
		// TC10
		login();
		String current = driver.getWindowHandle();
		WebElement account = driver.findElement(By.xpath("//a[@title='Accounts Tab']"));

		waitForElementVisibility(account);
		action(account);
		click(account);
		Thread.sleep(2000);

		WebElement prompt = driver.findElement(By.xpath("//*[@id='tryLexDialogX'] "));
		waitForElementVisibility(prompt);
		if (prompt.isEnabled()) {
			click(prompt);
			Thread.sleep(2000);
		}
		WebElement createNew = driver.findElement(By.id("createNew"));
		Thread.sleep(2000);
		click(createNew);
		Thread.sleep(2000);
		WebElement accountinlist = driver.findElement(By.xpath("//*[@id=\"createNewMenu\"]/a[4]"));
		click(accountinlist);

		WebElement accName = driver.findElement(By.xpath("//*[@id=\"acc2\"]"));
		waitForElementVisibility(accName);
		sendText(accName, "chaka", "accountName");
		click(accName);
		WebElement type = driver.findElement(By.xpath("//*[@id='acc6']"));
		Select ob1 = new Select(type);
		ob1.selectByValue("Technology Partner");

		WebElement priority = driver.findElement(By.xpath("//*[@id = '00NDn00000Sjaix']"));
		Select ob2 = new Select(priority);
		ob2.selectByValue("High");

		WebElement save = driver.findElement(By.xpath("//*[@name='save']"));
		click(save);
		WebElement topName = driver.findElement(By.className("topName"));
		if (topName.isEnabled()) {
			logger.info("testcase 10 passed");
			System.out.println("createAccount test case pass");
			extentReport.logTestpassed("createAccount");
		} else {
			logger.info("testcase 10 failed");
			System.out.println("createAccount test case fail");
			extentReport.logTestFailed("createAccount");
		}
		logout();

	}

	@Test(alwaysRun = true, priority = 40)
	public static void createNewView() throws InterruptedException {
//tc11
		login();
		Thread.sleep(3000);
		WebElement account = driver.findElement(By.xpath("//a[@title='Accounts Tab']"));

		waitForElementVisibility(account);
		action(account);
		click(account);
		Thread.sleep(2000);

		WebElement prompt = driver.findElement(By.xpath("//*[@id='tryLexDialogX'] "));
		waitForElementVisibility(prompt);
		if (prompt.isEnabled()) {
			click(prompt);
			Thread.sleep(2000);
		}

		WebElement createNewView = driver.findElement(By.xpath("//a[text()='Create New View']"));
		waitForElementVisibility(createNewView);
		click(createNewView);

		WebElement viewName = driver.findElement(By.xpath("//input [@id = 'fname']"));
		sendText(viewName, "docview22", "viewname ");
		click(viewName);

		WebElement viewUniqueName = driver.findElement(By.xpath("//input [@id = 'devname']"));
		sendText(viewUniqueName, "docview22", "view uniquename");
		click(viewUniqueName);

		WebElement save = driver.findElement(By.xpath("//input[@name = 'save']"));
		click(save);

		WebElement check = driver.findElement(By.id("00BDn00000L9SD8_listSelect"));
		if (check.isEnabled()) {
			System.out.println("test case pass");
		} else {
			System.out.println("test case fail");
		}
		logout();
	}

	@Test(alwaysRun = true, priority = 12)
	public static void editView() throws InterruptedException {
		// tc12
		login();
		WebElement account = driver.findElement(By.xpath("//a[@title='Accounts Tab']"));
		waitForElementVisibility(account);
		action(account);
		click(account);
		Thread.sleep(2000);
		WebElement prompt = driver.findElement(By.xpath("//*[@id='tryLexDialogX'] "));
		waitForElementVisibility(prompt);
		if (prompt.isEnabled()) {
			click(prompt);
			Thread.sleep(2000);
		}
		WebElement view = driver.findElement(By.xpath("//select[@id = 'fcf']"));
		select(view, "docview");
		WebElement edit = driver.findElement(By.xpath("//a[text() = 'Edit']"));
		click(edit);

		WebElement viewName = driver.findElement(By.xpath("//input [@id = 'fname']"));
		sendText(viewName, "docviewEdited", "viewname ");
		click(viewName);
		WebElement save = driver.findElement(By.xpath("//input[@name = 'save']"));
		click(save);
		WebElement docEdit = driver.findElement(By.xpath("//select[@class='title']"));
		if (docEdit.isEnabled()) {
			logger.info("testcase 12 passed");
			System.out.println("EditView test case pass");
			extentReport.logTestpassed("EditView");
		} else {
			logger.info("testcase 12 failed");
			System.out.println("EditView  test case fail");
			extentReport.logTestFailed("EditView");
		}
		logout();

	}

	@Test(alwaysRun = true, priority = 13)
	public static void mergeAccounts() throws InterruptedException {
		// tc13
		login();
		WebElement account = driver.findElement(By.xpath("//a[@title='Accounts Tab']"));
		waitForElementVisibility(account);
		action(account);
		click(account);
		Thread.sleep(2000);
		WebElement prompt = driver.findElement(By.id("tryLexDialogX"));
		waitForElementVisibility(prompt);
		if (prompt.isEnabled()) {
			click(prompt);
			Thread.sleep(2000);

		}
		WebElement merge = driver.findElement(By.xpath("//a[text()='Merge Accounts']"));
		click(merge);
		WebElement textbox = driver.findElement(By.xpath("//input[@id='srch']"));
		click(textbox);
		sendText(textbox, "Chaithra", "Chaithra account to merge");
		// Thread.sleep(3000);
		WebElement findAccounts = driver.findElement(By.xpath("//input[@name='srchbutton']"));
		click(findAccounts);
		Thread.sleep(3000);
		WebElement next = driver.findElement(By.xpath("//*[@id='stageForm']/div/div[2]/div[5]/div/input[1]"));
		click(next);
		// *[@id="stageForm"]/div/div[2]/div[5]/div/input[1]
		Thread.sleep(2000);
		WebElement mergebutton = driver.findElement(By.xpath("//input[@value=' Merge ']"));
		click(mergebutton);
		driver.switchTo().alert().accept();
		Thread.sleep(2000);
		WebElement home = driver.findElement(By.xpath("//h2[normalize-space()='Home']"));
		if (home.isEnabled()) {
			logger.info("testcase 13 passed");
			System.out.println("MergeAccount test case pass");
			extentReport.logTestpassed("MergeAccounts");
		} else {
			logger.info("testcase 13 failed");
			System.out.println("MergeAcounts  test case fail");
			extentReport.logTestFailed("MergeAccounts");
		}
		logout();
	}
	@Test(alwaysRun = true, priority = 14)
	
	public static void createAccountReport() throws InterruptedException {
		// tc14
		login();
		WebElement account = driver.findElement(By.xpath("//a[@title='Accounts Tab']"));
		waitForElementVisibility(account);
		action(account);
		click(account);
		Thread.sleep(2000);
		WebElement prompt = driver.findElement(By.id("tryLexDialogX"));
		waitForElementVisibility(prompt);
		if (prompt.isEnabled()) {
			click(prompt);
			Thread.sleep(2000);

		}
		WebElement accountHistory = driver
				.findElement(By.xpath("//a[text() = 'Accounts with last activity > 30 days']"));
		click(accountHistory);

		WebElement from = driver.findElement(By.xpath("//img[@id=\"ext-gen152\"]"));
		waitForElementVisibility(from);
		click(from);

		WebElement today = driver.findElement(By.xpath("//button[text() = 'Today']"));
		waitForElementVisibility(today);
		click(today);

		WebElement save = driver.findElement(By.xpath("//button[@id = 'ext-gen49']"));
		click(save);

		WebElement reportName = driver.findElement(By.xpath("//input[@name = 'reportName']"));
		String date = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss").format(new Date());
		String reportNameInput = "testReportName" + date;
		sendText(reportName, reportNameInput, "reportName");
		click(reportName);

		WebElement reportUniqueName = driver.findElement(By.xpath("//input[@name = 'reportDevName']"));
		String reportUniqueNameInput = "testReportName" + date;
		sendText(reportUniqueName, reportUniqueNameInput, "reportUniqueName");
		click(reportUniqueName);
		Thread.sleep(5000);

		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("document.getElementById('ext-gen297' ).click();");

		WebElement pageTitle = driver
				.findElement(By.xpath("//*[@id=\"noTableContainer\"]/div/div[1]/div[1]/div[1]/h1"));
		if (pageTitle.isEnabled()) {

			logger.info("testcase 14 passed");
			System.out.println("CreateAccountReports test case pass");
			extentReport.logTestpassed("CreateAccountReports");
		} else {
			logger.info("testcase 14 failed");
			System.out.println("CreateAcountsReport  test case fail");
			extentReport.logTestFailed("CreateAccountReports");
		}
		logout();
	}
	@Test(alwaysRun = true, priority = 15)
	public static void oppurtunities() throws InterruptedException {
		//tc15
		login();
		WebElement oppurtunities = driver.findElement(By.xpath("//a[@title='Opportunities Tab']"));
		waitForElementVisibility(oppurtunities);
		action(oppurtunities);
		click(oppurtunities);
		Thread.sleep(2000);
		WebElement prompt = driver.findElement(By.id("tryLexDialogX"));
		waitForElementVisibility(prompt);
		if (prompt.isEnabled()) {
			click(prompt);
			Thread.sleep(2000);
		}
		ArrayList<String> expected = new ArrayList<String>(Arrays.asList("All Opportunities", "Closing Next Month",
				"Closing This Month", "My Opportunities", "New Last Week", "New This Week", "Opportunity Pipeline",
				"Private", "Recently Viewed Opportunities", "Won"));

		WebElement view = driver.findElement(By.id("fcf"));
		Select select = new Select(view);
		List<WebElement> list = select.getOptions();
		ArrayList<String> actual = new ArrayList<String>();

		for (WebElement ele : list) {
			String text = ele.getText();
			System.out.println(ele.getText());
			actual.add(text);
		}
		System.out.println("*************");
		System.out.println(actual);
		System.out.println(expected);
		Assert.assertEquals(actual,expected);
		/*if (expected.equals(actual)) {
			System.out.println("Test Case passed");
		} else {
			System.out.println("Test case failed");
		}
		
*/
		logout();
	}
	@Test(alwaysRun = true, priority = 16)
	
	public static void newOppurtunities() throws InterruptedException {
		//tc16
		login();
		WebElement oppurtunities = driver.findElement(By.xpath("//a[@title='Opportunities Tab']"));
		waitForElementVisibility(oppurtunities);
		action(oppurtunities);
		click(oppurtunities);
		Thread.sleep(2000);
		WebElement prompt = driver.findElement(By.id("tryLexDialogX"));
		if (prompt.isEnabled()) {
			click(prompt);
			Thread.sleep(2000);
		}
	
		String expected = "New Opportunity" ;
		WebElement newButton = driver.findElement(By.xpath("//input[@name = 'new']"));
		click(newButton);
		WebElement pagetitle = driver.findElement(By.xpath("//h2[text()=' New Opportunity']"));
		String actual = pagetitle.getText();
		Assert.assertEquals(actual, expected);
		
		/*
		 * if(actual.equals(expected)) { System.out.println("TestCase Pass"); }else {
		 * System.out.println("test Case fail"); }
		 */
		
		logout();
	}

	@Test(alwaysRun = true, priority = 17)
	
	public static void oppurtunitiesPipelineLink() throws InterruptedException {
		//tc17
		login();
		WebElement oppurtunities = driver.findElement(By.xpath("//a[@title='Opportunities Tab']"));
		waitForElementVisibility(oppurtunities);
		action(oppurtunities);
		click(oppurtunities);
		WebElement prompt = driver.findElement(By.id("tryLexDialogX"));
		if (prompt.isEnabled()) {
			click(prompt);
		}
		String expected = "Opportunity Pipeline" ;
		WebElement pipeLine =driver.findElement(By.xpath("//a[text()='Opportunity Pipeline']"));
		click(pipeLine);
		WebElement actualTitle = driver.findElement(By.xpath("//h1[text()='Opportunity Pipeline']"));
		String actual = actualTitle.getText();
		Assert.assertEquals(actual, expected);
		/*
		 * if(actual.equals(expected)) { System.out.println("Test case pass"); }else {
		 * System.out.println("test case fail"); }
		 */
		logout();
		
	}
	@Test(alwaysRun = true, priority = 18)
	
	public static void stuckOppurtunitiesLink() {
		//tc18
		login();
		WebElement oppurtunities = driver.findElement(By.xpath("//a[@title='Opportunities Tab']"));
		waitForElementVisibility(oppurtunities);
		action(oppurtunities);
		click(oppurtunities);
		WebElement prompt = driver.findElement(By.id("tryLexDialogX"));
		if (prompt.isEnabled()) {
			click(prompt);
		}
		String expected = "Stuck Opportunities" ;
		WebElement pipeLine =driver.findElement(By.xpath("//a[text()='Stuck Opportunities']"));
		click(pipeLine);
		WebElement actualTitle = driver.findElement(By.xpath("//h1[text()='Stuck Opportunities']"));
		String actual = actualTitle.getText();
		Assert.assertEquals(actual, expected);
		/*
		 * if(actual.equals(expected)) { System.out.println("Test case pass"); }else {
		 * System.out.println("test case fail"); }
		 */
		
	}
	@Test(alwaysRun = true, priority = 19)
	
	public static void quatarlyLink() throws InterruptedException {
		//tc19
		login();
		WebElement oppurtunities = driver.findElement(By.xpath("//a[@title='Opportunities Tab']"));
		waitForElementVisibility(oppurtunities);
		action(oppurtunities);
		click(oppurtunities);
		WebElement prompt = driver.findElement(By.id("tryLexDialogX"));
		if (prompt.isEnabled()) {
			click(prompt);
		}
		String expected = "Opportunity Report ~ Salesforce - Developer Edition";
		WebElement interval = driver.findElement(By.id("quarter_q")); 
		WebElement include = driver.findElement(By.id("open"));
		select(interval,"Current FQ");
		select(include,"All Opportunities");
		WebElement runReport = driver.findElement(By.xpath("//input[@title='Run Report']"));
		click(runReport);
		String actual = driver.getTitle();
		Assert.assertEquals(actual,expected);
		/*
		 * if(driver.getTitle().equalsIgnoreCase(expected)) {
		 * System.out.println("Test CAse pass"); } else {
		 * System.out.println("test case fail"); } logout();
		 */
		
	}
	@Test(alwaysRun = true, priority = 20)
	
	public static void leads() throws InterruptedException {
		//tc20
		
		login();
		String expected = "Leads";
		WebElement leads = driver.findElement(By.xpath("//a[text()='Leads']"));
		waitForElementVisibility(leads);
		action(leads);
		click(leads);
		Thread.sleep(2000);
		WebElement prompt = driver.findElement(By.id("tryLexDialogX"));
		if (prompt.isEnabled()) {
			click(prompt);
			Thread.sleep(2000);
		}
		Thread.sleep(3000);
		WebElement leadPage = driver.findElement(By.xpath("//h1[text() = 'Leads']"));
		String actual = leadPage.getText();
		System.out.println("actual:" + actual);
		Assert.assertEquals(actual,expected);
		/*
		 * if(actual.equalsIgnoreCase(expected)) { System.out.println("Test case pass");
		 * }else { System.out.println("Test case failed"); }
		 */
		logout();
	
	}
	
	
	@Test (alwaysRun = true, priority = 21)
	public static void verifyDropDownInOppurtunities() throws InterruptedException {
		//tc21
		login();
		WebElement leads = driver.findElement(By.xpath("//a[text()='Leads']"));
		waitForElementVisibility(leads);
		action(leads);
		click(leads);
		Thread.sleep(2000);
		WebElement prompt = driver.findElement(By.id("tryLexDialogX"));
		if (prompt.isEnabled()) {
			click(prompt);
			Thread.sleep(2000);

		}
		ArrayList<String> expected = new ArrayList<String>(Arrays.asList("All Open Leads", "My Unread Leads",
				"Recently Viewed Leads", "Today's Leads", "View - Custom 1", "View - Custom 2"));
		WebElement view = driver.findElement(By.id("fcf"));
		Select select = new Select(view);
		List<WebElement> list = select.getOptions();
		ArrayList<String> actual = new ArrayList<String>();

		for (WebElement ele : list) {
			String text = ele.getText();
			System.out.println(ele.getText());
			actual.add(text);
		}
		System.out.println("*************");
		System.out.println(actual);
		System.out.println(expected);
		Assert.assertEquals(actual,expected);
//		if (expected.equals(actual)) {
//			System.out.println("Test Case passed");
//		} else {
//			System.out.println("Test case failed");
//		}
		logout();
	}
	
	
	@Test(alwaysRun = true, priority = 22)
	
	public static void myUnreadLeadsValidation() throws InterruptedException {
		//tc22
		login();
		WebElement leads = driver.findElement(By.xpath("//a[text()='Leads']"));
		waitForElementVisibility(leads);
		action(leads);
		click(leads);
		Thread.sleep(2000);
		WebElement prompt = driver.findElement(By.id("tryLexDialogX"));
		if (prompt.isEnabled()) {
			click(prompt);
			Thread.sleep(2000);

		}
		WebElement view = driver.findElement(By.id("fcf"));
		select(view,"My Unread Leads");
		String expected = "Today's Leads";
		WebElement view1 = driver.findElement(By.id("fcf"));
		String actual = view1.getText(); 
		Assert.assertEquals(actual,expected);
		/*
		 * if(actual.equals(expected)) { System.out.println("Test Case Pass"); }else {
		 * System.out.println("test case fail"); }
		 */
	}
	
	
	@Test(alwaysRun = true, priority = 23)
	
	public static void todaysLeads() throws InterruptedException {
		//tc23
		
		login();
		WebElement leads = driver.findElement(By.xpath("//a[text()='Leads']"));
		waitForElementVisibility(leads);
		action(leads);
		click(leads);
		Thread.sleep(2000);
		WebElement prompt = driver.findElement(By.id("tryLexDialogX"));
		if (prompt.isEnabled()) {
			click(prompt);
			Thread.sleep(2000);

		}
		String expected = "Today's Leads";
		WebElement view = driver.findElement(By.id("fcf"));
		String actual =returnSelectString(view,"Today's Leads");
		System.out.println("actual" + actual);
		Assert.assertEquals(actual,expected);
		/*
		 * if(actual.equals(expected)) { System.out.println("Test Case Pass"); }else {
		 * System.out.println("test case fail"); }
		 */
		logout();
	}

	@Test(alwaysRun = true, priority = 24)
	
	public static void NewLeadCreation() throws InterruptedException {
		//tc24
		login();
		WebElement leads = driver.findElement(By.xpath("//a[text()='Leads']"));
		waitForElementVisibility(leads);
		action(leads);
		click(leads);
		Thread.sleep(2000);
		WebElement prompt = driver.findElement(By.id("tryLexDialogX"));
		if (prompt.isEnabled()) {
			click(prompt);
			Thread.sleep(2000);

		}
		String expected = "ABCD";
		WebElement newButton = driver.findElement(By.xpath("(//input[@title='New'])[1]"));
		click(newButton);
		WebElement lastName = driver.findElement(By.xpath("(//input[@id='name_lastlea2'])[1]"));
		sendText(lastName,"ABCD" ,"lastNAme in newLeadCreation");
		WebElement company = driver.findElement(By.xpath("(//input[@id='lea3'])[1]"));
		sendText(company,"ABCD" ,"company in newLeadCreation");
		WebElement save = driver.findElement(By.xpath("(//input[@title='Save'])[2]"));
		click(save);
		WebElement header = driver.findElement(By.xpath("//h2[normalize-space()='ABCD']"));
		String actual =header.getText();
		System.out.println("Actual String:" + actual);
		if (actual.equals(expected)) {
			logger.info("testcase 24 passed");
			System.out.println("NewLeadCreation test case pass");
			extentReport.logTestpassed("NewLeadCreation");
			WebElement delete = driver.findElement(By.xpath("(//input[@title='Delete'])[1]"));
			click(delete);
			driver.switchTo().alert().accept();
			
		}else {
			logger.info("testcase 24 passed");
			System.out.println("NewLeadCreation test case fail");
			extentReport.logTestFailed("NewLeadCreation");
		}
		Thread.sleep(4000);
		logout();
	
	}
	
	
	@Test(alwaysRun = true, priority = 25)
	public static void createNewContact() throws InterruptedException {
		//tc25
		login();
		WebElement contacts = driver.findElement(By.xpath("//a[text()='Contacts']"));
		waitForElementVisibility(contacts);
		action(contacts);
		click(contacts);
		Thread.sleep(2000);
		WebElement prompt = driver.findElement(By.id("tryLexDialogX"));
		if (prompt.isEnabled()) {
			click(prompt);
			Thread.sleep(2000);

		}
		WebElement newButton = driver.findElement(By.xpath("(//input[@title='New'])[1]"));
		click(newButton);
		WebElement lastName = driver.findElement(By.id("name_lastcon2"));
		sendText(lastName,"Huragi" ,"last name in create new contact");
		WebElement acctName = driver.findElement(By.id("con4"));
		sendText(acctName,"chaithra","Account Name ");
		WebElement save = driver.findElement(By.xpath("(//input[@title='Save'])[2]"));
		click(save);
		WebElement title = driver.findElement(By.xpath("//h2[@class ='topName']"));
		if(title.isEnabled()) {
			logger.info("testcase 25 passed");
			System.out.println("createNewContact test case pass");
			extentReport.logTestpassed("createNewContact");
			WebElement delete = driver.findElement(By.xpath("//input[@value = 'Delete']"));
			click(delete);
			driver.switchTo().alert().accept();
		}else {
			logger.info("testcase 25 passed");
			System.out.println("createNewContact test case pass");
			extentReport.logTestFailed("createNewContact");
		}
		logout();

	}
	
	@Test(alwaysRun = true, priority = 26)
	
	public static void createNewViewinContacts() throws InterruptedException {
		//tc26
		login();
		WebElement contacts = driver.findElement(By.xpath("//a[text()='Contacts']"));
		waitForElementVisibility(contacts);
		action(contacts);
		click(contacts);
		Thread.sleep(2000);
		WebElement prompt = driver.findElement(By.id("tryLexDialogX"));
		if (prompt.isEnabled()) {
			click(prompt);
			Thread.sleep(2000);

		}
		WebElement newViewButton = driver.findElement(By.xpath("//a[normalize-space()='Create New View']"));
		click(newViewButton);
		WebElement viewName = driver.findElement(By.xpath("//input[@id='fname']"));
		
		sendText(viewName,"automation" ,"view name in contacts");
		String expected = "automation";
		System.out.println("Expected:"+ expected);
		WebElement viewUniqueName = driver.findElement(By.xpath("//input[@id='devname']"));
		clear(viewUniqueName);
		sendText(viewUniqueName,"Tekarch","unique name in contact view");
		WebElement save = driver.findElement(By.xpath("(//input[@title='Save'])[2]"));
		click(save);
		Thread.sleep(3000);
		WebElement selectoption = driver.findElement(By.xpath("//select[@name='fcf']"));
		Select ob = new Select(selectoption);
		WebElement selected = ob.getFirstSelectedOption();
		String actual = selected.getText();
		System.out.println("Actual:"+ actual);
		Assert.assertEquals(actual,expected);
		/*
		 * if(actual.equals(expected)) { System.out.println("Test case pass"); }else {
		 * System.out.println("Test case fail"); } l
		 */
		logout();
	}

	
	@Test(alwaysRun = true, priority = 27)
	public static void recentlyCreated() throws InterruptedException {
		//tc27
		login();
		WebElement contacts = driver.findElement(By.xpath("//a[text()='Contacts']"));
		waitForElementVisibility(contacts);
		action(contacts);
		click(contacts);
		Thread.sleep(2000);
		WebElement prompt = driver.findElement(By.id("tryLexDialogX"));
		if (prompt.isEnabled()) {
			click(prompt);
			Thread.sleep(2000);

		}
		WebElement recentlyCreated = driver.findElement(By.xpath("//select[@id ='hotlist_mode']"));
		select(recentlyCreated,"Recently Created");
		WebElement hotlist = driver.findElement(By.xpath("//div[@class='hotListElement']"));
		if(hotlist.isEnabled()) {
			logger.info("recentlyCreated pass");
			extentReport.logTestpassed("recentlyCreated");
			System.out.println("Testcase 27 pass");
		}else {
			System.out.println("Test case 27 fail");
			logger.info("recentlyCreated fail");
			extentReport.logTestpassed("recentlyCreated");
		}
		logout();
	}
	
	
	@Test (alwaysRun = true, priority = 28)
	public static void myContactView() throws InterruptedException {
		//tc28
		login();
		WebElement contacts = driver.findElement(By.xpath("//a[text()='Contacts']"));
		waitForElementVisibility(contacts);
		action(contacts);
		click(contacts);
		Thread.sleep(2000);
		WebElement prompt = driver.findElement(By.id("tryLexDialogX"));
		if (prompt.isEnabled()) {
			click(prompt);
			Thread.sleep(2000);

		}
		WebElement myContacts = driver.findElement(By.id("fcf"));
		select(myContacts,"My Contacts");
		Thread.sleep(2000);
		WebElement contacts1 = driver.findElement(By.xpath("//div[@class='pbBody']"));
		if(contacts1.isEnabled()) {
			logger.info("myContactView pass");
			extentReport.logTestpassed("myContactView()");
			System.out.println("Testcase 28 pass");
		}else {
			System.out.println("Test case 28 fail");
			logger.info("myContactView fail");
			extentReport.logTestpassed("myContactView");
		}
		logout();

	}
	
	
	@Test (alwaysRun = true, priority = 29)
	public static void myContactViewInContacts() throws InterruptedException {
		//tc29
		login();
		WebElement contacts = driver.findElement(By.xpath("//a[text()='Contacts']"));
		waitForElementVisibility(contacts);
		action(contacts);
		click(contacts);
		Thread.sleep(2000);
		WebElement prompt = driver.findElement(By.id("tryLexDialogX"));
		if (prompt.isEnabled()) {
			click(prompt);
			Thread.sleep(2000);

		}
		WebElement myContacts = driver.findElement(By.id("fcf"));
		select(myContacts,"My Contacts");
		WebElement contactName = driver.findElement(By.xpath("//*[@id='bodyCell']/div[3]/div[1]/div/div[2]/table/tbody/tr[2]/th/a"));
		//String expected = contactName.getText();
		//System.out.println("Expected:" + expected);
		click(contactName);
		Thread.sleep(2000);
		WebElement topName = driver.findElement(By.xpath("//*[@id=\"contactHeaderRow\"]/div[2]/h2"));
		String actual = topName.getText();
		System.out.println("actual : " + actual);
		//click(topName);
		
		if(topName.isEnabled()) {
			logger.info("myContactViewInContacts pass");
			extentReport.logTestpassed("myContactViewInContacts");
			System.out.println("Testcase 29 pass");
		}else {
			System.out.println("Test case 29 fail");
			logger.info("myContactViewInContacts fail");
			extentReport.logTestpassed("myContactViewInContacts");
		}
				logout();
	}
	

	@Test (alwaysRun = true, priority = 30)
	public static void createNewViewInContacts() throws InterruptedException {
		//tc30
		login();
		WebElement contacts = driver.findElement(By.xpath("//a[text()='Contacts']"));
		waitForElementVisibility(contacts);
		action(contacts);
		click(contacts);
		Thread.sleep(2000);
		WebElement prompt = driver.findElement(By.id("tryLexDialogX"));
		if (prompt.isEnabled()) {
			click(prompt);
			Thread.sleep(2000);

		}
		WebElement newViewButton = driver.findElement(By.xpath("//a[normalize-space()='Create New View']"));
		click(newViewButton);
		WebElement viewUniqueName = driver.findElement(By.xpath("//input[@id='devname']"));
		clear(viewUniqueName);
		sendText(viewUniqueName,"efgh","unique name in contact view");
		WebElement save = driver.findElement(By.xpath("(//input[@title='Save'])[2]"));
		click(save);
		Thread.sleep(3000);
		WebElement error = driver.findElement(By.xpath("(//div[@class='errorMsg'])[1]"));
		if(error.isEnabled()) {
			logger.info("createNewViewInContacts pass");
			extentReport.logTestpassed("createNewViewInContacts");
			System.out.println("Testcase 30 pass");
		}else {
			System.out.println("Test case 30 fail");
			logger.info("createNewViewInContacts fail");
			extentReport.logTestpassed("createNewViewInContacts");
		}
		logout();
	}
	

	@Test(alwaysRun = true, priority = 31)
	public static void cancelCreateNewView() throws InterruptedException {
		//tc31
		login();
		WebElement contacts = driver.findElement(By.xpath("//a[text()='Contacts']"));
		waitForElementVisibility(contacts);
		action(contacts);
		click(contacts);
		Thread.sleep(2000);
		WebElement prompt = driver.findElement(By.id("tryLexDialogX"));
		if (prompt.isEnabled()) {
			click(prompt);
			Thread.sleep(2000);

		}
		WebElement newViewButton = driver.findElement(By.xpath("//a[normalize-space()='Create New View']"));
		click(newViewButton);
		WebElement viewName = driver.findElement(By.xpath("//input[@id='fname']"));
		
		sendText(viewName,"ABCD" ,"view name in contacts");
		WebElement viewUniqueName = driver.findElement(By.xpath("//input[@id='devname']"));
		clear(viewUniqueName);
		sendText(viewUniqueName,"EFGH","unique name in contact view");
		WebElement cancel = driver.findElement(By.xpath("//input[@value='Cancel']"));
		click(cancel);
		Thread.sleep(3000);
		WebElement contacts1 = driver.findElement(By.xpath("//h2[text ()= ' Home']"));
		if(contacts1.isEnabled()) {
			logger.info("cancelCreateNewView");
			extentReport.logTestpassed("cancelCreateNewView");
			System.out.println("Testcase 31 pass");
		}else {
			System.out.println("Test case 31 fail");
			logger.info("cancelCreateNewView");
			extentReport.logTestpassed("cancelCreateNewView");
		}
		logout();
	}
	

	@Test (alwaysRun = true, priority = 32)
	public static void createNewContactForInvalid() throws InterruptedException {
	//tc32
		login();
		WebElement contacts = driver.findElement(By.xpath("//a[text()='Contacts']"));
		waitForElementVisibility(contacts);
		action(contacts);
		click(contacts);
		Thread.sleep(2000);
		WebElement prompt = driver.findElement(By.id("tryLexDialogX"));
		if (prompt.isEnabled()) {
			click(prompt);
			Thread.sleep(2000);

		}WebElement newButton = driver.findElement(By.xpath("(//input[@title='New'])[1]"));
		click(newButton);
		WebElement lastName = driver.findElement(By.id("name_lastcon2"));
		sendText(lastName,"Indian" ,"last name in create new contact");
		WebElement acctName = driver.findElement(By.id("con4"));
		sendText(acctName,"Global Media","Account Name ");
		WebElement save = driver.findElement(By.xpath("//input[@value='Save & New']"));
		click(save);
		WebElement error = driver.findElement(By.xpath("//div[@class='errorMsg']"));
		if(error.isEnabled()) {
			logger.info("createNewContactForInvalid");
			extentReport.logTestpassed("createNewContactForInvalid");
			System.out.println("Testcase 32 pass");
		}else {
			System.out.println("Test case 32 fail");
			logger.info("createNewContactForInvalid");
			extentReport.logTestpassed("createNewContactForInvalid");
		}
		logout();
		
	}

	@Test (alwaysRun = true, priority = 33)
	public static void validateHome() throws InterruptedException {
		//tc33
		login();
		WebElement home = driver.findElement(By.xpath("//a[text()='Home']"));
		waitForElementVisibility(home);
		action(home);
		click(home);
		WebElement prompt = driver.findElement(By.id("tryLexDialogX"));
		if (prompt.isEnabled()) {
			click(prompt);
			Thread.sleep(2000);
		}
		WebElement myProfile = driver.findElement(By.xpath("//h1/a[@href='/_ui/core/userprofile/UserProfilePage']"));
		 String expected = myProfile.getText();
		 System.out.println("Expected:" + expected);
		 click(myProfile);
		 WebElement userMenu = driver.findElement(By.id("userNavLabel"));
		 action(userMenu);
		 click(userMenu);
		 WebElement userMenuMyProfile = driver.findElement(By.xpath("//a[text() ='My Profile']"));
		 click(userMenuMyProfile);
		 WebElement profilename = driver.findElement(By.id("tailBreadcrumbNode"));
		 String actual = profilename.getText().trim();
		 System.out.println("actual:" + actual);
		 Assert.assertEquals(actual,expected);
		 if(actual.equals(expected)) {
			 System.out.println("test case pass");
		 }else {
			 System.out.println("Test case fail");
		 }
		 logout();
	}
	

	@Test(alwaysRun = true, priority = 34)
	public static void validateHomeProfileName() throws InterruptedException {
		//tc34
		login();
		WebElement home = driver.findElement(By.xpath("//a[text()='Home']"));
		waitForElementVisibility(home);
		action(home);
		click(home);
		WebElement prompt = driver.findElement(By.id("tryLexDialogX"));
		if (prompt.isEnabled()) {
			click(prompt);
			Thread.sleep(2000);
		}
		String expected = "Chaithra abcd";
		 WebElement myProfile = driver.findElement(By.xpath("//h1/a[@href='/_ui/core/userprofile/UserProfilePage']"));
		 click(myProfile);
		 WebElement editProfile =driver.findElement(By.xpath("//a[@class='contactInfoLaunch editLink']"));
			waitForElementVisibility(editProfile);
			click(editProfile);
			Thread.sleep(4000);
			driver.switchTo().frame("contactInfoContentId");
			Thread.sleep(3000);
			WebElement about = driver.findElement(By.partialLinkText("About"));
			click(about);
			WebElement lastName = driver.findElement(By.id("lastName"));
			lastName.clear();
			sendText(lastName, "abcd", "LastNAme");
			WebElement saveAll = driver.findElement(By.xpath("//input[@value='Save All']"));
			click(saveAll);
			 WebElement profilename = driver.findElement(By.id("tailBreadcrumbNode"));
			 String actual = profilename.getText().trim();
			 System.out.println("actual:" + actual);
			 Assert.assertEquals(actual,expected);
			 if(actual.equals(expected)) {
				 System.out.println("test case pass");
			 }else {
				 System.out.println("Test case fail");
			 }
			 logout();
	
	}
	

	
	@Test (alwaysRun = true, priority = 35)
	public static void RemoveTab() throws InterruptedException {
		//tc35
		login();
		WebElement alltab = driver.findElement(By.xpath("//img[@title ='All Tabs']"));
		waitForElementVisibility(alltab);
		action(alltab);
		click(alltab);
		Thread.sleep(2000);
		WebElement customize = driver.findElement(By.xpath("//input[@value='Customize My Tabs']"));
		click(customize);
		Thread.sleep(2000);
		WebElement selectTab = driver.findElement(By.xpath("//select[@id='duel_select_1']"));
		//click(selectTab);
		Thread.sleep(4000);
		select(selectTab,"Assets");
		WebElement remove = driver.findElement(By.xpath("//img[@alt ='Remove']"));
		click(remove);
		WebElement save = driver.findElement(By.xpath("//input[@value=' Save ']"));
		click(save);
		logout();
		Thread.sleep(4000);	//	close();
	}
	@Test(alwaysRun = true, priority = 35,dependsOnMethods = { "RemoveTab" })
	
		public static void verifyRemoveTab() throws InterruptedException {
		
		login();
		WebElement alltab1 = driver.findElement(By.xpath("//img[@title ='All Tabs']"));
		waitForElementVisibility(alltab1);
		action(alltab1);
		click(alltab1);
		Thread.sleep(2000);
		String tabRemoved = "Assets";
		WebElement customize1 = driver.findElement(By.xpath("//input[@value='Customize My Tabs']"));
		click(customize1);
		Thread.sleep(2000);
		WebElement selectTab1 = driver.findElement(By.xpath("//select[@id='duel_select_1']"));
		click(selectTab1);
		Select ob = new Select(selectTab1);
		List<WebElement> str = ob.getAllSelectedOptions();
		for(WebElement ele : str) {
			String actual = ele.getText();
			if(actual.equals(tabRemoved)) {
				System.out.println("test case fail");
				logger.info("verifyRemoveTab");
				extentReport.logTestpassed("verifyRemoveTab");
				break;
			}
			
		}
		System.out.println("test case pass");
		extentReport.logTestpassed("verifyRemoveTab");
		System.out.println("Testcase 36 pass");
		WebElement availabletabs= driver.findElement(By.xpath("//select[@id='duel_select_0']"));
		select( availabletabs,"Assets");
		WebElement add = driver.findElement(By.xpath("//img[@alt ='Add']"));
		click(add);
		WebElement save1 = driver.findElement(By.xpath("//input[@value=' Save ']"));
		click(save1);
	}
		
	
	@Test(alwaysRun = true, priority = 36)
	public static void todaysDateLink() throws InterruptedException {
		//tc36
		login();
		WebElement home = driver.findElement(By.xpath("//a[text()='Home']"));
		waitForElementVisibility(home);
		action(home);
		click(home);
		Thread.sleep(3000);
		WebElement prompt = driver.findElement(By.id("tryLexDialogX"));
		if (prompt.isEnabled()) {
			click(prompt);
			Thread.sleep(2000);
		}
		WebElement today = driver.findElement(By.xpath("//td/a[@href='/00U/c?md0=2023&md3=42']"));
		click(today);
		WebElement eightpm = driver.findElement(By.xpath("//a[normalize-space()='8:00 PM']"));
		click(eightpm);
		WebElement subjectimg = driver.findElement(By.xpath("//img[@title='Subject Combo (New Window)']"));
		click(subjectimg);
		String baseWindowHandle = driver.getWindowHandle();
		Set<String> allWindowHandles = driver.getWindowHandles();
		for (String handle : allWindowHandles) {
			if (!baseWindowHandle.equals(handle))
				driver.switchTo().window(handle);
		}
		WebElement other = driver.findElement(By.xpath("//li[@class='listItem4']//a[1]"));
		click(other);
		Thread.sleep(3000);
		driver.switchTo().window(baseWindowHandle);
		WebElement end = driver.findElement(By.xpath(
				"/html/body/div[1]/div[2]/table/tbody/tr/td[2]/form/div[1]/div[2]/div[4]/table/tbody/tr[3]/td[4]/div/span/span/input"));
		click(end);

		WebElement ninepm = driver.findElement(By.xpath("//*[@id='timePickerItem_42']"));
		click(ninepm);
		WebElement save = driver.findElement(By.xpath("//*[@id='bottomButtonRow']/input[1]"));
		click(save);
		WebElement calender = driver.findElement(By.xpath(
				"//body[1]/div[1]/div[2]/table[1]/tbody[1]/tr[1]/td[2]/div[1]/table[1]/tbody[1]/tr[1]/td[1]/form[1]/div[1]/div[1]/div[1]/table[1]/tbody[1]/tr[2]/td[2]/div[29]/span[1]/div[1]/div[1]/div[1]/span[1]/a[1]/span[1]"));
		if (calender.isEnabled()) {
			logger.info("todaysDateLink");
			extentReport.logTestpassed("todaysDateLink");
			System.out.println("Testcase 36 pass");
		}else {
			System.out.println("Test case 36 fail");
			logger.info("todaysDateLink");
			extentReport.logTestpassed("todaysDateLink");
		}
		logout();
	}
	
	
	
	
	@Test(alwaysRun = true, priority = 37)
	public static void todaysDateLinkRecurssive() throws InterruptedException {
		login();
		WebElement home = driver.findElement(By.xpath("//a[text()='Home']"));
		waitForElementVisibility(home);
		action(home);
		click(home);
		Thread.sleep(3000);
		WebElement prompt = driver.findElement(By.id("tryLexDialogX"));
		if (prompt.isEnabled()) {
			click(prompt);
			Thread.sleep(2000);
		}
		WebElement today = driver.findElement(By.xpath("//td/a[@href='/00U/c?md0=2023&md3=42']"));
		click(today);
		WebElement fourpm = driver.findElement(By.xpath("//a[normalize-space()='4:00 PM']"));
		click(fourpm);
		WebElement subjectimg = driver.findElement(By.xpath("//img[@title='Subject Combo (New Window)']"));
		click(subjectimg);
		String baseWindowHandle = driver.getWindowHandle();
		Set<String> allWindowHandles = driver.getWindowHandles();
		for (String handle : allWindowHandles) {
			if (!baseWindowHandle.equals(handle))
				driver.switchTo().window(handle);
		}
		WebElement other = driver.findElement(By.xpath("//li[@class='listItem4']//a[1]"));
		click(other);
		Thread.sleep(3000);
		driver.switchTo().window(baseWindowHandle);
		WebElement end = driver.findElement(By.xpath(
				"/html/body/div[1]/div[2]/table/tbody/tr/td[2]/form/div[1]/div[2]/div[4]/table/tbody/tr[3]/td[4]/div/span/span/input"));
		click(end);

		WebElement sevenpm = driver.findElement(By.xpath("//*[@id='timePickerItem_40']"));
		click(sevenpm);
		
		WebElement isreoccurance = driver.findElement(By.name("IsRecurrence"));
		click(isreoccurance);
		WebElement weekly = driver.findElement(By.id("rectypeftw"));
		click(weekly);
		WebElement recursevery = driver.findElement(By.id("wi"));
		//clear(recursevery);
		sendText(recursevery, "2","recurse every 2 weeks ");
		WebElement enddate = driver.findElement(By.id("RecurrenceEndDateOnly"));
		click(enddate);
		WebElement selectDate = driver.findElement(By.xpath("//td[normalize-space()='26']"));
		click(selectDate);
		WebElement save = driver.findElement(By.xpath("//*[@id='bottomButtonRow']/input[1]"));
		click(save);
		WebElement calender = driver.findElement(By.xpath(
				"//body[1]/div[1]/div[2]/table[1]/tbody[1]/tr[1]/td[2]/div[1]/table[1]/tbody[1]/tr[1]/td[1]/form[1]/div[1]/div[1]/div[1]/table[1]/tbody[1]/tr[2]/td[2]/div[29]/span[1]/div[1]/div[1]/div[1]/span[1]/a[1]/span[1]"));
		if (calender.isEnabled()) {
			logger.info("todaysDateLinkRecurssive");
			extentReport.logTestpassed("todaysDateLinkRecurssive");
			System.out.println("Testcase 37 pass");
		}else {
			System.out.println("Test case 37 fail");
			logger.info("todaysDateLinkRecurssive");
			extentReport.logTestpassed("todaysDateLinkRecurssive");
		}
		logout();
		
	}
	}
	
	
		
	
		
	
	
	
	
	



