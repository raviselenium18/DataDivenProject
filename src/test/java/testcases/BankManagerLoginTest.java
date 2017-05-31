package testcases;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.TestBase;

public class BankManagerLoginTest extends TestBase {

	
	
	@Test
	public void bankManagerLoginTest() throws InterruptedException{
		
		
		click("bmlBtn");
		Assert.assertTrue(isElementPresent(OR.getProperty("addCustBtn")),"Login not successful..");
		Thread.sleep(3000);
	}
	
}
