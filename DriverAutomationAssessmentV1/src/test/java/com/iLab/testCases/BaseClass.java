package com.iLab.testCases;



import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.iLab.utilities.ReadConfig;
import com.iLab.utilities.SeleniumActions;


public class BaseClass 
{
	
	public static WebDriver driver;
	public static ExtentHtmlReporter htmlReporter;
	public static ExtentReports extent;
	public static ExtentTest logger;
	public static ExtentTest loggerStep;
	
	public String testDataSheetNameFromSuite= "";
	public String browserTypeFromSuite;
	public String runQueryFromSuite;
	
	static ReadConfig config =new ReadConfig();  //Read Config - Open config file

	public static String siteURLFromConfig =config.getSiteURL();					//get site URL from config file
	public String chromePathFromConfig = config.getChromeBrowserPath();				//get Chrome path from config file
	public String IEPathFromConfig = config.getIEBrowserPath();						//get IE path from config file
	public String fireFoxPathFromConfig= config.getFireFoxBrowserPath();			//get FireFox browser path from config file
	public String testDataLocationPathFromConfig =config.getTestDataLocationPath();	//get Test data location from config file
	public String connectionStringFromConfig = config.getConnectionString();		//get SQL Connection string from 
		
	SeleniumActions seleniumActionsUtil = new SeleniumActions();					//Object of the Selenium Actions class to that it will be access
	
	@Parameters("browser")		//parameters from test suits xml
	@BeforeTest
	public void setup(String browserType )
	{
		//Initialize with values from the test suits xml before test														//
		browserTypeFromSuite = browserType;													//
		
	}
	
	//open browser and navigate to site
	@SuppressWarnings("deprecation")
	public void openBrowser()
	{
		if (browserTypeFromSuite.equals("chrome")) 
		{
			System.setProperty("webdriver.chrome.driver",chromePathFromConfig);
			driver = new ChromeDriver();
			
		}
		else if (browserTypeFromSuite.equals("Firefox"))
		{
			System.setProperty("webdriver.chrome.driver",fireFoxPathFromConfig);
			driver = new FirefoxDriver();
		}
		else if (browserTypeFromSuite.equals("IE")) 
		{
			
			DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setCapability(CapabilityType.BROWSER_NAME, "IE");
	        capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
	        capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
	      
			System.setProperty("webdriver.ie.driver",IEPathFromConfig );
			driver = new InternetExplorerDriver(capabilities) ;
		}
		
		//navigate to site
		driver.get(siteURLFromConfig);
		
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		
		//maximize browser
		driver.manage().window().maximize();
		
	}
	
	
}
