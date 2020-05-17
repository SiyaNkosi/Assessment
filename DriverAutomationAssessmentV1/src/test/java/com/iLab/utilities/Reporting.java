package com.iLab.utilities;

import java.io.File;

//Listener class used to generate Extent reports

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.iLab.testCases.BaseClass;


public class Reporting extends TestListenerAdapter
{
	
	public ExtentHtmlReporter htmlReporter;
	public ExtentReports extent;
	public ExtentTest logger;
	
	
	public void onStart(ITestContext testContext)
	{
		//create extent report
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());//time stamp
		String repName="Test-Report-"+timeStamp+".html";
		
		htmlReporter=new ExtentHtmlReporter(System.getProperty("user.dir")+ "/test-output/"+repName);//specify location of the report
		htmlReporter.loadXMLConfig(System.getProperty("user.dir")+ "/extent-config.xml");
		
		extent=new ExtentReports();
		
		extent.attachReporter(htmlReporter);
		extent.setSystemInfo("Environemnt","QA");
		extent.setSystemInfo("Site URL", BaseClass.siteURLFromConfig );
		extent.setSystemInfo("user","Siyabonga Nkosi");
		
		htmlReporter.config().setDocumentTitle("iLab Test Automation Report"); 	// name of the report
		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP); 		//location of the chart
		htmlReporter.config().setTheme(Theme.DARK);								//report theme
		
		
		BaseClass.htmlReporter = htmlReporter;									//
		BaseClass.extent = extent;												//
		BaseClass.logger = logger;												//
		
		System.out.println("On Start");
		
	}
	
	//when the test pass this will be executed 
	public void onTestSuccess(ITestResult tr)
	{	
		
		try 
		{
			Thread.sleep(3000);
			
			if (BaseClass.driver != null) 
			{
				BaseClass.driver.quit();
			}
		} 
		catch (InterruptedException e) 
		{

			e.printStackTrace();
		}

	}
	
	//when test fail this will be executed
	public void onTestFailure(ITestResult tr)
	{
		if (BaseClass.driver != null) 
		{
			BaseClass.driver.quit();
		}
		
		
	}
	
	
	public void onTestSkipped(ITestResult tr)
	{
		logger=extent.createTest(tr.getName()); // create new entry in the report
		logger.log(Status.SKIP,MarkupHelper.createLabel(tr.getName(),ExtentColor.ORANGE));
	}
	
	public void onFinish(ITestContext testContext)
	{
		extent.flush();
	}
	
	public static void createTestLog(String strTestName)
	{
		
		BaseClass.logger=BaseClass.extent.createTest(strTestName);
		
	}
	
	public static void addStepToExtentReport(String strStepName)
	{
		BaseClass.loggerStep = BaseClass.logger.createNode(strStepName);
	}
	
	public static void addLogUnderCurrentOnExtentReport(Status status, String strLog)
	{
		if(BaseClass.loggerStep!=null)
		{
			BaseClass.loggerStep.log(status , strLog);
		}
		else
		{
			System.out.println("Extent Node does not exist");
			BaseClass.logger.log(status , strLog);
		}
		
	}
	
	public static void addINFOLogUnderCurrentOnExtentReport( String strLog)
	{
		BaseClass.loggerStep.log(Status.INFO , strLog);
	}
	
	public static void addFAILLogUnderCurrentOnExtentReport( String strLog)
	{
		BaseClass.loggerStep.log(Status.FAIL , strLog);
	}
	
	public static void addPASSLogUnderCurrentOnExtentReport( String strLog)
	{
		BaseClass.loggerStep.log(Status.PASS , strLog);
	}
	
	public static void addLogPASSUnderCurrentOnExtentReportWithScreenShot( String strLog,String strScreenShotName) throws IOException
	{
		//capture screenshot
		captureScreen(strScreenShotName);
		
		BaseClass.loggerStep.pass( strLog ,MediaEntityBuilder.createScreenCaptureFromPath(System.getProperty("user.dir") + "\\Screenshots\\" + strScreenShotName + ".png").build() );
	}
	
	public static void addLogFAILUnderCurrentOnExtentReportWithScreenShot( String strLog,String strScreenShotName) throws IOException
	{
		//capture screenshot
		captureScreen(strScreenShotName);
		
		BaseClass.loggerStep.fail( strLog ,MediaEntityBuilder.createScreenCaptureFromPath(System.getProperty("user.dir") + "\\Screenshots\\" + strScreenShotName + ".png").build() );
	}
	
	public void addLogFailUnderCurrentOnExtentReportWithScreenShot( String strLog,String strScreenshotName) throws IOException
	{
		//capture screenshot
		captureScreen(strScreenshotName);
		BaseClass.loggerStep.fail ( strLog ,MediaEntityBuilder.createScreenCaptureFromPath(System.getProperty("user.dir") + "\\Screenshots\\" + strScreenshotName + ".png").build() );
	}
	
	public static void captureScreen(String tname) throws IOException 
	{
		TakesScreenshot ts = (TakesScreenshot) BaseClass.driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File target = new File(System.getProperty("user.dir") + "/Screenshots/" + tname + ".png");
		FileUtils.copyFile(source, target);
		//System.out.println("Screenshot taken");
	}
	
}