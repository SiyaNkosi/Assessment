package com.iLab.pageObjects;

import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class ApplyOnline  {
	
	
	 WebDriver ldriver;
	
	//Contractor 
	 public ApplyOnline(WebDriver rdriver) 
	 {
		
		ldriver = rdriver;
		
		PageFactory.initElements(rdriver, this);		// all elements will get initialized
		
	 }
	 
	
	 @FindBy(xpath="//div[@id='popmake-3738']//button")
	 @CacheLookup
	 public WebElement lnkClosePopUp;
	 
	 
	 @FindBy(xpath="//li[@id='menu-item-1373']//a[text()='CAREERS']")
	 @CacheLookup
	 public WebElement lnkCarees;
	 
	 @FindBy(linkText = "South Africa")
	 @CacheLookup
	 public WebElement lnkRegion;
	 
	 @FindBy(xpath = "//div[@class='wpjb-line-major']/a[1]")
	 @CacheLookup
	 public List<WebElement> lnkJobsAvailable;
	 
	 @FindBy (partialLinkText = "Apply Online")
	 @CacheLookup
	 public WebElement btnApplyOnline;
	 
	 @FindBy(id = "applicant_name")
	 @CacheLookup
	 public WebElement txtName;
	 
	 @FindBy(id = "email")
	 @CacheLookup
	 public WebElement txtEmail;
	 
	 @FindBy(id = "phone")
	 @CacheLookup
	 public WebElement txtPhoneNo;
	 
	 @FindBy(id = "wpjb_submit")
	 @CacheLookup
	 public WebElement btnSendApplication;
	 
	 @FindBy(xpath = "//ul[@class='wpjb-errors']/li")
	 @CacheLookup
	 public WebElement lblError;
	 
	 public WebElement firstJobOnTheList() 
	 {
		return lnkJobsAvailable.get(1);
		
	 }
	 
	 public String getErrorText() 
	 {
		
		 return lblError.getText();
	}
	 

}
