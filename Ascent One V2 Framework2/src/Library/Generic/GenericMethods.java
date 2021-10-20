package Library.Generic;

import java.awt.AWTException;
import java.awt.List;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.lang.model.element.Element;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.gargoylesoftware.htmlunit.javascript.host.dom.Selection;




import DriverScript.DriverScript;
//import Print.Random;
import Library.Utility.Xls_Reader;

public class GenericMethods {
	
	DriverScript ds=new DriverScript();
	HtmlResult hr=new HtmlResult();
	public static String companyname,companynameform,companynameheader,vtestdata;
	public static String address2;
	
	public void Fn_LaunchApp() throws InterruptedException
	{
		Thread.sleep(3000);
		ds.driver.get(ds.vProjectUrl);
//		ds.driver.manage().window().maximize();
//		WebDriverWait wait=new WebDriverWait(ds.driver,30);
//		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		ds.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
	}
	// Get Date and Time of system
	public String Fn_DateTime(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();		
		String TempFileName=dateFormat.format(date);
		String NewFileName1=TempFileName.replace("/","_");
		String NewFileName2=NewFileName1.replace(" ","_");
		String NewFileName=NewFileName2.replace(":","_");
		return NewFileName;
//		System.out.println(NewFileName);
	}
//	Get Screenshots – use this method when test case fail.
	public void Fn_screenshot(WebDriver driver, String screenshotname)
	{
		try {
			
		TakesScreenshot ts=(TakesScreenshot)driver;
		File source=ts.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(source,new File( "src/Screenshot/"+Fn_DateTime()+screenshotname+".png"));
		} 
		catch (IOException e) {
			System.out.println("Error whils taking screenshot");
			e.printStackTrace();
		}
	}
	
//	Verify page title
	public void Fn_verifyTitle(String vExp) throws Throwable
	{
		String vActTitle=ds.driver.getTitle();
		if(vActTitle.trim().equals(vExp.trim()))
		{
			System.out.println("PASS");
			hr.fgInsertResult(ds.vResFilePath, "Fn_verifyTitle", "Page Title Should be matched", "Page Title Matched", "PASS");
		}
		else
		{
			Fn_screenshot(ds.driver,vActTitle);
			System.out.println("FAIL");
			hr.fgInsertResult(ds.vResFilePath, "Fn_verifyTitle", "Page Title Should be matched", "Page Title not Matched", "FAIL");
		}
	}
// Customised wait function- give wait in excel file
	public void Fn_wait(String vVal) throws InterruptedException{
		
		int vVal1=vVal.indexOf(".");
		vVal=vVal.substring(0, vVal1);
		int time = Integer.parseInt(vVal);
		Thread.sleep(time);
	}
	
	// To Highlight Elements using javascript
	public void Fn_highlight(WebElement vObj,int vObjCnt,double vExpCnt,String vVal2) throws Throwable{
		if(vObjCnt==vExpCnt){
			
			for (int i=1 ; i<=7;i++){
//				((JavascriptExecutor)ds.driver).executeScript("arguments[0].style.backgroundColor='black';", vObj);
//				Thread.sleep(100);
//				((JavascriptExecutor) ds.driver).executeScript("arguments[0].style.backgroundColor='yellow';", vObj);
				((JavascriptExecutor) ds.driver).executeScript("arguments[0].style.border= '10px solid yellow';", vObj);
				Thread.sleep(300);
				((JavascriptExecutor) ds.driver).executeScript("arguments[0].style.border= '10px solid black';", vObj);
				
				}
			((JavascriptExecutor) ds.driver).executeScript("arguments[0].style.border= 'none';", vObj);
			
			System.out.println("PASS");
			hr.fgInsertResult(ds.vResFilePath, "Fn_highlight", "Element Should be highlighted", "\""+vVal2+ "\""+ " Highlighted", "PASS");
		}
		else
		{
			Fn_screenshot(ds.driver,"Fn_highlight");
			System.out.println("FAIL");
			hr.fgInsertResult(ds.vResFilePath, "Fn_highlight", "Element Should be highlighted", "\""+vVal2+ "\""+ " not Highlighted", "FAIL");
		}
		
		
	}
	
//	To verfiy object exist
	public void Fn_verifyObjectExist(int vObjCnt,double vExpCnt,String vVal2) throws Throwable
	{
		if(vObjCnt==vExpCnt)
		{
			System.out.println("PASS");
			hr.fgInsertResult(ds.vResFilePath, "Fn_verifyObjectExist", "Element Should be existed", "\""+vVal2+ "\""+ " Existed", "PASS");
		}
		else
		{
			Fn_screenshot(ds.driver,"Fn_verifyObjectExist");
			System.out.println("FAIL");
			hr.fgInsertResult(ds.vResFilePath, "Fn_verifyObjectExist", "Element Should be existed", "\""+vVal2+ "\""+ " not Existed", "FAIL");
		}
	}
	
//	Verify text present on the webpage.
	public void Fn_verifyText(WebElement vObj, int vObjCnt,String vVal,String vVal2) throws Throwable
	{                
                if(vObjCnt==1)
        		{
        			
        			Thread.sleep(1000);
        			//String vGetVal=vObj.getAttribute("value");
        			String vGetVal=vObj.getText().trim();
        			System.out.println(vGetVal);
        			System.out.println(vVal);
        			if(vGetVal.equals(vVal))
        			{
        				System.out.println("PASS");
        				hr.fgInsertResult(ds.vResFilePath, "Fn_verifyText", "Text Should be Matched", "\""+vVal2+ "\""+ " Text matched", "PASS");
        			}
        			else
        			{
        				Fn_screenshot(ds.driver,"Fn_verifyText");
        				System.out.println("FAIL");
        				hr.fgInsertResult(ds.vResFilePath, "Fn_verifyText", "Text Should be Matched", "\""+vVal2+ "\""+ " Text not matched", "FAIL");
        			}
        		}
        		else
        		{
        			Fn_screenshot(ds.driver,"Fn_verifyText");
        			System.out.println("FAIL vObjCnt==1");
        			hr.fgInsertResult(ds.vResFilePath, "Fn_verifyText", "Text Should be Matched", "\""+vVal2+ "\""+ " Text not matched", "FAIL");
        		}
            }
////  Fetch Dropdown list value
//	public void Fn_ListValue(WebElement vObj,int vObjCnt)
//	{
//		String  frameList=vObj.getAttribute("value");
//		for(String[] attribute: frameList)
//		{
//			String Url=
//			System.out.println(Url);
//		}
//		System.out.println(frameList.size());
//	
//	}
	
	
	
//	Compare data in text file and data given in excel file
	public void Fn_verifyProdName(WebElement vObj, int vObjCnt,String vVal) throws InterruptedException
	{
		BufferedReader br = null;
        vVal = "";
        try {
            //Point the br object to the file you want to read 
            //File to be read line by line - C:/Desktop/Code.txt
            br = new BufferedReader( new FileReader("D:/ObjNext/Framework Date - 13052016 Updated/ProductNamefile.txt"));
            //Read the file Line by Line till Null value is encountered
            while( (vVal = br.readLine()) != null){
                //display each line
                System.out.println(vVal);
                
                if(vObjCnt==1)
        		{
        			//vObj.clear();
        			//vObj.sendKeys(vVal);
        			Thread.sleep(2000);
        			//String vGetVal=vObj.getAttribute("value");
        			String vGetVal=vObj.getText();
        			System.out.println(vGetVal);
        			if(vGetVal.equals(vVal))
        			{
        				System.out.println("PASS");
        			}
        			else
        			{
        				System.out.println("FAIL");
        			}
        		}
        		else
        		{
        			System.out.println("FAIL");
        		}
            }
            
            br.close();
        } catch (FileNotFoundException e) {
            //Display error message if File was not found
            System.err.println("Unable to find the file");
        } catch (IOException e) {
            //Display error message if an exception is encounterd while reading the file
            System.err.println("Unable to read the file");
        }
           
	}
	
