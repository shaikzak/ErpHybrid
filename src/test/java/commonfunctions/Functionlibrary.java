package commonfunctions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

public class Functionlibrary
{
public static WebDriver driver;
public static Properties conpro;

public static WebDriver startbrowser() throws Throwable
{
	conpro=new Properties();
	conpro.load(new FileInputStream("./PropertyFile/Environmental.properties"));
	
	if(conpro.getProperty("browser").equalsIgnoreCase("chrome"))
	{
		driver=new ChromeDriver();
		driver.manage().window().maximize();
	}
	else if(conpro.getProperty("browser").equalsIgnoreCase("firefox"))	
	{
		driver=new FirefoxDriver();
	}
	else
	{
		Reporter.log("browser is not matching",true);
	}
	return driver;
	
	
}
public static void openurl()
{
	driver.get(conpro.getProperty("url"));

}
public static void waitforelement(String LocatorType, String LocatorValue,String TestData)
{
	WebDriverWait mywait=new WebDriverWait(driver,Duration.ofSeconds(Integer.parseInt(TestData)));
	if(LocatorType.equalsIgnoreCase("name"))
	{
		mywait.until(ExpectedConditions.visibilityOfElementLocated(By.name(LocatorValue)));
	}
	if(LocatorType.equalsIgnoreCase("xpath"))
	{
		mywait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LocatorValue)));
	}
	if(LocatorType.equalsIgnoreCase("id"))
	{
		mywait.until(ExpectedConditions.visibilityOfElementLocated(By.id(LocatorValue)));
	}
	
	
}
public static void typeaction(String LocatorType, String LocatorValue,String TestData)
{
	if(LocatorType.equalsIgnoreCase("xpath"))
	{
		driver.findElement(By.xpath(LocatorValue)).clear();
		driver.findElement(By.xpath(LocatorValue)).sendKeys(TestData);
	}
	if(LocatorType.equalsIgnoreCase("name"))
	{
		driver.findElement(By.name(LocatorValue)).clear();
		driver.findElement(By.name(LocatorValue)).sendKeys(TestData);
	}
	if(LocatorType.equalsIgnoreCase("id"))
	{
		driver.findElement(By.id(LocatorValue)).clear();
		driver.findElement(By.id(LocatorValue)).sendKeys(TestData);
	}
}
public static void clickaction(String LocatorType,String LocatorValue)
{
	if(LocatorType.equalsIgnoreCase("xpath"))
	{
		driver.findElement(By.xpath(LocatorValue)).click();
	}
	if(LocatorType.equalsIgnoreCase("name"))
	{
		driver.findElement(By.name(LocatorValue)).click();
	}
	if(LocatorType.equalsIgnoreCase("id"))
	{
		driver.findElement(By.id(LocatorValue)).sendKeys(Keys.ENTER);	
	}
		
}
public static void validatetittle(String exptittle)
{
	String acttittle=driver.getTitle();
	try {
	Assert.assertEquals(exptittle, acttittle,"tittle is not matching");
	}catch (AssertionError a)
	{
		System.out.println(a.getMessage());
	}
	
}
public static void closebrowser()

		{
	driver.quit();
	
		}
public static String generatedate()

{
	Date date=new Date();
	DateFormat df=new SimpleDateFormat("YYYY_MM_DD");
	return df.format(date);
	
}
public static void dropDownAction(String LocatorType,String LocatorValue, String TestData)
{
	if(LocatorType.equalsIgnoreCase("xpath"))
	{
		int value=Integer.parseInt(TestData);
		Select element=new Select(driver.findElement(By.xpath(LocatorValue)));
		element.selectByIndex(value);
	}
	if(LocatorType.equalsIgnoreCase("name"))
	{
		int value=Integer.parseInt(TestData);
		Select element=new Select(driver.findElement(By.name(LocatorValue)));
		element.selectByIndex(value);
	}
	if(LocatorType.equalsIgnoreCase("id"))
	{
		int value=Integer.parseInt(TestData);
		Select element=new Select(driver.findElement(By.id(LocatorValue)));
		element.selectByIndex(value);
	}
	

}
public static void capturestock( String LocatorType,String LocatorValue) throws Throwable
{
	String stock_num="";
	if(LocatorType.equalsIgnoreCase("xpath"))
	{
		stock_num=driver.findElement(By.xpath(LocatorValue)).getAttribute("value");
	}
	if(LocatorType.equalsIgnoreCase("id"))
	{
		stock_num=driver.findElement(By.id(LocatorValue)).getAttribute("value");
	}
	if(LocatorType.equalsIgnoreCase("name"))
	{
		stock_num=driver.findElement(By.name(LocatorValue)).getAttribute("value");
	}
	FileWriter fw=new FileWriter("./CaptureData/stocknumber.txt");
	BufferedWriter bw=new BufferedWriter(fw);
	bw.write(stock_num);
	bw.flush();
	bw.close();
	
	
}
public static void stockTable() throws Throwable
{
	FileReader fr=new FileReader("./CaptureData/stocknumber.txt");
	BufferedReader br= new BufferedReader(fr);
	String Exp_data=br.readLine();
	

	
	if(!driver.findElement(By.xpath(conpro.getProperty("search_textbox"))).isDisplayed())
		driver.findElement(By.xpath(conpro.getProperty("search_pannel"))).click();


	    driver.findElement(By.xpath(conpro.getProperty("search_textbox"))).clear();
	    driver.findElement(By.xpath(conpro.getProperty("search_textbox"))).sendKeys(Exp_data);
	    driver.findElement(By.xpath(conpro.getProperty("search_button"))).click();
	    Thread.sleep(3000);
	    String Act_Data =driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[8]/div/span/span")).getText();
	    Reporter.log(Act_Data+"======"+Exp_data,true);
	    try
	    {
	    Assert.assertEquals(Act_Data,Exp_data,"stock number is not matching");
	    }catch(AssertionError a)
	    {
	    	System.out.println(a.getMessage());
	    }
	    
}
public static void capturesup(String LocatorType,String LocatorValue) throws Throwable

