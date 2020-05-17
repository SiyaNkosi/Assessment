package com.iLab.utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.iLab.testCases.BaseClass;
public class SeleniumActions 
{
	public int shortTimeout = 30;
	
	Reporting rep = new Reporting();
	
	public boolean waitForElementToBeVisible(WebElement element,String elementDescription)
	{
		
		try 
		{
			
			WebDriverWait wait=new WebDriverWait(BaseClass.driver,shortTimeout);
			
			if (wait.until(ExpectedConditions.visibilityOf(element)) != null) {
				
				return true;
			} 
			
			return false;
			
		}
		catch (Exception e) 
		{
			Reporting.addFAILLogUnderCurrentOnExtentReport("Failed to find '" + elementDescription+"'. Check console for Exception!");
			System.out.println(e.getMessage());
			return false;
			
		}
		
	}
	
	public boolean waitForElementByTextToBeVisible(String elementText,String elementDescription)
	{
		
		try 
		{
			
			WebDriverWait wait=new WebDriverWait(BaseClass.driver,shortTimeout);
			
			if (wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(elementText))) != null) {
				
				return true;
			} 
			
			return false;
			
		}
		catch (Exception e) 
		{
			Reporting.addFAILLogUnderCurrentOnExtentReport("Failed to find '" + elementDescription+"'. Check console for Exception!");
			System.out.println(e.getMessage());
			return false;
			
		}
		
	}
	
	public boolean clickElement(WebElement element,String elementDescription)
	{
		try 
		{	
			//look for element if it exists
			if (waitForElementToBeVisible(element,elementDescription)) {
				
				element.click();
				
				Reporting.addINFOLogUnderCurrentOnExtentReport("Successfully clicked '" + elementDescription +"'");
				
				return true;
			}
			
			
			//if element not found add a fail log n=to the report
			Reporting.addINFOLogUnderCurrentOnExtentReport("Failed to click '" + elementDescription +"' because it does not exist");
			Assert.fail();
			return false;
			
		} 
		catch (Exception e) 
		{
			Reporting.addFAILLogUnderCurrentOnExtentReport("Failed to click '" + elementDescription+"' Exception :" + e.getMessage());
			
			//e.printStackTrace();
			Assert.fail();
			return false;
		}
		
	}
	
	public boolean clickElementByText(String elementText,String elementDescription)
	{
		try 
		{	
			//look for element if it exists
			if (waitForElementByTextToBeVisible(elementText, elementDescription)) {
				
				
				
				BaseClass.driver.findElement(By.linkText(elementText)).click();
				
				Reporting.addINFOLogUnderCurrentOnExtentReport("Successfully clicked '" + elementDescription +"'");
				
				return true;
			}
			
			
			//if element not found add a fail log n=to the report
			Reporting.addINFOLogUnderCurrentOnExtentReport("Failed to click '" + elementDescription +"' because it does not exist");
			Assert.fail();
			return false;
			
		} 
		catch (Exception e) 
		{
			Reporting.addFAILLogUnderCurrentOnExtentReport("Failed to click '" + elementDescription+"' Exception :" + e.getMessage());
			
			//e.printStackTrace();
			Assert.fail();
			return false;
		}
		
	}

	public boolean enterTextOnElement(WebElement element,String value,String elementDescription)
	{
		try 
		{
			if (waitForElementToBeVisible(element, elementDescription)) 
			{
				
				element.sendKeys(value);
				
				Reporting.addINFOLogUnderCurrentOnExtentReport("Successfully entered '"+ value +"' on the '" + elementDescription +"'");
				
				return true;
				
			}
			
			Reporting.addFAILLogUnderCurrentOnExtentReport("Failed to enter text '"+ value +"' because '" + elementDescription+"' does not exist");
			
			return false;
			
		} catch (Exception e) 
		{
			Reporting.addFAILLogUnderCurrentOnExtentReport("Failed to enter text '"+ value +"' on '" + elementDescription+"' Exception :" + e.getMessage());
			
			return false;
		}
		
	}
	
	public void scrollToElement(WebElement element)
	{
		((JavascriptExecutor)BaseClass.driver).executeScript("arguments[0].scrollIntoView();", element);
	}
	
	
	public void waitForElementToBeClickable(WebElement element)
	{
//		WebDriverWait wait2 = new WebDriverWait(driver,60);
//		
//		wait2.until(ExpectedConditions.elementToBeClickable(element));
		
	}

}
