package base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import utilities.DbManager;
import utilities.ExcelReader;
import utilities.MonitoringMail;

public class TestBase {
	
	/*
	 * WebDriver
	 * Properties
	 * Logs
	 * Mails
	 * DB Conn
	 * Excel Reading
	 *
	 * 
	 */
	public static WebDriver driver;
	public static Properties OR = new Properties();
	public static Properties Config = new Properties();
	//public static Logger log = Logger.getLogger("devpinoyLogger");
	public static ExcelReader excel = new ExcelReader(System.getProperty("user.dir")+"\\src\\test\\resources\\excel\\testdata.xlsx");
	public static MonitoringMail mail = new MonitoringMail();
	public static FileInputStream fis;
	public static WebDriverWait wait;
	
	
	@BeforeSuite
	public void setUp(){
		
		
		if(driver==null){
			
			try {
				fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\properties\\Config.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				Config.load(fis);
			//	log.debug("Config properties file loaded");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			try {
				fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\properties\\OR.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				OR.load(fis);
			//	log.debug("OR properties file loaded");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			if(Config.getProperty("browser").equals("firefox")){
				
				driver = new FirefoxDriver();
		//		log.debug("Launching Firefox");
				
			}else if(Config.getProperty("browser").equals("chrome")){
				
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\src\\test\\resources\\executables\\chromedriver.exe");
				driver = new ChromeDriver();
		//		log.debug("Launching Chrome");
				
			}else if(Config.getProperty("browser").equals("ie")){
				
				System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"\\src\\test\\resources\\executables\\IEDriverServer.exe");
				driver = new InternetExplorerDriver();
		//		log.debug("Launching IE");
				
			}
			
			
			driver.get(Config.getProperty("testsiteurl"));
		//	log.debug("Navigated to :"+Config.getProperty("testsiteurl"));
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Integer.parseInt(Config.getProperty("implicit.wait")), TimeUnit.SECONDS);
			
			wait = new WebDriverWait(driver,Integer.parseInt(Config.getProperty("explicit.wait")));
		
		
			try {
				DbManager.setMysqlDbConnection();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
		
		
		
		
	}
	
	
	
	public void click(String locator){
		
		driver.findElement(By.cssSelector(OR.getProperty(locator))).click();
		
	}
	
	
	public void type(String locator,String value){
		
		driver.findElement(By.cssSelector(OR.getProperty(locator))).sendKeys(value);
	}
	
	
	public void select(String locator,String value){
		
		WebElement dropdown = driver.findElement(By.cssSelector(OR.getProperty(locator)));
		Select select = new Select(dropdown);
		select.selectByVisibleText(value);
		
		
		
	}
	
	
	public static boolean isElementPresent(String locator){
		
		try{
		driver.findElement(By.cssSelector(locator));
		return true;
		}catch(Throwable t){
			
			return false;
		}
	}
	
	@AfterSuite
	public void tearDown(){
		
		driver.quit();
	//	log.debug("Test Execution completed !!!");
		
	}
	

}