{
	String supnum="";
	if(LocatorType.equalsIgnoreCase("xpath"))
	{
		supnum=driver.findElement(By.xpath(LocatorValue)).getAttribute("value");
	}
	if(LocatorType.equalsIgnoreCase("id"))
	{
		supnum=driver.findElement(By.id(LocatorValue)).getAttribute("value");
	}
	if(LocatorType.equalsIgnoreCase("name"))
	{
		supnum=driver.findElement(By.name(LocatorValue)).getAttribute("value");
	}
	FileWriter fw=new FileWriter("./CaptureData/suppliernumber.txt");
	BufferedWriter bw=new BufferedWriter(fw);
	bw.write(supnum);
	bw.flush();
	bw.close();
	
	
	
	
}
public static void supplierTable() throws Throwable
{
	FileReader fr=new FileReader("./CaptureData/suppliernumber.txt");
    BufferedReader br=new BufferedReader(fr);
    String exp_data=br.readLine();
    if(!driver.findElement(By.xpath(conpro.getProperty("search_textbox"))).isDisplayed())
    	driver.findElement(By.xpath(conpro.getProperty("search_pannel"))).click();


    driver.findElement(By.xpath(conpro.getProperty("search_textbox"))).clear();
    driver.findElement(By.xpath(conpro.getProperty("search_textbox"))).sendKeys(exp_data);
    driver.findElement(By.xpath(conpro.getProperty("search_button"))).click();
    String act_data=driver.findElement(By.xpath(conpro.getProperty("//table[@class='table ewTable']/tbody/tr[1]/td[6]/div/span/span"))).getText();
    Reporter.log(act_data+"==========="+exp_data,true);
    try {
    	Assert.assertEquals(act_data, exp_data,"supplire num is not matching");
		
	} catch (AssertionError a) 
    {
		System.out.println(a.getMessage());
		
	}
   
    
    
}
public static void capturecus(String LocatorType,String LocatorValue) throws Throwable
{
	String cus_num="";
	if(LocatorType.equalsIgnoreCase("xpath"))
	{
		cus_num=driver.findElement(By.xpath(LocatorValue)).getAttribute("value");
		
	}
	if(LocatorType.equalsIgnoreCase("name"))
	{
		cus_num=driver.findElement(By.name(LocatorValue)).getAttribute("value");
		
	}
	if(LocatorType.equalsIgnoreCase("id"))
	{
		cus_num=driver.findElement(By.id(LocatorValue)).getAttribute("value");
		
	}
	
	FileWriter fr=new FileWriter("./CaptureData/customernumber.txt");
	BufferedWriter br= new BufferedWriter(fr);
	br.write(cus_num);
	br.flush();
	br.close();
}
	
public static void customerTable() throws Throwable

{
	FileReader fr=new FileReader("./CaptureData/customernumber.txt");
    BufferedReader br=new BufferedReader(fr);
    String exp_data=br.readLine();
    if(!driver.findElement(By.xpath(conpro.getProperty("search_textbox"))).isDisplayed())
    	driver.findElement(By.xpath(conpro.getProperty("search_pannel"))).click();


    driver.findElement(By.xpath(conpro.getProperty("search_textbox"))).clear();
    driver.findElement(By.xpath(conpro.getProperty("search_textbox"))).sendKeys(exp_data);
    driver.findElement(By.xpath(conpro.getProperty("search_button"))).click();
    String act_data=driver.findElement(By.xpath(conpro.getProperty("//table[@class='table ewTable']/tbody/tr[1]/td[6]/div/span/span"))).getText();
    Reporter.log(act_data+"==========="+exp_data,true);
    try {
    	Assert.assertEquals(act_data, exp_data,"supplire num is not matching");
		
	} catch (AssertionError a) 
    {
		System.out.println(a.getMessage());
		
	}
}
}
   
			
	
		
    

    
	    
	    

		
			
	
	

















