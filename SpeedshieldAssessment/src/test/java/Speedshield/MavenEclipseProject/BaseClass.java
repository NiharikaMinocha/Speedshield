package Speedshield.MavenEclipseProject;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
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

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

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
	public String captureScreen() throws IOException {
		TakesScreenshot screen = (TakesScreenshot) driver;
		File src = screen.getScreenshotAs(OutputType.FILE);
		String dest ="C:\\Users\\nihar\\git\\Framework1\\SpeedshieldAssessment\\test-output\\Screenshots"+getcurrentdateandtime()+".png";
		File target = new File(dest);
		FileUtils.copyFile(src, target);
		return dest;
	}
	
public String getcurrentdateandtime(){
String str = null;
try{
DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss:SSS");
Date date = new Date();
str= dateFormat.format(date);
str = str.replace(" ", "").replaceAll("/", "").replaceAll(":", "");
}
catch(Exception e){

}
return str;
}
	
	@AfterMethod
    public void getResult(ITestResult result) throws IOException
    {
		//Extent report Color Setup and Exception Reporting
        if(result.getStatus() == ITestResult.FAILURE)
        {
            test.log(Status.FAIL, MarkupHelper.createLabel(result.getName()+" Test case FAILED due to below issues:", ExtentColor.RED));
            test.fail(result.getThrowable());
            test.log(Status.INFO, "Failure Screenshot").addScreenCaptureFromPath(captureScreen());
        }
        else if(result.getStatus() == ITestResult.SUCCESS)
        {
            test.log(Status.PASS, MarkupHelper.createLabel(result.getName()+" Test Case PASSED", ExtentColor.GREEN));
        }
        else
        {
            test.log(Status.SKIP, MarkupHelper.createLabel(result.getName()+" Test Case SKIPPED", ExtentColor.ORANGE));
            test.skip(result.getThrowable());
            test.log(Status.INFO, "Skipped Step Screenshot").addScreenCaptureFromPath(captureScreen());
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