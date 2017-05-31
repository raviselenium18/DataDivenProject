package testcases;

import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.TestBase;
import utilities.TestUtil;

public class OpenAccountTest extends TestBase {

	@Test(dataProvider="getData")
	public void openAccountTest(String customer, String currency,String alerttext) throws InterruptedException {

		click("openAccBtn");
		select("customer", customer);
		select("currency", currency);
		click("process");

		Alert alert = wait.until(ExpectedConditions.alertIsPresent());

		Assert.assertTrue(alert.getText().contains(alerttext), "Hello, Account created successfully!!");

		alert.accept();

		Thread.sleep(3000);

	}
	
	
	@DataProvider
	public static Object[][] getData(){
	

		return TestUtil.getData("OpenAccountTest");

		
		
	}

}
