package commonFuncions;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import utilities.PropertyFileUtil;
/*Project Name: Stock Accounting
 * Tester: Ranga
 * Created Date : 02/03/23
 * Module Name: All Modules
 */
public class FunctionLibrary {
public static WebDriver driver;
//method for start Browser
public static WebDriver startBrowser()throws Throwable
{
	if(PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("chrome"))
	{
		driver = new ChromeDriver();
		driver.manage().window().maximize();
	}
	else if(PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("firefox"))
	{
		driver = new FirefoxDriver();
	}
	else
	{
		Reporter.log("Browser value is Not Matching",true);
	}
	return driver;
}
//method for open application
public static void applicationUrl(WebDriver driver)throws Throwable
{
	driver.get(PropertyFileUtil.getValueForKey("Url"));
}
//method for wait for element
public static void waitForElement(WebDriver driver,String LocatorType,String LocatorValue,String MyWait)
{
	WebDriverWait myWait =new WebDriverWait(driver, Integer.parseInt(MyWait));
	if(LocatorType.equalsIgnoreCase("xpath"))
	{
		myWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LocatorValue)));
	}
	else if(LocatorType.equalsIgnoreCase("name"))
	{
		myWait.until(ExpectedConditions.visibilityOfElementLocated(By.name(LocatorValue)));
	}
	else if(LocatorType.equalsIgnoreCase("id"))
	{
		myWait.until(ExpectedConditions.visibilityOfElementLocated(By.id(LocatorValue)));
	}
}
//method for type action
public static void typeAction(WebDriver driver,String LocatorType,String LocatorValue,String TestData)
{
	if(LocatorType.equalsIgnoreCase("name"))
	{
		driver.findElement(By.name(LocatorValue)).clear();
		driver.findElement(By.name(LocatorValue)).sendKeys(TestData);
	}
	else if(LocatorType.equalsIgnoreCase("xpath"))
	{
		driver.findElement(By.xpath(LocatorValue)).clear();
		driver.findElement(By.xpath(LocatorValue)).sendKeys(TestData);
	}
	else if(LocatorType.equalsIgnoreCase("id"))
	{
		driver.findElement(By.id(LocatorValue)).clear();
		driver.findElement(By.id(LocatorValue)).sendKeys(TestData);
	}
}
//method for click action
public static void clickAction(WebDriver driver,String LocatorType,String LocatorValue)
{
	if(LocatorType.equalsIgnoreCase("xpath"))
	{
		driver.findElement(By.xpath(LocatorValue)).click();
	}
	else if(LocatorType.equalsIgnoreCase("name"))
	{
		driver.findElement(By.name(LocatorValue)).click();
	}
	else if(LocatorType.equalsIgnoreCase("id"))
	{
		driver.findElement(By.id(LocatorValue)).sendKeys(Keys.ENTER);
	}
}
//validate page title
public static void validateTitle(WebDriver driver,String ExpectedTitle)
{
	String Actualttitle = driver.getTitle();
	try {
	Assert.assertEquals(Actualttitle, ExpectedTitle,"Title is Not Matching");
	}catch(Throwable t)
	{
		System.out.println(t.getMessage());
	}
}
//method for close browser
public static void closeBrowser(WebDriver driver)
{
	driver.quit();
}
}








