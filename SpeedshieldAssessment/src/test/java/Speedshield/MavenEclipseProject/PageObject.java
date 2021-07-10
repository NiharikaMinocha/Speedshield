package Speedshield.MavenEclipseProject;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import com.aventstack.extentreports.Status;


public class PageObject extends BaseClass {
	
	static Logger log = Logger.getLogger(TestCase1.class.getName());
	String foreName;
	String emailError;
	
	 public void AcceptCookies() throws InterruptedException 
	    {
	    	driver.findElement(By.id("okBtn")).click();
	    }
	 
	 public void Login(String UserName, String Password) throws InterruptedException 
	    {
	    	driver.findElement(By.id("tbUsername")).sendKeys(UserName);
	    	driver.findElement(By.id("tbPassword")).sendKeys(Password);
	    	driver.findElement(By.id("btnSignIn")).click();
	    	test.log(Status.PASS, "Login Success");
	    	
	    }
	 public void ScrollDown() throws InterruptedException 
	    {
		 JavascriptExecutor js = (JavascriptExecutor) driver;
		 js.executeScript("window.scrollBy(0,200)");		 
	    } 
	 public void Filter(String FirstName, String LastName )
	    {
	    	driver.findElement(By.xpath("//input[@id='tbSearch0_1']")).sendKeys(FirstName);
	    	driver.findElement(By.xpath("//input[@id='tbSearch0_2']")).sendKeys(LastName);
	    	
	    }
	 public void FilterValidation (String FirstName, String LastName) throws InterruptedException
	  {
	 Thread.sleep(2000);
	 String FirstNameFilter= driver.findElement(By.xpath("//tbody/tr[1]/td[1]")).getText();
     String LastNameFilter= driver.findElement(By.xpath("//tbody/tr[1]/td[2]")).getText();
     
     Assert.assertEquals(FirstNameFilter,FirstName);
     Assert.assertEquals(LastNameFilter,LastName);  
     test.log(Status.PASS, "Successfully filtered with the FirstName and LastName provided");
	  }
	 public void SelectOperator()
	    {
		 driver.findElement(By.xpath("//tbody/tr[1]/td[1]")).click();
	    }
	 public void UpdatePersonnelNumber(String PersonnelNumber)
	    {
		 driver.findElement(By.xpath("//input[@id='tbPersonnelNumber']")).clear();
		 driver.findElement(By.xpath("//input[@id='tbPersonnelNumber']")).sendKeys(PersonnelNumber);
		 driver.findElement(By.xpath("//input[@id='btnUpdateOperatorDetails']")).click();
	    }
	 public void SuccessMessageValidation(String ExpectedSuccessMessage, String PersonnelNumber, String FirstName, String LastName) throws InterruptedException
	    {
		 driver.findElement(By.xpath("//input[@id='btnUpdateYes']")).click();
		 String ActualMessage= driver.findElement(By.xpath("//p[@id='pStatusOperaterDetails']")).getText();
		 Assert.assertEquals(ActualMessage,ExpectedSuccessMessage);
		 test.log(Status.PASS, "Successfully Updated Personnel Number to : "+PersonnelNumber+" for "+FirstName+" "+LastName);
	    }
	 
	 
	    
	    
}