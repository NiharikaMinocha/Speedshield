package Speedshield.MavenEclipseProject;
import java.io.IOException;

import org.testng.annotations.*;
public class TestCase1 extends PageObject {
	
	
	 
  @Parameters({"UserName","Password","FirstName","LastName","PersonnelNumber","ExpectedSuccessMessage"}) 
  @Test
  public void TC1_Update_Operator_Personnal_Number(String UserName,String Password,String FirstName, String LastName, String PersonnelNumber, String ExpectedSuccessMessage) throws InterruptedException, IOException {
	test = extent.createTest("TC1 Update Operator Personnal Number");  
    AcceptCookies();	  
	Login(UserName,Password);
	ScrollDown(); 
	Filter(FirstName,LastName);
	FilterValidation(FirstName,LastName);
	SelectOperator();
	ScrollDown(); 
	UpdatePersonnelNumber(PersonnelNumber);
	SuccessMessageValidation(ExpectedSuccessMessage,PersonnelNumber,FirstName,LastName);
	
  }
  
}