	public void Fn_verifyQuoteID(WebElement vObj, int vObjCnt,String vVal) throws InterruptedException
	{
		BufferedReader br = null;
        vVal = "";
        try {
            //Point the br object to the file you want to read 
            //File to be read line by line - C:/Javaselenium/Code.txt
            br = new BufferedReader( new FileReader("D:/ObjNext/Framework Date - 13052016 Updated/QuoteIDfile.txt"));
            //Read the file Line by Line till Null value is encountered
            while( (vVal = br.readLine()) != null){
                //display each line
                System.out.println(vVal);
                
                if(vObjCnt==1)
        		{
        			//vObj.clear();
        			//vObj.sendKeys(vVal);
        			Thread.sleep(2000);
        			//String vGetVal=vObj.getAttribute("value");
        			String vGetVal=vObj.getText();
        			System.out.println(vGetVal);
        			if(vGetVal.equals(vVal))
        			{
        				System.out.println("PASS");
        			}
        			else
        			{
        				System.out.println("FAIL");
        			}
        		}
        		else
        		{
        			System.out.println("FAIL");
        		}
            }
            
            br.close();
        } catch (FileNotFoundException e) {
            //Display error message if File was not found
            System.err.println("Unable to find the file");
        } catch (IOException e) {
            //Display error message if an exception is encounterd while reading the file
            System.err.println("Unable to read the file");
        }
       
        
        
	}
		
//	Set String Value	
	public void Fn_SetVal(WebElement vObj, int vObjCnt,String vVal,String vVal1,String vVal2) throws Throwable
	{
		try {
			if(vObjCnt==1)
			{
				if(vVal1.equalsIgnoreCase("Testdata"))
				{
					Fn_Readdata();
					//vObj.clear();
					vObj.sendKeys(vtestdata);
					vVal=vtestdata;
				}
				vObj.clear();
				vObj.sendKeys(vVal);
				String vGetVal=vObj.getAttribute("value").trim();
				if(vGetVal.equals(vVal))
				{
					System.out.println("PASS");
					hr.fgInsertResult(ds.vResFilePath, "Fn_SetVal", "Text Should be entered", vVal2+ " Text Entered", "PASS");
				}
				else
				{
					Fn_screenshot(ds.driver,"Fn_SetVal");
					System.out.println("FAIL");
					hr.fgInsertResult(ds.vResFilePath, "Fn_SetVal", "Text Should be entered",vVal2+" Text not Entered", "FAIL");
				}
			}
			else
			{
				Fn_screenshot(ds.driver,"Fn_SetVal");
				System.out.println("FAIL");
				hr.fgInsertResult(ds.vResFilePath, "Fn_SetVal", "Text Should be entered", vVal2+" Text not Entered", "FAIL");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//	Set integer Value
	public void Fn_SetIntVal(WebElement vObj, int vObjCnt,String vVal) throws Throwable
	{
		if(vObjCnt==1)
		{
			int vVal1=vVal.indexOf(".");
			vVal=vVal.substring(0, vVal1);
			vObj.clear();
			Thread.sleep(2000);
			vObj.sendKeys(vVal);
			String vGetVal=vObj.getAttribute("value").trim();
			if(vGetVal.equals(vVal))
			{
				System.out.println("PASS");
				hr.fgInsertResult(ds.vResFilePath, "Fn_SetVal", "\""+ vVal +"\"" + "Text Should be entered", "\""+vGetVal+ "\""+ "Text Entered", "PASS");
			}
			else
			{
				Fn_screenshot(ds.driver,"Fn_SetIntVal");
				System.out.println("FAIL");
				hr.fgInsertResult(ds.vResFilePath, "Fn_SetVal", "\""+ vVal +"\"" + "Text Should be entered","\""+vGetVal+ "\""+  "Text not Entered", "FAIL");
			}
		}
		else
		{
			Fn_screenshot(ds.driver,"Fn_SetIntVal");
			System.out.println("FAIL");
			hr.fgInsertResult(ds.vResFilePath, "Fn_SetVal", "\""+ vVal +"\"" + "Text Should be entered", "Text not Entered", "FAIL");
		}
	}
	
	public void Fn_SelectVal(WebElement vObj, int vObjCnt,String vVal) throws Throwable
	{
		if(vObjCnt==1)
		{
			vObj.clear();
			vObj.sendKeys(vVal);
			//Thread.sleep(1000);
			String vGetVal=vObj.getAttribute("value");
			System.out.println(vGetVal);
			if(vGetVal.equals(vVal))
			{
				System.out.println("PASS");
				hr.fgInsertResult(ds.vResFilePath, "Fn_SelectVal", "Value Should be selected", "Value selected", "PASS");
			}
			else
			{
				Fn_screenshot(ds.driver,"Fn_SelectVal");
				System.out.println("FAIL");
				hr.fgInsertResult(ds.vResFilePath, "Fn_SelectVal", "Value Should be selected", "Value not selected", "FAIL");
			}
		}
		else
		{
			Fn_screenshot(ds.driver,"Fn_SelectVal");
			System.out.println("FAIL");
			hr.fgInsertResult(ds.vResFilePath, "Fn_SelectVal", "Value Should be selected", "Value not selected", "FAIL");
		}
	}
	public void Fn_SelectValDropdown(WebElement vObj, int vObjCnt,String vVal) throws Throwable
	{
		if(vObjCnt==1)
		{
			Select se= new Select(vObj);
			se.selectByVisibleText(vVal);
			//Thread.sleep(1000);
			String vGetVal=vObj.getText();
			System.out.println(vGetVal);
			if(vGetVal.equals(vVal))
			{
				System.out.println("PASS");
				hr.fgInsertResult(ds.vResFilePath, "Fn_SelectVal", "Value Should be selected", "Value selected", "PASS");
			}
			else
			{
				Fn_screenshot(ds.driver,"Fn_SelectValDropdown");
				System.out.println("FAIL");
				hr.fgInsertResult(ds.vResFilePath, "Fn_SelectVal", "Value Should be selected", "Value not selected", "FAIL");
			}
		}
		else
		{
			Fn_screenshot(ds.driver,"Fn_SelectValDropdown");
			System.out.println("FAIL");
			hr.fgInsertResult(ds.vResFilePath, "Fn_SelectVal", "Value Should be selected", "Value not selected", "FAIL");
		}
	}
	
//	Sendkeys Arrow dropdown  
	public void Fn_ArrowDownSel(WebElement vObj, int vObjCnt) throws Throwable 
	{
		if(vObjCnt==1)
		{
			Thread.sleep(2000);
			vObj.sendKeys(Keys.ARROW_DOWN);
			System.out.println("PASS");
		
		}
		else
		{
			Fn_screenshot(ds.driver,"Fn_ArrowDownSel");
			System.out.println("FAIL");
			hr.fgInsertResult(ds.vResFilePath, "Fn_ArrowDownSel", "Value Should be selected", "Value not selected", "FAIL");
		}
	}
//	Sendkeys enter key press
	public void Fn_ArrowDownEnter(WebElement vObj, int vObjCnt) throws Throwable 
	{
		if(vObjCnt==1)
		{
			
			Thread.sleep(2000);
			vObj.sendKeys(Keys.ENTER);
			System.out.println("PASS");
		
		}
		else
		{
			Fn_screenshot(ds.driver,"Fn_ArrowDownEnter");
			System.out.println("FAIL");
			hr.fgInsertResult(ds.vResFilePath, "Fn_ArrowDownEnter", "Value Should be selected", "Value not selected", "FAIL");
		}
	}
	
	public void Fn_SelectfromList(WebElement vObj, int vObjCnt,String vVal) throws Throwable
	{
		int vVal1=vVal.indexOf(vVal);
		System.out.println(vVal1);
		vVal=vVal.substring(0, vVal1);
		if(vObjCnt==1)
		{
			//vObj.clear();
			vObj.sendKeys(vVal);
			Thread.sleep(2000);
			vObj.click();
			Thread.sleep(4000);
			
			String vGetVal=vObj.getText();
			
			System.out.println(vGetVal);
			if(vGetVal.contains(vVal))
			{
				System.out.println("PASS");
				hr.fgInsertResult(ds.vResFilePath, "Fn_SelectfromList", "Value Should be selected", "Value selected", "PASS");
			}
			else
			{
				Fn_screenshot(ds.driver,"Fn_SelectfromList");
				System.out.println("FAIL");
				hr.fgInsertResult(ds.vResFilePath, "Fn_SelectfromList", "Value Should be selected", "Value not selected", "FAIL");
			}
		}
		else
		{
			Fn_screenshot(ds.driver,"Fn_SelectfromList");
			System.out.println("FAIL");
			hr.fgInsertResult(ds.vResFilePath, "Fn_SelectfromList", "Value Should be selected", "Value not selected", "FAIL");
		}
	}
	
//	Select value from dropdown
	public void Fn_SelectfromDropdown(WebElement vObj, int vObjCnt,String vVal) throws Throwable
	{
		
		System.out.println(vVal);
		if(vObjCnt==1)
		{
			//vObj.clear();
			Thread.sleep(2000);
			vObj.click();


			String vGetVal=vObj.getAttribute("value");
			
//			int len=vGetVal.length();
//			System.out.println(len);
//			for (int i=0;i<=len;i++)
//			{
//				arrlist.add(vGetVal);
//				if (arrlist.get(i).equals(vVal))
//				{
//					System.out.println("Matched Done");
//				}
//				else
//				{
//					System.out.println("Not Matched");
//				}
//			}
			
			
			//String [] str=new String[];
			System.out.println(vGetVal);
			
			if(vGetVal.contains(vVal))
			{
				Select se=new Select(vObj);
				se.selectByVisibleText(vVal);
				
				System.out.println("PASS");
				hr.fgInsertResult(ds.vResFilePath, "Fn_SelectfromList", "Value Should be selected", "Value selected", "PASS");
			}
			else
			{
				Fn_screenshot(ds.driver,"Fn_SelectfromDropdown");
				System.out.println("FAIL 1");
				hr.fgInsertResult(ds.vResFilePath, "Fn_SelectfromList", "Value Should be selected", "Value not selected", "FAIL");
			}
		}
		else
		{
			Fn_screenshot(ds.driver,"Fn_SelectfromDropdown");
			System.out.println("FAIL");
			hr.fgInsertResult(ds.vResFilePath, "Fn_SelectfromList", "Value Should be selected", "Value not selected", "FAIL");
		}
	}
	
	public void Fn_Click(WebElement vObj, int vObjCnt, String vVal2) throws Throwable 
	{
		if(vObjCnt==1)
		{
			vObj.click();
			Thread.sleep(1000);
			System.out.println("PASS");
			//vObj.sendKeys(Keys.ENTER);
			hr.fgInsertResult(ds.vResFilePath, "Fn_Click", "Element Should be clicked", vVal2+" clicked successfully", "PASS");
			
		}
		else
		{
			Fn_screenshot(ds.driver,"Fn_Click");
			System.out.println("FAIL");
			hr.fgInsertResult(ds.vResFilePath, "Fn_Click", "Element Should be clicked", vVal2+" not clicked", "FAIL");
		}
	}
	
	
	public void Fn_SelectCheckBox(WebElement vObj, int vObjCnt) throws InterruptedException 
	{
		if(vObjCnt==1)
		{
			vObj.click();
			Thread.sleep(5000);
			System.out.println("PASS");
		}
		else
		{
			System.out.println("FAIL");
		}
	}
	
	
	public  void Fn_SwitchWindow(String vVal) throws InterruptedException  
	{
		Thread.sleep(5000);
		ds.driver.switchTo().frame(vVal);
	}
	
	public  void Fn_SwitchFrametoWindow() throws InterruptedException  
	{
		ds.driver.switchTo().defaultContent();
		Thread.sleep(2000);
	} 
	
	public  void Fn_alert() throws InterruptedException  
	{
		Alert alt=ds.driver.switchTo().alert();
		alt.accept();
//		String No="No";
//		String Yes="Yes";
//		Thread.sleep(5000);
//		Alert alt=ds.driver.switchTo().alert();
////		String message=alt.getText();
////		System.out.println(message);
////		System.out.println(vVal);
//		if(Yes.equalsIgnoreCase("Yes"))
//		{
//		alt.accept();
//		}
//		else
//		{
//			alt.dismiss();
//		}
		
	}
	
	//*** To create and write product name in a text file***//
	public void Fn_SetProductName(WebElement vObj, int vObjCnt,String vVal) throws InterruptedException
	{
		if(vObjCnt==1)
		{
			Random randominteger= new Random();
			int randomnumber=randominteger.hashCode();
			vVal= vVal+randomnumber;
			
			System.out.println(vVal);
			vObj.clear();
			vObj.sendKeys(vVal);
			Thread.sleep(4000);
			String vGetVal=vObj.getAttribute("value").trim();
			if(vGetVal.equals(vVal))
			{
				System.out.println("PASS");
			}
			else
			{
				System.out.println("FAIL");
			}
		}
		else
		{
			System.out.println("FAIL");
		}
		
		//****Create Text File**********
		final Formatter x;
		
		try{
			x= new Formatter ("D:/ObjNext/Framework Date - 13052016 Updated/ProductNamefile.txt");
			System.out.println("Created");
		}catch(Exception e){
			System.out.println("error");
		}
		
		//*****write product name into text file**********
		System.out.println(vVal);
		try {
            //String to written in the file - C:/Javaseleniumworld/Code.txt
            String content =vVal ; 
            File file = new File("D:/ObjNext/Framework Date - 13052016 Updated/ProductNamefile.txt");
  
            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }
            //Use BufferedWriter to write to the file
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();
  
 
        } catch (IOException e) {
            //Display error message if an exception is encounterd while writing the file
            e.printStackTrace();
        }
		
	}
	
	//***To append name - text and number****// 
	public void Fn_SetAppendName(WebElement vObj, int vObjCnt,String vVal) throws InterruptedException
	{
		if(vObjCnt==1)
		{
			Random randominteger= new Random();
			int randomnumber=randominteger.hashCode();
			vVal= (vVal+randomnumber).substring(0,4);
			
			System.out.println(vVal);
			vObj.clear();
			vObj.sendKeys(vVal);
			Thread.sleep(4000);
			String vGetVal=vObj.getAttribute("value").trim();
			if(vGetVal.equals(vVal))
			{
				System.out.println("PASS");
			}
			else
			{
				System.out.println("FAIL");
			}
		}
		else
		{
			System.out.println("FAIL");
		}
		
		
	}
	
	public String Fn_SetExcelName(String vVal) throws InterruptedException
	{
		String PrdName=vVal;
		return PrdName;
	}
	
	
	//**** Read Product Name from Text file ***
	public void Fn_ReadProductName(WebElement vObj, int vObjCnt,String vVal) throws InterruptedException, IOException
	{
		
		BufferedReader br = null;
        vVal = "";
        try {
            //Point the br object to the file you want to read 
            //File to be read line by line - C:/Javaseleniumworld/Code.txt
            br = new BufferedReader( new FileReader("D:/ObjNext/Framework Date - 13052016 Updated/ProductNamefile.txt"));
            //Read the file Line by Line till Null value is encountered
            while( (vVal = br.readLine()) != null){
                //display each line
                System.out.println(vVal);
                
                if(vObjCnt==1)
        		{
        			System.out.println(vVal);
        			vObj.clear();
        			vObj.sendKeys(vVal);
        			Thread.sleep(4000);
        			String vGetVal=vObj.getAttribute("value").trim();
        			if(vGetVal.equals(vVal))
        			{
        				System.out.println("PASS");
        			}
        			else
        			{
        				System.out.println("FAIL");
        			}
        		}
        		else
        		{
        			System.out.println("FAIL");
        		}
            }
            
            br.close();
        } catch (FileNotFoundException e) {
            //Display error message if File was not found
            System.err.println("Unable to find the file");
        } catch (IOException e) {
            //Display error message if an exception is encounterd while reading the file
            System.err.println("Unable to read the file");
        }        
        
	}
	
	//*** To write Quote id from BPO system into Text file***//
	public void Fn_GetQuoteID(WebElement vObj, int vObjCnt,String vVal) throws InterruptedException
	{
		if(vObjCnt==1)
		{			
			Thread.sleep(5000);
			
			vVal=vObj.getText();
			
			System.out.println("PASS");
		}
		else
		{
			System.out.println("FAIL");
		}
		
		//****Create Text File**********
		final Formatter x;
		
		try{
			x= new Formatter ("D:/ObjNext/Framework Date - 13052016 Updated/QuoteIDfile.txt");
			System.out.println("Created");
		}catch(Exception e){
			System.out.println("error");
		}
		
		//*****write Quote ID  into text file**********
		System.out.println(vVal);
		try {
            //String to written in the file - C:/Javaselenium/Code.txt
            String content =vVal ; 
            File file = new File("D:/ObjNext/Framework Date - 13052016 Updated/QuoteIDfile.txt");
  
            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }
            //Use BufferedWriter to write to the file
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();
  
 
        } catch (IOException e) {
            //Display error message if an exception is encounterd while writing the file
            e.printStackTrace();
        }
		
	}
	
	//*** To read quote id from text file.***//
	public void Fn_SetQuoteID(WebElement vObj, int vObjCnt,String vVal) throws InterruptedException, IOException
	{
		
		BufferedReader br = null;
        vVal = "";
        try {
            //Point the br object to the file you want to read 
            //File to be read line by line - C:/Javaselenium/Code.txt
            br = new BufferedReader( new FileReader("D:/ObjNext/Framework Date - 13052016 Updated/QuoteIDfile.txt"));
            //Read the file Line by Line till Null value is encountered
            while( (vVal = br.readLine()) != null){
                //display each line
                System.out.println(vVal);
                
                if(vObjCnt==1)
        		{
        			System.out.println(vVal);
        			vObj.clear();
        			vObj.sendKeys(vVal);
        			Thread.sleep(5000);
        			String vGetVal=vObj.getAttribute("value").trim();
        			if(vGetVal.equals(vVal))
        			{
        				System.out.println("PASS");
        			}
        			else
        			{
        				System.out.println("FAIL");
        			}
        		}
        		else
        		{
        			System.out.println("FAIL");
        		}
            }
            
            br.close();
        } catch (FileNotFoundException e) {
            //Display error message if File was not found
            System.err.println("Unable to find the file");
        } catch (IOException e) {
            //Display error message if an exception is encounterd while reading the file
            System.err.println("Unable to read the file");
        }        
        
	}
	
	public void Fn_SetPO(WebElement vObj, int vObjCnt,String vVal) throws InterruptedException, IOException
	{
		
		BufferedReader br = null;
        vVal = "";
        try {
            //Point the br object to the file you want to read 
            //File to be read line by line - C:/Javaseleniumworld/Code.txt
            br = new BufferedReader( new FileReader("D:/ObjNext/Framework Date - 13052016 Updated/QuoteIDfile.txt"));
            //Read the file Line by Line till Null value is encountered
            while( (vVal = br.readLine()) != null){
                //display each line
            	vVal="PO"+vVal;
                System.out.println(vVal);
                
                if(vObjCnt==1)
        		{
        			System.out.println(vVal);
        			vObj.clear();
        			vObj.sendKeys(vVal);
        			Thread.sleep(5000);
        			String vGetVal=vObj.getAttribute("value").trim();
        			if(vGetVal.equals(vVal))
        			{
        				System.out.println("PASS");
        			}
        			else
        			{
        				System.out.println("FAIL");
        			}
        		}
        		else
        		{
        			System.out.println("FAIL");
        		}
            }
            
            br.close();
        } catch (FileNotFoundException e) {
            //Display error message if File was not found
            System.err.println("Unable to find the file");
        } catch (IOException e) {
            //Display error message if an exception is encounterd while reading the file
            System.err.println("Unable to read the file");
        }        
        
	}
	
	public void Fn_Upload(WebElement vObj, int vObjCnt) throws InterruptedException, AWTException 
	{
		if(vObjCnt==1)
		{
			
			vObj.sendKeys("C:/Users/brinderjeet.singh/Downloads/uploadfile.pdf");
			//vObj.click();
			Thread.sleep(8000);
			
			vObj.sendKeys(Keys.ENTER);
			
			
			
//			ds.driver.findElement(By.xpath("//input[@class='button'][@type='submit'][@value='New Attachment']")).click();
//			ds.driver.findElement(By.xpath("//input[@type='file'][@name='binFile']")).click();
//			//dr.findElement(By.xpath("//input[@type='file'][@name='binFile']")).click();

			//setClipboardData("C:\\Users\\Brinder\\Desktop\\body.jpg");
			//native key strokes for CTRL, V and ENTER keys
//			Robot robot = new Robot();
//			robot.keyPress(KeyEvent.VK_CONTROL);
//			robot.keyPress(KeyEvent.VK_V);
//			robot.keyRelease(KeyEvent.VK_V);
//			robot.keyRelease(KeyEvent.VK_CONTROL);
//			robot.keyPress(KeyEvent.VK_ENTER);
//			robot.keyRelease(KeyEvent.VK_ENTER);

			Thread.sleep(2000);
			System.out.println("PASS");

//			dr.findElement(By.xpath("//input[@class='button'][@type='submit'][@value='Upload']")).click();
//			Thread.sleep(10000);
//
//			element.SendKeys(Keys.Enter);

			
		}
		else
		{
			System.out.println("FAIL");
		}
	}
	


//public static void highlightCustom( dr,WebElement element) {
//	ds.driver;
//for (int i=1 ; i<=7;i++){
//((JavascriptExecutor)dr).executeScript("arguments[0].style.backgroundColor='black';", element);
//if (i==7){
//((JavascriptExecutor) dr).executeScript("arguments[0].style.backgroundColor='white';", element);
//}
//}
//}
//	
//	
	public void Fn_MouseOver(WebElement vObj, int vObjCnt) throws Throwable 
	{
		if(vObjCnt==1)
		{
			Actions act=new Actions(ds.driver);
			act.moveToElement(vObj).perform();
			act.click().build().perform();
			//Thread.sleep(1000);
			System.out.println("PASS");
			//hr.fgInsertResult(ds.vResFilePath, "Fn_MouseOver", "Object should be mouseover successfully", "Object mouseovered", "PASS");
		}
		else
		{
			Fn_screenshot(ds.driver,"Fn_MouseOver");
			System.out.println("FAIL");
			//hr.fgInsertResult(ds.vResFilePath, "Fn_MouseOver", "Object should be mouseover successfully", "Object did not mouseover", "FAIL");
		}
	}
//	To upload file 
	public void Fn_UploadFile(String vVal) throws IOException, Exception
	{
		Thread.sleep(1000);
		//Runtime.getRuntime().exec("D:\\AutoITScript\\UploadFile.exe");
		
		// Specify the file location with extension
		StringSelection sel=new StringSelection(vVal);
		
		//Copy to clipboard
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(sel, null);
		Thread.sleep(1000);
		Robot r=new Robot();
		//Press CTRL+V
		r.keyPress(KeyEvent.VK_CONTROL);
		r.keyPress(KeyEvent.VK_V);
		
		//Release CTRL+V
		r.keyRelease(KeyEvent.VK_CONTROL);
		r.keyRelease(KeyEvent.VK_V);
		
		//Press Enter
		Thread.sleep(2000);
		r.keyPress(KeyEvent.VK_ENTER);
		r.keyRelease(KeyEvent.VK_ENTER);
		
	}
	
//	To  verify a downloaded  file
	public void Fn_VerifyFileDownloaded(String vPath, String vFilename) throws Throwable
    {
          Thread.sleep(3000);
          boolean flag = false;
        File dir = new File(vPath);
        File[] dir_contents = dir.listFiles();
              
        for (int i = 0; i < dir_contents.length; i++)
        {
            if (dir_contents[i].getName().equals(vFilename))
                flag=true;
        }
        if (flag==true)
        {
          System.out.println("PASS");
                hr.fgInsertResult(ds.vResFilePath, "Fn_VerifyFileDownloaded", "File should be downloaded successfully", "File downloaded successfully", "PASS");
        }
        else
        {
        	Fn_screenshot(ds.driver,"Fn_VerifyFileDownloaded");
          System.out.println("FAIL");
                hr.fgInsertResult(ds.vResFilePath, "Fn_VerifyFileDownloaded", "File should be downloaded successfully", "File not downloaded successfully", "FAIL");
        }
          
    }

	public void Fn_SetValInteger(WebElement vObj, int vObjCnt,String vVal) throws InterruptedException
	{
		int vVal1=vVal.indexOf(".");
		vVal=vVal.substring(0, vVal1);
		//vVal=vVal.replace(".", "");
		if(vObjCnt==1)
		{
			vObj.clear();
			vObj.sendKeys(vVal);
			Thread.sleep(4000);
			String vGetVal=vObj.getAttribute("value").trim();
			System.out.println(vGetVal);
			if(vGetVal.equals(vVal))
			{
				System.out.println("PASS");				
			}
			else
			{
				System.out.println("FAIL");
			}
		}
		else
		{
			System.out.println("FAIL");
		}
	}
	
	public void Fn_ConcatText(WebElement vObj, int vObjCnt,String vVal) throws InterruptedException, IOException
	{
		
		BufferedReader br = null;
       // vVal = "";
		String quoteid;
        try {
            //Point the br object to the file you want to read 
            //File to be read line by line - C:/Javaseleniumworld/Code.txt
            br = new BufferedReader( new FileReader("D:/ObjNext/Framework Date - 13052016 Updated/QuoteIDfile.txt"));
            //Read the file Line by Line till Null value is encountered
            while( (quoteid=br.readLine()) != null){
                //display each line
            	vVal=vVal+quoteid;
                System.out.println(vVal);
                
                if(vObjCnt==1)
        		{
        			System.out.println(vVal);
        			vObj.clear();
        			vObj.sendKeys(vVal);
        			Thread.sleep(5000);
        			String vGetVal=vObj.getAttribute("value").trim();
        			if(vGetVal.equals(vVal))
        			{
        				System.out.println("PASS");
        			}
        			else
        			{
        				System.out.println("FAIL");
        			}
        		}
        		else
        		{
        			System.out.println("FAIL");
        		}
            }
            
            br.close();
        } catch (FileNotFoundException e) {
            //Display error message if File was not found
            System.err.println("Unable to find the file");
        } catch (IOException e) {
            //Display error message if an exception is encounterd while reading the file
            System.err.println("Unable to read the file");
        }        
        
	}
	
	
//*** To create and write Quote id from BPO system into Text file***//
		public void Fn_GetProductQty(WebElement vObj, int vObjCnt) throws InterruptedException
		{			
			if(vObjCnt==1)
			{	
				String vGetVal=vObj.getText().trim();
				int vVal1=vGetVal.indexOf(",");
    			//vGetVal=vGetVal.substring(0, vVal1);
    			vGetVal=vGetVal.replace(",", "");
				System.out.println(vGetVal);
			
				//****Create Text File**********
			final Formatter x;
			
			try{
				x= new Formatter ("D:/ObjNext/Framework Date - 13052016 Updated/ProductQuantityfile.txt");
				System.out.println("Created");
			}catch(Exception e){
				System.out.println("error");
			}
			
			//*****write product name into text file**********
			System.out.println(vGetVal);
			try {
	            //String to written in the file - C:/Javaseleniumworld/Code.txt
	            String content =vGetVal ; 
	            File file = new File("D:/ObjNext/Framework Date - 13052016 Updated/ProductQuantityfile.txt");
	  
	            // if file doesnt exists, then create it
	            if (!file.exists()) {
	                file.createNewFile();
	            }
	            //Use BufferedWriter to write to the file
	            FileWriter fw = new FileWriter(file.getAbsoluteFile());
	            BufferedWriter bw = new BufferedWriter(fw);
	            bw.write(content);
	            bw.close();
	  
	 
	        } catch (IOException e) {
	            //Display error message if an exception is encounterd while writing the file
	            e.printStackTrace();
	        }
			
		}
		}
		
//*** To read  quote id from text file.***//
		public void Fn_SetProductQuantity(WebElement vObj, int vObjCnt,String vVal) throws InterruptedException, IOException
		{
			
			BufferedReader br = null;
	        vVal = "";
	        try {
	            //Point the br object to the file you want to read 
	            //File to be read line by line - C:/Javaselenium/Code.txt
	            br = new BufferedReader( new FileReader("D:/ObjNext/Framework Date - 13052016 Updated/ProductQuantityfile.txt"));
	            //Read the file Line by Line till Null value is encountered
	            while( (vVal = br.readLine()) != null){
	                //display each line
	                System.out.println(vVal);
	                
	                if(vObjCnt==1)
	        		{
	        			System.out.println(vVal);
	        			vObj.clear();
	        			vObj.sendKeys(vVal);
	        			Thread.sleep(5000);
	        			String vGetVal=vObj.getText().trim();
	        			if(vGetVal.equals(vVal))
	        			{
	        				System.out.println("PASS");
	        			}
	        			else
	        			{
	        				System.out.println("FAIL");
	        			}
	        		}
	        		else
	        		{
	        			System.out.println("FAIL");
	        		}
	            }
	            
	            br.close();
	        } catch (FileNotFoundException e) {
	            //Display error message if File was not found
	            System.err.println("Unable to find the file");
	        } catch (IOException e) {
	            //Display error message if an exception is encounterd while reading the file
	            System.err.println("Unable to read the file");
	        }        
	        
		}
		
		
//*** To read and verify quote id from text file.***//
		public void Fn_VerifyProductQuantity(WebElement vObj, int vObjCnt,String vVal) throws InterruptedException, IOException
		{
			
			BufferedReader br = null;
	        vVal = "";
	        try {
	            //Point the br object to the file you want to read 
	            //File to be read line by line - C:/Javaselenium/Code.txt
	            br = new BufferedReader( new FileReader("D:/ObjNext/Framework Date - 13052016 Updated/ProductQuantityfile.txt"));
	            //Read the file Line by Line till Null value is encountered
	            while( (vVal = br.readLine()) != null){
	                //display each line
	                System.out.println(vVal);
	                
	                if(vObjCnt==1)
	        		{
	        			
	        			String vGetVal=vObj.getText();
	        			int vVal1=vVal.indexOf(",");
	        			//vGetVal=vGetVal.substring(0, vVal1);
	        			vGetVal=vGetVal.replace(",", "");
	        			System.out.println(vGetVal);
	        			if(vGetVal.equals(vVal))
	        			{
	        				System.out.println("PASS");
	        			}
	        			else
	        			{
	        				System.out.println("FAIL");
	        			}
	        		}
	        		else
	        		{
	        			System.out.println("FAIL");
	        		}
	            }
	            
	            br.close();
	        } catch (FileNotFoundException e) {
	            //Display error message if File was not found
	            System.err.println("Unable to find the file");
	        } catch (IOException e) {
	            //Display error message if an exception is encounterd while reading the file
	            System.err.println("Unable to read the file");
	        }
		}
	
		public void Fn_GetDropdownValue(WebElement vObj, int vObjCnt) throws Throwable
		{                
			if(vObjCnt==1)
			{
				Thread.sleep(1000);
				//String vGetVal=vObj.getAttribute("value");
				
				companyname=vObj.getText().trim();
				System.out.println(companyname);
				
				System.out.println("PASS");
				hr.fgInsertResult(ds.vResFilePath, "Fn_GetDropdownValue", "Text Should be copied", "Text copied", "PASS");	
			}
			else
			{
				Fn_screenshot(ds.driver,"Fn_GetDropdownValue");
				System.out.println("FAIL vObjCnt==1");
				hr.fgInsertResult(ds.vResFilePath, "Fn_GetDropdownValue", "Text Should be copied", "Text not copied", "FAIL");
			}
	
		}
//**********Get Text from textbox***************************
		public void Fn_GetTextboxValue(WebElement vObj, int vObjCnt) throws Throwable
		{                
			if(vObjCnt==1)
			{
				Thread.sleep(3000);								
//				companynameform=vObj.getText().trim();
				
				companynameform=vObj.getAttribute("value");
				System.out.println(companyname);
				System.out.println(companynameform);
				if(companyname!=null){
				if(companynameform.equals(companyname))
				{
				System.out.println("PASS");
				hr.fgInsertResult(ds.vResFilePath, "Fn_GetTextboxValue", "Text Should be matched", "Text matched", "PASS");
				}
				else
				{
					Fn_screenshot(ds.driver,"Fn_GetTextboxValue");
					System.out.println("FAIL Company name not equal");
					hr.fgInsertResult(ds.vResFilePath, "Fn_GetTextboxValue", "Text Should be matched", "Text not matched", "FAIL");
				}
				}
				else
				{
					Fn_screenshot(ds.driver,"Fn_GetTextboxValue");
					System.out.println("FAIL company name not found");
					hr.fgInsertResult(ds.vResFilePath, "Fn_GetTextboxValue", "Text Should be matched", "Text not matched", "FAIL");
				}
			}
			else
			{
				Fn_screenshot(ds.driver,"Fn_GetTextboxValue");
				System.out.println("FAIL vObjCnt==1");
				hr.fgInsertResult(ds.vResFilePath, "Fn_GetTextboxValue", "Text Should be matched", "Text not matched", "FAIL");
			}
	
		}
// *********Get text from label************************
		public void Fn_GetLabelValue(WebElement vObj, int vObjCnt) throws Throwable
		{                
			if(vObjCnt==1)
			{
				Thread.sleep(3000);								
				companynameheader=vObj.getText().trim();
				
//				companynameform=vObj.getAttribute("value");
				System.out.println(companyname);
				System.out.println(companynameheader);
				if(companyname!=null){
				if(companynameheader.equals(companyname))
				{
				System.out.println("PASS");
				hr.fgInsertResult(ds.vResFilePath, "Fn_GetLabelValue", "Text Should be matched", "Text matched", "PASS");
				}
				else
				{
					Fn_screenshot(ds.driver,"Fn_GetLabelValue");
					System.out.println("FAIL Company name not equal");
					hr.fgInsertResult(ds.vResFilePath, "Fn_GetLabelValue", "Text Should be matched", "Text not matched", "FAIL");
				}
				}
				else
				{
					Fn_screenshot(ds.driver,"Fn_GetLabelValue");
					System.out.println("FAIL company name not found");
					hr.fgInsertResult(ds.vResFilePath, "Fn_GetLabelValue", "Text Should be matched", "Text not matched", "FAIL");
				}
			}
			else
			{
				Fn_screenshot(ds.driver,"Fn_GetLabelValue");
				System.out.println("FAIL vObjCnt==1");
				hr.fgInsertResult(ds.vResFilePath, "Fn_GetLabelValue", "Text Should be matched", "Text not matched", "FAIL");
			}
	
		}
		
//		******************Get Attribute value of any tagname*************************
			
		public void Fn_GetAttribute(WebElement vObj, int vObjCnt,String vVal, String vVal2) throws Throwable
		{                
			if(vObjCnt==1)
			{
				Thread.sleep(3000);								
				String vGetAttrib=vObj.getAttribute(vVal);
				System.out.println(vGetAttrib);
				System.out.println(vVal2);
				if(vGetAttrib.contains(vVal2))
				{
				System.out.println("PASS");
				hr.fgInsertResult(ds.vResFilePath, "Fn_GetTextboxValue", "Text Should be matched", "Text matched", "PASS");
				}
				else
				{
					Fn_screenshot(ds.driver,"Fn_GetAttribute");
					System.out.println("FAIL Attribute");
					hr.fgInsertResult(ds.vResFilePath, "Fn_GetTextboxValue", "Text Should be matched", "Text not matched", "FAIL");
				}
				
			}
			else
			{
				Fn_screenshot(ds.driver,"Fn_GetAttribute");
				System.out.println("FAIL vObjCnt");
				hr.fgInsertResult(ds.vResFilePath, "Fn_GetTextboxValue", "Text Should be matched", "Text not matched", "FAIL");
			}
	
		}
	
//	Read data from excel
		public void Fn_Readdata()
		{
			Xls_Reader xrd=new Xls_Reader(System.getProperty("user.dir")+"\\src\\TestData\\GMTestData.xlsx");
			vtestdata= xrd.getCellData(ds.vProjectName, ds.vParam1, ds.t);
		}
}
