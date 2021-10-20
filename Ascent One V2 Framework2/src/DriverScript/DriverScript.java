package DriverScript;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import Library.Generic.HtmlResult;
import Library.Generic.KeywordDriver;
import Library.Utility.Xls_Reader;

public class DriverScript {

	public static int vProjectRowCnt=0;
	public static WebDriver driver;
	public static String vProjectUrl,vTCName,vDescription,vResFilePath,vProjectName,vModuleName,vParam1,vTDColumn;
	public static int noofTC, passtc, failtc, passval, failval,failcnt,t;
	
	public static void main(String[] args) throws Throwable {
		int vModuleRowCnt,vTCCnt,vScnCnt;
		String vRun,vProjectModuleFiles,vProjectScenerioFile,vProjectResultFile,vProjectTestDataFile,vDBName,vDBUser,vDbpassword,vBrowser,vModRun,vPriority,vTCRun,vKeywords,vActKeyword;
		System.out.println(System.getProperty("user.dir"));
		
		Xls_Reader xr=new Xls_Reader(System.getProperty("user.dir")+"\\src\\DriverFiles\\ProjectDriver.xlsx");
		vProjectRowCnt=xr.getRowCount("Projects");
		System.out.println("Total Project: "+vProjectRowCnt);
		for(int i=2;i<=vProjectRowCnt;i++)
		{
			 vRun=xr.getCellData("Projects", "Run", i);
			 if(vRun.equalsIgnoreCase("ON"))
			 {
				 vProjectName=xr.getCellData("Projects", "ProjectName", i);
				 vProjectUrl=xr.getCellData("Projects", "ProjectUrl", i);
				 vProjectModuleFiles=xr.getCellData("Projects", "ProjectModuleFiles", i);
				 vProjectScenerioFile=xr.getCellData("Projects", "ProjectScenerioFile", i);
				 vProjectResultFile=xr.getCellData("Projects", "ProjectExcelResultFile", i);
				 vProjectTestDataFile=xr.getCellData("Projects", "ProjectTestDataFile", i);
				 vDBName=xr.getCellData("Projects", "DBName", i);
				 vDBUser=xr.getCellData("Projects", "DBUser", i);
				 vDbpassword=xr.getCellData("Projects", "DBPassword", i);
				 vBrowser=xr.getCellData("Projects", "Browser", i);
				 System.out.println(vProjectName);
				 System.out.println("=====================");
				 
				//HTML Result
					DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					Date date = new Date();
					String startDate=dateFormat.format(date);
					 HtmlResult hr=new HtmlResult();
					 vResFilePath=hr.CreateResultFileAndPath(vProjectName);
					 hr.fg_OpenResultsFile(vResFilePath, vProjectName);
					 noofTC=0;
					 passtc=0;
					 failtc=0;
					 passval=0;
					 failval=0;
				 
				 // Launch Browser
					 switch(vBrowser.trim())
					 {
						 case "Firefox":
							 System.setProperty("webdriver.chrome.driver", "D:\\Brinder\\BrowserDrivers\\geckodriver.exe");
							 driver=new FirefoxDriver();
						 break;
						 case "Chrome":
							 System.setProperty("webdriver.chrome.driver", "D:\\Brinder\\BrowserDrivers\\chromedriver.exe");
							 driver = new ChromeDriver();
						 break;
						 case "IE":
							 System.setProperty("webdriver.ie.driver", "D:\\Brinder\\BrowserDrivers\\IEDriverServer.exe");
							 driver = new InternetExplorerDriver();
						 break;
						 case "Android":
							 File ClassPathRoot = new File(System.getProperty("user.dir"));
					           
				             File appDir = new File(ClassPathRoot, "app");
				           
				             File app = new File(appDir, "selendroid-test-app-0.11.0.apk");
				         
				             DesiredCapabilities capabilities = new DesiredCapabilities();
				             capabilities.setCapability("device","Android");
				             capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
				             capabilities.setCapability(CapabilityType.VERSION, "4.2");
				             capabilities.setCapability(CapabilityType.PLATFORM, "WINDOW");
				          
				             capabilities.setCapability("platformName", "Android");
				             capabilities.setCapability("app", app.getAbsolutePath());
				             System.out.println(app.getAbsolutePath());
//				             capabilities.setCapability("app", "E:\\Selenium\\androidworkspace\\Appium\\app\\selendroid-test-app-0.11.0.apk");
				             capabilities.setCapability("platformVersion", "4.3");        
				             capabilities.setCapability("deviceName","Android Emulator");         //Emulator-5554
				            // capabilities.setCapability("appPackage", "io.selendroid.test.app"); // This is package name of your app (you can get it from apk info app)
				             capabilities.setCapability("appPackage", "io.selendroid.testapp:0.14.0");
				             capabilities.setCapability("appActivity", ".HomeScreenActivity");
				             
				              driver = new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
						 break;
						 default:
							 System.out.println("Browser driver not available");
						 break;
						 
					 }
				 Xls_Reader xrm=new Xls_Reader(System.getProperty("user.dir")+"\\src\\DriverFiles\\"+vProjectModuleFiles);
				 Xls_Reader xrs=new Xls_Reader(System.getProperty("user.dir")+"\\src\\Scenerio\\"+vProjectScenerioFile);
				 Xls_Reader xres=new Xls_Reader(System.getProperty("user.dir")+"\\src\\Result\\"+vProjectResultFile);
Xls_Reader xrd=new Xls_Reader(System.getProperty("user.dir")+"\\src\\TestData\\"+vProjectTestDataFile);
				 // Switch to project sheet
				 vModuleRowCnt=xr.getRowCount(vProjectName);
				 for(int j=2;j<=vModuleRowCnt;j++)
				 {
					 vModRun=xr.getCellData(vProjectName, "Run", j);
					 if(vModRun.equalsIgnoreCase("ON"))
					 {
						 vModuleName=xr.getCellData(vProjectName, "ModuleName", j);
						 vPriority=xr.getCellData(vProjectName, "Priority", j);
						 System.out.println(vModuleName);

						 

int vTDRowCnt=xrd.getRowCount(vProjectName);
for( t=1;t<=vTDRowCnt;t++)
{
String vTDRun=xrd.getCellData(vProjectName, "Run", t);
if(vTDRun.equalsIgnoreCase("ON"))
{

						 
						 
						 //Read Test Cases
						 vTCCnt=xrm.getRowCount(vModuleName);
						 vScnCnt=xrs.getRowCount(vModuleName);
						 
						 for(int k=2;k<=vTCCnt;k++)
						 {
							 int flag=0;
							 int TCRowNum=0;
							 failcnt=0;
							 							 
							 vTCRun=xrm.getCellData(vModuleName, "Run", k);
							 if(vTCRun.equalsIgnoreCase("ON"))
							 {
								 vTCName=xrm.getCellData(vModuleName, "TCName", k);
								 vPriority=xrm.getCellData(vModuleName, "Priority", k);
								 noofTC=noofTC+1;
								 System.out.println(vTCName);
								 for (int m=2;m<=vScnCnt;m++)
								 {
									 vKeywords=xrs.getCellData(vModuleName, "Keywords", m);

									 
									 
									 
 String vParam2=xrs.getCellData(vModuleName, "Param2", m);
if(vParam2.equals("Testdata"))
{
  vParam1=xrs.getCellData(vModuleName, "Param1", m);
  vTDColumn=xrd.getCellData(vProjectName, vParam1, t);
}																 
									 
									 
									 

									 
									 if(vKeywords.trim().equals(vTCName.trim()))
									 {
										 flag=1;
										 vDescription=xrs.getCellData(vModuleName, "Keywords", m-1);
										 TCRowNum=m;
										 hr.fgInsertStep(vResFilePath);
									 }
									 if((m>TCRowNum) && (flag==1))
									 {
										 if(vKeywords.contains("//"))
										 {
											 break;
										 }
										 else
										 {
											 vActKeyword=vKeywords.trim();
											 System.out.println(vActKeyword);
											 KeywordDriver kd=new KeywordDriver(vActKeyword, xrs, m, vModuleName);
									 
										 }
									 }
								 }
								 if(failcnt>0)
								 {
									 failcnt=failcnt+1;
									 hr.ExcelResult(xres, "FAIL");
								 }
								 else
								 {
									 hr.ExcelResult(xres, "PASS");
								 }
							 }
}
}
						 }
					 }
				 }
				 Date date1=new Date();
				 String endDate=dateFormat.format(date1);
				 long timeofexe=hr.timeDiffernce(startDate, endDate, dateFormat);
				 hr.fgCreateSummary(vResFilePath);
				 hr.fgInsertSummary(vResFilePath, noofTC, noofTC-passtc, failtc, passval, failval, timeofexe);
				 hr.fgCloseFile(vResFilePath);
				 
			 }
	}
	}
}
