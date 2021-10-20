package Library.Generic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.NoSuchElementException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;

import DriverScript.DriverScript;
import Library.Utility.Xls_Reader;

public class KeywordDriver {
	
	public WebElement vObj=null;
	public int vObjCnt=0;
	public double vExpCnt=0;
	public String ObjString,vExp,vVal,vVal1,vVal2,vVal3;
	public double ExpCnt;
	
	GenericMethods gm=new GenericMethods();
	DriverScript dscript=new DriverScript();
	public KeywordDriver(String vActKeyword,Xls_Reader xrs,int m,String vModuleName) throws Throwable
	{
		Properties prop=new Properties();
		FileInputStream fis=new FileInputStream(System.getProperty("user.dir")+"\\src\\ObjectRepository\\Objects.properties");
		prop.load(fis);
			
		switch (vActKeyword.trim())
		{
		case "Fn_LaunchApp":
			gm.Fn_LaunchApp();
			break;
		case "Fn_verifyTitle":
			vExp=xrs.getCellData(vModuleName, "Param1", m);
			gm.Fn_verifyTitle(vExp);
			break;
		case "Fn_wait":
			vVal=xrs.getCellData(vModuleName, "Param1", m);
			gm.Fn_wait(vVal);
			break;
		case "Fn_highlight":
			ObjString=prop.getProperty(xrs.getCellData(vModuleName, "Object1", m).trim());
			vObj=ExtractObject(ObjString);
			vObjCnt=ObjectCounter(ObjString);
			try
			{
				 ExpCnt=Double.parseDouble(xrs.getCellData(vModuleName, "Param1", m));
			}catch(Throwable t)
			{
				System.out.println(t.getMessage());
			}
			
			if(ExpCnt>0)
			{
				vExpCnt=ExpCnt;
			}
			else
			{
				vExpCnt=1;
			}
			vVal2=xrs.getCellData(vModuleName, "Param3", m);
			gm.Fn_highlight(vObj, vObjCnt, vExpCnt,vVal2);
			break;
		case "Fn_verifyObjectExist":
			ObjString=prop.getProperty(xrs.getCellData(vModuleName, "Object1", m).trim());
			vObj=ExtractObject(ObjString);
			vObjCnt=ObjectCounter(ObjString);
			try
			{
				 ExpCnt=Double.parseDouble(xrs.getCellData(vModuleName, "Param1", m));
			}catch(Throwable t)
			{
				System.out.println(t.getMessage());
			}
			
			if(ExpCnt>0)
			{
				vExpCnt=ExpCnt;
			}
			else
			{
				vExpCnt=1;
			}
			vVal2=xrs.getCellData(vModuleName, "Param3", m);
			gm.Fn_verifyObjectExist(vObjCnt, vExpCnt,vVal2);
			break;
		case "Fn_SetVal":
			 ObjString=prop.getProperty(xrs.getCellData(vModuleName, "Object1", m).trim());
			vObj=ExtractObject(ObjString);
			vObjCnt=ObjectCounter(ObjString);
			vVal=xrs.getCellData(vModuleName, "Param1", m);
			vVal1=xrs.getCellData(vModuleName, "Param2", m);
			vVal2=xrs.getCellData(vModuleName, "Param3", m);
			gm.Fn_SetVal(vObj,vObjCnt,vVal,vVal1,vVal2);
			break;
		case "Fn_SetIntVal":
			 ObjString=prop.getProperty(xrs.getCellData(vModuleName, "Object1", m).trim());
			vObj=ExtractObject(ObjString);
			vObjCnt=ObjectCounter(ObjString);
			vVal=xrs.getCellData(vModuleName, "Param1", m);
			gm.Fn_SetIntVal(vObj, vObjCnt,vVal);
			break;
		case "Fn_SelectfromList":
			 ObjString=prop.getProperty(xrs.getCellData(vModuleName, "Object1", m).trim());
			vObj=ExtractObject(ObjString);
			vObjCnt=ObjectCounter(ObjString);
			vVal=xrs.getCellData(vModuleName, "Param1", m);
			gm.Fn_SelectfromList(vObj, vObjCnt,vVal);
			break;
		case "Fn_SelectfromDropdown":
			 ObjString=prop.getProperty(xrs.getCellData(vModuleName, "Object1", m).trim());
			vObj=ExtractObject(ObjString);
			vObjCnt=ObjectCounter(ObjString);
			vVal=xrs.getCellData(vModuleName, "Param1", m);
			gm.Fn_SelectfromDropdown(vObj, vObjCnt,vVal);
			break;
		case "Fn_SelectVal":
			 ObjString=prop.getProperty(xrs.getCellData(vModuleName, "Object1", m).trim());
			vObj=ExtractObject(ObjString);
			vObjCnt=ObjectCounter(ObjString);
			vVal=xrs.getCellData(vModuleName, "Param1", m);
			gm.Fn_SelectVal(vObj, vObjCnt,vVal);
			break;
		case "Fn_SelectValDropdown":
			 ObjString=prop.getProperty(xrs.getCellData(vModuleName, "Object1", m).trim());
			vObj=ExtractObject(ObjString);
			vObjCnt=ObjectCounter(ObjString);
			vVal=xrs.getCellData(vModuleName, "Param1", m);
			gm.Fn_SelectValDropdown(vObj, vObjCnt,vVal);
			break;
		case "Fn_verifyProdName":
			 ObjString=prop.getProperty(xrs.getCellData(vModuleName, "Object1", m).trim());
			vObj=ExtractObject(ObjString);
			vObjCnt=ObjectCounter(ObjString);
			//vVal=xrs.getCellData(vModuleName, "Param1", m);
			gm.Fn_verifyProdName(vObj, vObjCnt,vVal);
			break;
		case "Fn_verifyQuoteID":
			 ObjString=prop.getProperty(xrs.getCellData(vModuleName, "Object1", m).trim());
			vObj=ExtractObject(ObjString);
			vObjCnt=ObjectCounter(ObjString);
			//vVal=xrs.getCellData(vModuleName, "Param1", m);
			gm.Fn_verifyQuoteID(vObj, vObjCnt,vVal);
			break;
		case "Fn_verifyText":
			 ObjString=prop.getProperty(xrs.getCellData(vModuleName, "Object1", m).trim());
			vObj=ExtractObject(ObjString);
			vObjCnt=ObjectCounter(ObjString);
			vVal=xrs.getCellData(vModuleName, "Param1", m);
			vVal2=xrs.getCellData(vModuleName, "Param3", m);
			gm.Fn_verifyText(vObj, vObjCnt,vVal,vVal2);
			break;
		case "Fn_Click":
			 ObjString=prop.getProperty(xrs.getCellData(vModuleName, "Object1", m).trim());
			vObj=ExtractObject(ObjString);
			vObjCnt=ObjectCounter(ObjString);
			vVal2=xrs.getCellData(vModuleName, "Param3", m);
			gm.Fn_Click(vObj, vObjCnt,vVal2);
			break;
		case "Fn_ArrowDownSel":
			 ObjString=prop.getProperty(xrs.getCellData(vModuleName, "Object1", m).trim());
			vObj=ExtractObject(ObjString);
			vObjCnt=ObjectCounter(ObjString);
			gm.Fn_ArrowDownSel(vObj, vObjCnt);
			break;
		case "Fn_ArrowDownEnter":
			 ObjString=prop.getProperty(xrs.getCellData(vModuleName, "Object1", m).trim());
			vObj=ExtractObject(ObjString);
			vObjCnt=ObjectCounter(ObjString);
			gm.Fn_ArrowDownEnter(vObj, vObjCnt);
			break;
		case "Fn_SelectCheckBox":
			 ObjString=prop.getProperty(xrs.getCellData(vModuleName, "Object1", m).trim());
			vObj=ExtractObject(ObjString);
			vObjCnt=ObjectCounter(ObjString);
			gm.Fn_SelectCheckBox(vObj, vObjCnt);
			break;
		case "Fn_SwitchWindow":
			vVal=xrs.getCellData(vModuleName, "Param1", m);
			gm.Fn_SwitchWindow(vVal);
			break;
		case "Fn_alert":
			vVal=xrs.getCellData(vModuleName, "Param1", m);
			gm.Fn_alert();
			break;
		case "Fn_SwitchFrametoWindow":
			gm.Fn_SwitchFrametoWindow();
			break;
			
		case "Fn_SetProductName":
			 ObjString=prop.getProperty(xrs.getCellData(vModuleName, "Object1", m).trim());
			vObj=ExtractObject(ObjString);
			vObjCnt=ObjectCounter(ObjString);
			vVal=xrs.getCellData(vModuleName, "Param1", m);
			//vVal2=xrs.getCellData(vModuleName, "Param2", m);
			//vVal3= vVal2.substring(0,6);
			//vVal=vVal1.substring(0,6);
			gm.Fn_SetProductName(vObj, vObjCnt,vVal);
			break;
			
		case "Fn_SetAppendName":
			 ObjString=prop.getProperty(xrs.getCellData(vModuleName, "Object1", m).trim());
			vObj=ExtractObject(ObjString);
			vObjCnt=ObjectCounter(ObjString);
			vVal=xrs.getCellData(vModuleName, "Param1", m);
			//vVal2=xrs.getCellData(vModuleName, "Param2", m);
			//vVal3= vVal2.substring(0,6);
			//vVal=vVal1.substring(0,6);
			gm.Fn_SetAppendName(vObj, vObjCnt,vVal);
			break;
		
			
		case "Fn_SetExcelName":
			ObjString=prop.getProperty(xrs.setCellData(vModuleName, "Param3", m, vVal));
			//vObj=ExtractObject(ObjString);
			//vObjCnt=ObjectCounter(ObjString);
			vVal1=xrs.getCellData(vModuleName, "Param1", m);
			vVal2=xrs.getCellData(vModuleName, "Param2", m);
			vVal3= vVal2.substring(0,6);
			vVal=vVal1+vVal3;
			gm.Fn_SetExcelName(vVal);
			break;
			
//		case "Fn_CreateFile":
//			gm.Fn_CreateFile();
//			break;
//			
//		case "Fn_WriteProductName":
////			 ObjString=prop.getProperty(xrs.getCellData(vModuleName, "Object1", m).trim());
////			vObj=ExtractObject(ObjString);
////			vObjCnt=ObjectCounter(ObjString);
//			vVal1=xrs.getCellData(vModuleName, "Param1", m-1);
//			vVal2=xrs.getCellData(vModuleName, "Param2", m-1);
//			vVal3= vVal2.substring(0,3);
//			vVal=vVal1+vVal3;
//			System.out.println(vVal);
//			gm.Fn_WriteProductName(vVal);
//			break;
			
		case "Fn_ReadProductName":
			ObjString=prop.getProperty(xrs.getCellData(vModuleName, "Object1", m).trim());
			vObj=ExtractObject(ObjString);
			vObjCnt=ObjectCounter(ObjString);
//			vVal1=xrs.getCellData(vModuleName, "Param1", m);
//			vVal2=xrs.getCellData(vModuleName, "Param2", m);
//			vVal3= vVal2.substring(0,6);
			//vVal=vVal1+vVal3;
			gm.Fn_ReadProductName(vObj, vObjCnt,vVal);
			break;
		case "Fn_GetQuoteID":
			 ObjString=prop.getProperty(xrs.getCellData(vModuleName, "Object1", m).trim());
			vObj=ExtractObject(ObjString);
			vObjCnt=ObjectCounter(ObjString);
			vVal=xrs.getCellData(vModuleName, "Param1", m);
			//vVal2=xrs.getCellData(vModuleName, "Param2", m);
			//vVal3= vVal2.substring(0,6);
			//vVal=vVal1.substring(0,6);
			gm.Fn_GetQuoteID(vObj, vObjCnt,vVal);
			break;
		case "Fn_SetQuoteID":
			ObjString=prop.getProperty(xrs.getCellData(vModuleName, "Object1", m).trim());
			vObj=ExtractObject(ObjString);
			vObjCnt=ObjectCounter(ObjString);
//			vVal1=xrs.getCellData(vModuleName, "Param1", m);
//			vVal2=xrs.getCellData(vModuleName, "Param2", m);
//			vVal3= vVal2.substring(0,6);
			//vVal=vVal1+vVal3;
			gm.Fn_SetQuoteID(vObj, vObjCnt,vVal);
			break;
		case "Fn_GetProductQty":
			 ObjString=prop.getProperty(xrs.getCellData(vModuleName, "Object1", m).trim());
			vObj=ExtractObject(ObjString);
			vObjCnt=ObjectCounter(ObjString);
			//vVal=xrs.getCellData(vModuleName, "Param1", m);
			//vVal2=xrs.getCellData(vModuleName, "Param2", m);
			//vVal3= vVal2.substring(0,6);
			//vVal=vVal1.substring(0,6);
			gm.Fn_GetProductQty(vObj, vObjCnt);
			break;
		case "Fn_SetProductQuantity":
			ObjString=prop.getProperty(xrs.getCellData(vModuleName, "Object1", m).trim());
			vObj=ExtractObject(ObjString);
			vObjCnt=ObjectCounter(ObjString);
			gm.Fn_SetProductQuantity(vObj, vObjCnt,vVal);
			break;
		case "Fn_VerifyProductQuantity":
			 ObjString=prop.getProperty(xrs.getCellData(vModuleName, "Object1", m).trim());
			vObj=ExtractObject(ObjString);
			vObjCnt=ObjectCounter(ObjString);
			//vVal=xrs.getCellData(vModuleName, "Param1", m);
			gm.Fn_VerifyProductQuantity(vObj, vObjCnt,vVal);
			break;
		case "Fn_SetPO":
			ObjString=prop.getProperty(xrs.getCellData(vModuleName, "Object1", m).trim());
			vObj=ExtractObject(ObjString);
			vObjCnt=ObjectCounter(ObjString);
//			vVal1=xrs.getCellData(vModuleName, "Param1", m);
//			vVal2=xrs.getCellData(vModuleName, "Param2", m);
//			vVal3= vVal2.substring(0,6);
			//vVal=vVal1+vVal3;
			gm.Fn_SetPO(vObj, vObjCnt,vVal);
			break;
		case "Fn_Upload":
			 ObjString=prop.getProperty(xrs.getCellData(vModuleName, "Object1", m).trim());
			vObj=ExtractObject(ObjString);
			vObjCnt=ObjectCounter(ObjString);
			gm.Fn_Upload(vObj, vObjCnt);
			break;
		case "Fn_UploadFile":
			vVal=xrs.getCellData(vModuleName, "Param1", m);
			gm.Fn_UploadFile(vVal);
			break;
		case "Fn_VerifyFileDownloaded":
            vVal=xrs.getCellData(vModuleName, "Param1", m);
            vVal1=xrs.getCellData(vModuleName, "Param2", m);
            gm.Fn_VerifyFileDownloaded(vVal,vVal1);
            break;

		case "Fn_SetValInteger":
			 ObjString=prop.getProperty(xrs.getCellData(vModuleName, "Object1", m).trim());
			vObj=ExtractObject(ObjString);
			vObjCnt=ObjectCounter(ObjString);
			vVal=xrs.getCellData(vModuleName, "Param1", m);
			gm.Fn_SetValInteger(vObj, vObjCnt,vVal);
			break;
		case "Fn_ConcatText":
			ObjString=prop.getProperty(xrs.getCellData(vModuleName, "Object1", m).trim());
			vObj=ExtractObject(ObjString);
			vObjCnt=ObjectCounter(ObjString);
			vVal=xrs.getCellData(vModuleName, "Param1", m);
//			vVal2=xrs.getCellData(vModuleName, "Param2", m);
//			vVal3= vVal2.substring(0,6);
			//vVal=vVal1+vVal3;
			gm.Fn_ConcatText(vObj, vObjCnt,vVal);
			break;
		case "Fn_MouseOver":
			gm.Fn_MouseOver(vObj,vObjCnt);
			break; 
		case "Fn_GetDropdownValue":
			 ObjString=prop.getProperty(xrs.getCellData(vModuleName, "Object1", m).trim());
			 vObj=ExtractObject(ObjString);
			 vObjCnt=ObjectCounter(ObjString);
			 gm.Fn_GetDropdownValue(vObj, vObjCnt);
			 break;
		case "Fn_GetTextboxValue":
			 ObjString=prop.getProperty(xrs.getCellData(vModuleName, "Object1", m).trim());
			 vObj=ExtractObject(ObjString);
			 vObjCnt=ObjectCounter(ObjString);
			 gm.Fn_GetTextboxValue(vObj, vObjCnt);
			 break;
		case "Fn_GetLabelValue":
			 ObjString=prop.getProperty(xrs.getCellData(vModuleName, "Object1", m).trim());
			 vObj=ExtractObject(ObjString);
			 vObjCnt=ObjectCounter(ObjString);
			 gm.Fn_GetLabelValue(vObj, vObjCnt);
			 break;
		case "Fn_GetAttribute":
			ObjString=prop.getProperty(xrs.getCellData(vModuleName, "Object1", m).trim());
			vObj=ExtractObject(ObjString);
			vObjCnt=ObjectCounter(ObjString);
			vVal=xrs.getCellData(vModuleName, "Param1", m);
			vVal2=xrs.getCellData(vModuleName, "Param2", m);
			gm.Fn_GetAttribute(vObj, vObjCnt,vVal,vVal2);
			break;
			
			//default:
			//	System.out.println("Invalid Keyword");
		}
		
	}
	public WebElement ExtractObject(String ObjString) throws IOException
	{
		WebElement vObj = null;
		
	
		try{
		if(ObjString.length()>0)
		{
			String[] extract=ObjString.split("@@@");
			switch (extract[0])
			{
			case "xpath":
				vObj=dscript.driver.findElement(By.xpath(extract[11]));
				break;
			case "id":
				vObj=dscript.driver.findElement(By.id(extract[1]));
				break;
			case "name":
				vObj=dscript.driver.findElement(By.name(extract[1]));
				break;
			case "linkText":
				vObj=dscript.driver.findElement(By.linkText(extract[1]));
				break;
			case "cssSelector":
				vObj=dscript.driver.findElement(By.cssSelector(extract[1]));
				break;
			case "partialLinkText":
				vObj=dscript.driver.findElement(By.partialLinkText(extract[1]));
				break;
			case "classname":
				vObj=dscript.driver.findElement(By.className(extract[1]));;
				break;
			}
			
		}
		return vObj;
		}catch(Exception e){		
			return null;
		}
		
	}
	
//	Take Screenshots of object not found
	public void getscreenshot()
    {
            File scrFile = ((TakesScreenshot)dscript.driver).getScreenshotAs(OutputType.FILE);
         //The below method will save the screen shot in d drive with name "*.jpeg"
            int i=1;
            try {
				FileUtils.copyFile(scrFile, new File("D:\\ObjNext\\Framework Date - 13052016 Updated\\AscentOne Selenium Framework\\src\\Screenshot\\"+vObj+".jpeg"));
				
			} catch (IOException e) {
				
				e.printStackTrace();
				
			}
			
			
    }
	
	
	public int ObjectCounter(String ObjString)
	{
		int vObjCnt = 0;
		try{
		if(ObjString.length()>0)
		{
			String[] extract=ObjString.split("@@@");
			switch (extract[0])
			{
			case "xpath":
				vObjCnt=dscript.driver.findElements(By.xpath(extract[1])).size();
				break;
			case "id":
				vObjCnt=dscript.driver.findElements(By.id(extract[1])).size();
				break;
			case "name":
				vObjCnt=dscript.driver.findElements(By.name(extract[1])).size();
				break;
			case "linkText":
				vObjCnt=dscript.driver.findElements(By.linkText(extract[1])).size();
				break;
			case "cssSelector":
				vObjCnt=dscript.driver.findElements(By.cssSelector(extract[1])).size();
				break;
			case "partialLinkText":
				vObjCnt=dscript.driver.findElements(By.partialLinkText(extract[1])).size();
				break;
			case "classname":
				vObjCnt=dscript.driver.findElements(By.className("android.widget.EditText")).size();
				break;
			}
		}
		return vObjCnt;
		}catch(Exception e){
			return 0;
		}
	}

}
