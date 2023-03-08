package driverFactory;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import commonFuncions.FunctionLibrary;
import utilities.ExcelFileUtil;

public class DriverScript {
public static WebDriver driver;
ExtentReports reports;
ExtentTest logger;
String Inputpath ="D:\\OJTLiveProjet\\ERP_HybridFrameWork\\FileInput\\DataEngine.xlsx";
String Outputpath ="D:\\OJTLiveProjet\\ERP_HybridFrameWork\\FileOutput\\HybridResults.xlsx";
public void starTest()throws Throwable
{
	String ModuleStatus="";
	//create object for excel file util class
	ExcelFileUtil xl = new ExcelFileUtil(Inputpath);
	//iterate  all rows in mastertestcases
	for(int i=1;i<=xl.rowCount("MasterTestCases");i++)
	{
	//read cell
		String Execution_Status =xl.getCellData("MasterTestCases", i, 2);
		if(Execution_Status.equalsIgnoreCase("Y"))
		{
			String TCModule =xl.getCellData("MasterTestCases", i, 1);
			reports = new ExtentReports("./ExtentReports/"+TCModule+"_"+FunctionLibrary.generateDate()+".html");
			//iterate all rows in TCModule
			for(int j=1;j<=xl.rowCount(TCModule);j++)
			{
				logger = reports.startTest(TCModule);
			String Description =xl.getCellData(TCModule, j, 0);
			String Object_Type = xl.getCellData(TCModule, j, 1);
			String Locator_Type = xl.getCellData(TCModule, j, 2);
			String Locator_Value = xl.getCellData(TCModule, j, 3);
			String Test_Data =xl.getCellData(TCModule, j, 4);
			try {
				if(Object_Type.equalsIgnoreCase("startBrowser"))
				{
					driver =FunctionLibrary.startBrowser();
					logger.log(LogStatus.INFO, Description);
				}
				else if(Object_Type.equalsIgnoreCase("applicationUrl"))
				{
					FunctionLibrary.applicationUrl(driver);
					logger.log(LogStatus.INFO, Description);
				}
				else if(Object_Type.equalsIgnoreCase("waitForElement"))
				{
					FunctionLibrary.waitForElement(driver, Locator_Type, Locator_Value, Test_Data);
					logger.log(LogStatus.INFO, Description);
				}
				else if(Object_Type.equalsIgnoreCase("typeAction"))
				{
					FunctionLibrary.typeAction(driver, Locator_Type, Locator_Value, Test_Data);
					logger.log(LogStatus.INFO, Description);
				}
				else if(Object_Type.equalsIgnoreCase("clickAction"))
				{
					FunctionLibrary.clickAction(driver, Locator_Type, Locator_Value);
					logger.log(LogStatus.INFO, Description);
				}
				else if(Object_Type.equalsIgnoreCase("validateTitle"))
				{
					FunctionLibrary.validateTitle(driver, Test_Data);
					logger.log(LogStatus.INFO, Description);
				}
				else if(Object_Type.equalsIgnoreCase("closeBrowser"))
				{
					FunctionLibrary.closeBrowser(driver);
					logger.log(LogStatus.INFO, Description);
				}
				else if(Object_Type.equalsIgnoreCase("mouseClick"))
				{
					FunctionLibrary.mouseClick(driver);
					logger.log(LogStatus.INFO, Description);
				}
				else if(Object_Type.equalsIgnoreCase("stockTable"))
				{
					FunctionLibrary.stockTable(driver, Test_Data);
					logger.log(LogStatus.INFO, Description);
				}
				else if(Object_Type.equalsIgnoreCase("captureData"))
				{
					FunctionLibrary.captureData(driver, Locator_Type, Locator_Value);
					logger.log(LogStatus.INFO, Description);
				}
				else if(Object_Type.equalsIgnoreCase("supplierTable"))
				{
					FunctionLibrary.supplierTable(driver);
					logger.log(LogStatus.INFO, Description);
				}
				//write as pass
				xl.setCellData(TCModule, j, 5, "Pass", Outputpath);
				logger.log(LogStatus.PASS, Description);
				ModuleStatus="True";
			}catch(Throwable t)
			{
				System.out.println(t.getMessage());
				//write as fail
				xl.setCellData(TCModule, j, 5, "Fail", Outputpath);
				logger.log(LogStatus.FAIL, Description);
				ModuleStatus="False";
			}
			if(ModuleStatus.equalsIgnoreCase("True"))
			{
				xl.setCellData("MasterTestCases", i, 3, "Pass", Outputpath);
			}
			else
			{
				xl.setCellData("MasterTestCases", i, 3, "Fail", Outputpath);
			}
			reports.endTest(logger);
			reports.flush();
			}
		}
		else
		{
			//write as blocked into MasterTestCases which are flag to N
			xl.setCellData("MasterTestCases", i, 3, "Blocked", Outputpath);
		}
	}
}
}
