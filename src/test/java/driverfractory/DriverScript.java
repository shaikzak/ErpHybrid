package driverfractory;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import commonfunctions.Functionlibrary;
import utilites.ExcelFileUtil;

public class DriverScript
{
	String inputpath="./FileInput/Dataengine.xlsx";
	String outputpath="./FileOutput/DataResults.xlsx";
	ExtentReports report;
	ExtentTest logger;
	String sheet="MasterTestCases";
	WebDriver driver;
  @Test
	public void startTest() throws Throwable
	{
		String ModuleStatus="";
		ExcelFileUtil xl=new ExcelFileUtil(inputpath);
		for(int i=1;i<=xl.rowcount(sheet);i++)
		{
			if(xl.getcelldata(sheet, i, 2).equalsIgnoreCase("y"))
			{
				String Tcmodule=xl.getcelldata(sheet, i, 1);
				report=new ExtentReports("./target/Reports/"+Tcmodule+Functionlibrary.generatedate()+"html");
				logger=report.startTest(Tcmodule);
				logger.assignAuthor("zakeer");

				for(int j=1;j<=xl.rowcount(Tcmodule);j++)
				{
					String Description=xl.getcelldata(Tcmodule, j, 0);
					String object_Type=xl.getcelldata(Tcmodule, j, 1);
					String Locator_Type=xl.getcelldata(Tcmodule, j, 2);
					String Locator_value=xl.getcelldata(Tcmodule, j, 3);
					String Test_Data=xl.getcelldata(Tcmodule, j, 4);
					try {
						if(object_Type.equalsIgnoreCase("startbrowser"))
						{
							driver=	Functionlibrary.startbrowser();
							logger.log(LogStatus.INFO, Description);

						}
						if(object_Type.equalsIgnoreCase("openurl"))
						{
							Functionlibrary.openurl();
							logger.log(LogStatus.INFO, Description);
						}
						if(object_Type.equalsIgnoreCase("waitforelement"))
						{
							Functionlibrary.waitforelement(Locator_Type, Locator_value, Test_Data);
							logger.log(LogStatus.INFO, Description);
						}
						if(object_Type.equalsIgnoreCase("typeaction"))
						{
							Functionlibrary.typeaction(Locator_Type, Locator_value, Test_Data);
							logger.log(LogStatus.INFO, Description);
						}
						if(object_Type.equalsIgnoreCase("clickaction"))
						{
							Functionlibrary.clickaction(Locator_Type, Locator_value);
							logger.log(LogStatus.INFO, Description);
						}
						if(object_Type.equalsIgnoreCase("validatetittle"))
						{
							Functionlibrary.validatetittle(Test_Data);	
							logger.log(LogStatus.INFO, Description);
						}
						if(object_Type.equalsIgnoreCase("closebrowser"))
						{
							Functionlibrary.closebrowser();	
							logger.log(LogStatus.INFO, Description);
						}
						if(object_Type.equalsIgnoreCase("dropDownAction"))
						{
							Functionlibrary.dropDownAction(Locator_Type,Locator_value, Test_Data);	
							logger.log(LogStatus.INFO, Description);
						}
						if(object_Type.equalsIgnoreCase("capturestock"))
						{
							Functionlibrary.capturestock(Locator_Type, Locator_value);	
							logger.log(LogStatus.INFO, Description);
						}
						if(object_Type.equalsIgnoreCase("stockTable"))
						{
							Functionlibrary.stockTable();	
							logger.log(LogStatus.INFO, Description);
						}
						xl.setcelldata(Tcmodule, j, 5, "pass", outputpath);
						logger.log(LogStatus.PASS, Description);
						ModuleStatus="true";

					} catch (Exception e) 
					{
						System.out.println(e.getMessage());
						xl.setcelldata(Tcmodule, j, 5, "fail", outputpath);
						logger.log(LogStatus.FAIL, Description);
						ModuleStatus="false";
					}
					if(ModuleStatus.equalsIgnoreCase("true"))
					{
						xl.setcelldata(sheet, i, 3, "pass", outputpath);
					}
					if(ModuleStatus.equalsIgnoreCase("false"))
					{
						xl.setcelldata(sheet, i, 3, "fail", outputpath);
					}
					report.endTest(logger);
					report.flush();

				}

			}
			else
			{
				xl.setcelldata(sheet, i, 3, "blocked", outputpath);

			}
		}

	}

}
