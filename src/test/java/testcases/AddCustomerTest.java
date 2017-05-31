package testcases;

import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.TestBase;
import utilities.TestUtil;

public class AddCustomerTest extends TestBase {

	
	@Test(dataProvider="getData")
	public void addCustomerTest(String firstName,String lastName,String postCode,String alerttext) throws InterruptedException{
		
		click("addCustBtn");
		type("firstname",firstName);
		type("lastname",lastName);
		type("postcode",postCode);
		click("addCustomer");
		
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		
		Assert.assertTrue(alert.getText().contains(alerttext),"Customer not added successfully");
		
		alert.accept();
		
		Thread.sleep(3000);
		
		Assert.fail("Failing add customer test...");
		
	}
	
	@DataProvider
	public static Object[][] getData(){
		

		return TestUtil.getData("AddCustomerTest");

		
		
	}
	
	
}
