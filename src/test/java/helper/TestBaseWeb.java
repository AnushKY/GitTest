package helper;

import java.io.File;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import services.WebServices;
import utility.Utility;

public class TestBaseWeb {
	
	public static WebDriver driver;
	public static ExtentReports report;
	public static ExtentTest extentTest;
	protected static File file;
	
	public static void initialize(Scenario scenario){
		String browser = WebHelper.getValue(WebServices.BROWSER);
		file  = new File(System.getProperty("user.dir")+"/TestData/"+WebHelper.getValue(WebServices.ENV)+"TestResult.json");
		report = new ExtentReports(System.getProperty("user.dir")+ "/Report/UI/"+browser+"/"+scenario.getName()+".html",false);
		switch(browser){
		case "chrome":
			extentTest = report.startTest("Scenario:: "+ scenario.getName() + " || Browser:" +browser);
			ChromeOptions chromeOptions  = new ChromeOptions();
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver(chromeOptions);
			
			chromeOptions.addArguments("--disable-gpu");
			chromeOptions.addArguments("--no-sandbox");
			chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
			break;
		case "ie":
			extentTest = report.startTest("Scenario:: "+ scenario.getName() + " || Browser:" +browser);
			InternetExplorerOptions internetexplorerOptions = new InternetExplorerOptions();
			WebDriverManager.iedriver().setup();
			driver = new InternetExplorerDriver(internetexplorerOptions);
			break;
		case "firefox":	
			extentTest = report.startTest("Scenario:: "+ scenario.getName() + " || Browser:" +browser);
			FirefoxOptions firefoxoptions = new FirefoxOptions();
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver(firefoxoptions);
			break;
		case "edge":
			extentTest = report.startTest("Scenario:: "+ scenario.getName() + " || Browser:" +browser);
			EdgeOptions edgeoptions = new EdgeOptions();
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver(edgeoptions);
			break;
		default:
			System.out.println("No Browser configured");
			
		}
		Utility.setCurrentScenarioName(scenario.getName());
	}

}
