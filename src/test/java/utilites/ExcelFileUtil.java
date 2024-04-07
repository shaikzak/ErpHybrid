package utilites;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.WorksheetDocument;

public class ExcelFileUtil
{
	Workbook wb;
	public  ExcelFileUtil(String Excelpath) throws Throwable
	{
		FileInputStream fi= new FileInputStream(Excelpath);
		wb=WorkbookFactory.create(fi);
	}
	public int rowcount(String sheetname)

	{
		return wb.getSheet(sheetname).getLastRowNum();
	}
	public String getcelldata(String sheetname,int row,int column)
	{
		String data="";
		if(wb.getSheet(sheetname).getRow(row).getCell(column).getCellType()==CellType.NUMERIC)
		{
			int celldata=(int) wb.getSheet(sheetname).getRow(row).getCell(column).getNumericCellValue();
			data=String.valueOf(celldata);
		}else
		{
			data=wb.getSheet(sheetname).getRow(row).getCell(column).getStringCellValue();

		}
		return data;
	}
	public void setcelldata(String sheetname,int row,int column,String status,String writeexcel)throws Throwable
	{
		Sheet ws=wb.getSheet(sheetname);
		Row rownum=ws.getRow(row);
		Cell cell =rownum.createCell(column);
		cell.setCellValue(status);
		if(status.equalsIgnoreCase("pass"))
		{
			CellStyle style=wb.createCellStyle();
			Font font=wb.createFont();
			font.setColor(IndexedColors.GREEN.getIndex());
			font.setBold(true);
			style.setFont(font);
			ws.getRow(row).getCell(column).setCellStyle(style);
		}
		else if(status.equalsIgnoreCase(("fail")))
		{
			CellStyle style=wb.createCellStyle();
			Font font=wb.createFont();
			font.setColor(IndexedColors.RED.getIndex());
			font.setBold(true);
			style.setFont(font);
			ws.getRow(row).getCell(column).setCellStyle(style);
		}
		else if(status.equalsIgnoreCase(("Blocked")))
		{
			CellStyle style=wb.createCellStyle();
			Font font=wb.createFont();
			font.setColor(IndexedColors.BLUE.getIndex());
			font.setBold(true);
			style.setFont(font);
			ws.getRow(row).getCell(column).setCellStyle(style);
		}
		FileOutputStream fo=new FileOutputStream(writeexcel);
		wb.write(fo);
		//	wb.close();

	}
	/*
public static void main(String[] args)throws Throwable
{
	ExcelFileUtil xl=new ExcelFileUtil("D:/empwork.xlsx");
	int rc =xl.rowcount("empl");
	System.out.println(rc);
	for( int i=1;i<=rc;i++)
	{
		String fname =xl.getcelldata("empl", i, 0) ;
		String mname =xl.getcelldata("empl", i, 1) ;
		String sal =xl.getcelldata("empl", i, 2) ;
		String empid=xl.getcelldata("empl", i, 3) ;
		System.out.println(fname +"    "+mname+"     "+sal+"   "+empid+"   ");
		//xl.setcelldata("empl", i, 4, "pass", "D:/result.xlsx");
		  xl.setcelldata("empl", i, 4, "fail", "D:/result.xlsx");
	}

}*/
}
