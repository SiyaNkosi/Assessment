package com.iLab.testCases;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.aventstack.extentreports.Status;
import com.iLab.pageObjects.ApplyOnline;
import com.iLab.utilities.Reporting;
import com.iLab.utilities.SQLUtility;
import com.iLab.utilities.XLUtils;


public class TC_ApplyOnline_ValidateUploadErrorMsg extends BaseClass
{
	@DataProvider(name="ValidateErrorMsg")
	public String[][] getTestData() throws IOException, SQLException
	{
		
		String workBookName = "TC_ApplyOnline_ValidateUploadErrorMsg.xlsx";
		String sheetName = "Sheet1";
		String runQuery = "Yes";
		
		//check if condition to run a query for data is 'Yes'
		if (runQuery.equals("Yes") ) 
		{			
			ResultSet qResults;
			
			//get query from spreadsheet
			String strQueryFromSheet = XLUtils.getCellData(testDataLocationPathFromConfig+workBookName, "Query", 1,1) ;
			
			qResults =  SQLUtility.getQueryResults(connectionStringFromConfig, strQueryFromSheet);
			
			//get data from results
			//String strResultName = qResults.getString("Name");
			//String strResultEmail = qResults.getString("Email");
			//String strResultRegion = qResults.getString("Region");
			
			//mock data
			String strResultName = "Mock Name";
			String strResultEmail = "MockEmail@gmail.com";
			String strResultRegion ="South Africa";
			
			//write result data on the spreadsheet
			XLUtils.setCellData(testDataLocationPathFromConfig+workBookName, sheetName, 2, 1, strResultName);
			XLUtils.setCellData(testDataLocationPathFromConfig+workBookName, sheetName, 2, 2, strResultEmail);
			XLUtils.setCellData(testDataLocationPathFromConfig+workBookName, sheetName, 2, 0, strResultRegion);
			
		}
		
		//get number of columns and rows from test data spreadsheet
		int intRowCount = XLUtils.getRowCount(testDataLocationPathFromConfig+workBookName, sheetName);
		int intColCount = XLUtils.getCellCount(testDataLocationPathFromConfig+workBookName, sheetName, intRowCount);
		
		String testData[][] = new String[intRowCount][intColCount];
		
		//get column row data
		//loop rows
		for (int i = 1; i <= intRowCount ; i++) 
		{	
			//loop columns
			for (int j = 0; j < intColCount ; j++) 
			{
				//put sheet data on an array
				testData[i-1][j] = XLUtils.getCellData(testDataLocationPathFromConfig+workBookName, sheetName, i, j);
				
			}
			
		}
		
		//return test data array to create data table -  DDT
		return testData;
	}
	
	@Test(dataProvider = "ValidateErrorMsg")
	public void ValidateUploadErrorMessage(String strRegion,String strName,String strEmail,String strErrorMsgExpected) throws InterruptedException, IOException
	{
		
		//open browser and navigate to site
		openBrowser();
		
		//access page objects
		ApplyOnline AO= new ApplyOnline(driver);
		
		//create test on extent report
		Reporting.createTestLog("ValidateUploadErrorMessage");
		
		//add step node
		Reporting.addStepToExtentReport("Home Page");
		
		//add home page screen shot to report
		Reporting.addLogPASSUnderCurrentOnExtentReportWithScreenShot("Home Page", "Home Page");
		

		//close pop up
		seleniumActionsUtil.clickElement(AO.lnkClosePopUp , "Home Page close Pop UP");
		
		//stability wait
		Thread.sleep(3000);
		
		
		//click Careers
		seleniumActionsUtil.clickElement(AO.lnkCarees, "Careers Hyperlink");
		
		//Add step on the report
		Reporting.addStepToExtentReport("Careers Page");
				
		//add careers page screen shot to report		
		Reporting.addLogPASSUnderCurrentOnExtentReportWithScreenShot("Careers Page", "Careers Page");
		
		
		//click on region dynamically
		seleniumActionsUtil.clickElementByText(strRegion, strRegion + " Region");
		
		
		//select first job from job lists
		seleniumActionsUtil.clickElement(AO.firstJobOnTheList(), "First job on the jobs list");
		
		
		//click apply online
		seleniumActionsUtil.clickElement(AO.btnApplyOnline, "Apply Online Button");
			
		//add apply online step to report
		Reporting.addStepToExtentReport("Apply Online");
		
		//scroll to element for screenshot
		seleniumActionsUtil.scrollToElement(AO.txtName);
		
		//add screenshot
		Reporting.addLogPASSUnderCurrentOnExtentReportWithScreenShot("Apply Online","ApplyOnline");
		
		
		//set name
		seleniumActionsUtil.enterTextOnElement(AO.txtName, strName, "Name textbox");
		
		
		//set email
		seleniumActionsUtil.enterTextOnElement(AO.txtEmail, strEmail, "Email textbox");
		
		//generate random phone number
		Random rand = new Random();
		
		String strCode = "072";
		int intTemp1 = rand.nextInt(999);
		int intTemp2 = rand.nextInt(9999);
		
		String strPhone = strCode + " " + Integer.toString(intTemp1) + " " + Integer.toString(intTemp2) ;
		
		Reporting.addLogUnderCurrentOnExtentReport(Status.INFO, "Successfully generated phone '"+ strPhone +"'");
		
		//set generated phone number
		seleniumActionsUtil.enterTextOnElement(AO.txtPhoneNo, strPhone, "Phone textbox");
				
		//click submit application
		seleniumActionsUtil.clickElement(AO.btnSendApplication , "Send Application Button");
		//addLogUnderCurrentOnExtentReport(Status.INFO, "Successfully clicked Submit");
		
		//validate error message
		if (seleniumActionsUtil.waitForElementToBeVisible(AO.lblError, "Upload Error Message"))
		{
			String strErrorMsg = AO.lblError.getText();
			
			//scroll to to element for screenshot
			seleniumActionsUtil.scrollToElement(AO.lblError);
			
			
			if (strErrorMsg.equals(strErrorMsgExpected)) 
			{
				
				
				Reporting.addLogPASSUnderCurrentOnExtentReportWithScreenShot("Successfully validated upload error message <br> Expected : "+ strErrorMsgExpected +") <br> Found : "+ AO.getErrorText() , "ErrorMSG");
				
				Assert.assertTrue(true);
				
			}
			else
			{
				Reporting.addLogFAILUnderCurrentOnExtentReportWithScreenShot("Failed to validated upload error message <br> Expected : "+ strErrorMsgExpected +") <br> Found : "+ AO.getErrorText() , "ErrorMSG");
				
				Assert.assertFalse(true);

			}
		}
				
	}

}
