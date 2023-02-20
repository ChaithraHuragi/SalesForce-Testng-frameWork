package com.tekarch.utilityforsaleforce;
import com.tekarch.SalesForce.*;
import com.tekarch.base.BaseClass;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestEventListners implements ITestListener {
	protected static ExtentReportsUtility extentReport=null;
	protected WebDriver driver;
	
	@Override
	public void onTestStart(ITestResult result) {
		extentReport.startSingleTestReport(result.getMethod().getMethodName());
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		extentReport.logTestpassed(result.getMethod().getMethodName());
	}

	@Override
	public void onTestFailure(ITestResult result) {
		extentReport.logTestFailed(result.getMethod().getMethodName());
		BaseClass ob=new BaseClass();
		driver=ob.returnDriverInstance();
		String path;
		try {
			path = ob.getScreenshot(driver);
			extentReport.logTestScreenShot(path);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	

	@Override
	public void onStart(ITestContext context) {
		extentReport = ExtentReportsUtility.getInstance();
		extentReport.startExtentReport();
	}

	@Override
	public void onFinish(ITestContext context) {
		extentReport.endReport();
	}

}
