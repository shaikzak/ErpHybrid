package driverfractory;

import org.testng.annotations.Test;

public class Apptest 
{
@Test
public void kickstart() throws Throwable
{
	DriverScript ds=new DriverScript();
	ds.startTest();
}
}
