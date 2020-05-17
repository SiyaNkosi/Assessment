package com.iLab.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class ReadConfig 
{
	
	Properties prop;  //object of the properties class
	
	public ReadConfig()
	{
		File filesrc = new File("./Configuration/Config.properties");
		
		try 
		{
			
			FileInputStream fis = new FileInputStream(filesrc);
			prop = new Properties();
			prop.load(fis);
			
		}
		catch(Exception e)
		{
			System.out.println("Failed To Open File. Exception : " + e.getMessage() );
			
		}
	}
	
	public String getSiteURL()
	{
		String siteURL = prop.getProperty("siteURL");
		return siteURL;
	}
	
	public String getChromeBrowserPath()
	{
		String chromePath = prop.getProperty("chromeBrowser");
		return chromePath;
	}
	
	public String getIEBrowserPath()
	{
		String IEPath = prop.getProperty("IEBrowser");
		return IEPath;
	}
	
	public String getFireFoxBrowserPath()
	{
		String ffPath = prop.getProperty("fireFoxBrowser");
		return ffPath;
	}
	
	public String getTestDataLocationPath()
	{
		String tdLocationPath = prop.getProperty("testDataLocationPath");
		return tdLocationPath;
	}
	
	
	public String getConnectionString()
	{
		String conString = prop.getProperty("ConnectionString");
		return conString;
	}

}
