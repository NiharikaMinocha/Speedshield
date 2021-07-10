package Speedshield.MavenEclipseProject;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.markuputils.ExtentColor;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class BaseClass 
{
            
	WebDriver driver;
	public static ExtentSparkReporter htmlReporter;
    public static ExtentReports extent;
    public static ExtentTest test;
	@Parameters({"URL"})	
	@BeforeSuite
	public void setupApplication(String URL) throws UnknownHostException
	{   
		//Extent Report Setup
		htmlReporter = new ExtentSparkReporter(".\\test-output\\ExtentReport\\TestReport.html");
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);

		extent.setSystemInfo("OS", System.getProperty("os.name"));
		extent.setSystemInfo("Host Name", InetAddress.getLocalHost().getHostName());
		extent.setSystemInfo("Environment", System.getenv("JAVA_HOME"));
		extent.setSystemInfo("User Name", System.getProperty("user.name"));

		htmlReporter.config().setDocumentTitle("Speedshield Technologies Report");
		htmlReporter.config().setReportName("Speedshield Technologies");
		htmlReporter.config().setTheme(Theme.STANDARD);
		
		//Webdriver setup
		System.setProperty("webdriver.chrome.driver", ".\\resources\\chromedriver.exe");
        driver=new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);		
		driver.manage().window().maximize();
		driver.get(URL);
				
	}
	
	@AfterMethod
    public void getResult(ITestResult result)
    {
		//Extent report Color Setup and Exception Reporting
        if(result.getStatus() == ITestResult.FAILURE)
        {
            test.log(Status.FAIL, MarkupHelper.createLabel(result.getName()+" Test case FAILED due to below issues:", ExtentColor.RED));
            test.fail(result.getThrowable());
        }
        else if(result.getStatus() == ITestResult.SUCCESS)
        {
            test.log(Status.PASS, MarkupHelper.createLabel(result.getName()+" Test Case PASSED", ExtentColor.GREEN));
        }
        else
        {
            test.log(Status.SKIP, MarkupHelper.createLabel(result.getName()+" Test Case SKIPPED", ExtentColor.ORANGE));
            test.skip(result.getThrowable());
        }
    }
	
	@AfterSuite
	public void closeApplication()
	{
		//close browser
		driver.quit();
		
		extent.flush();
			
	}
	
	
	
}