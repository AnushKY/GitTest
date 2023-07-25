package utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.AssumptionViolatedException;
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

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import helper.WebHelper;
import services.WebServices;

public class Utility {

	private static String currentScenarioName = "";
	public static ArrayList<String> urlStatus = new ArrayList<String>(); 

	public static String getUrlAndCheckHealth(String component, String locale, ExtentTest extentTest) {
		UrlVerifier urlVerifier = new UrlVerifier(component, locale);
		if (urlVerifier.isBroken()) {
			System.out.println("Skipping the test. Reason: " + urlVerifier.getMessage());
			extentTest.log(LogStatus.SKIP, "Skipping the test. Reason: " + urlVerifier.getMessage());
			throw new AssumptionViolatedException("Skipping the test. Reason: " + urlVerifier.getMessage());
		} else if (urlVerifier.isAlert()) {
			System.out.println("Issue encountered while url health check: " + urlVerifier.getMessage() + "\r\nContinue...");
			extentTest.log(LogStatus.WARNING, "Alert: " + urlVerifier.getMessage());
		} else {
			System.out.println("Found healthy URL: " + urlVerifier.getURL());
		}
		return urlVerifier.getURL();
	}

	public static String getTestUrl(String component, String locale) throws FileNotFoundException {
		JSONParser parser = new JSONParser();
		String env = WebHelper.getValue(WebServices.ENV).toString();
		String testUrl = null;
		String path = "";
		System.out.println("getTestURL");
		if (WebHelper.getValue(WebServices.SPRINT).equalsIgnoreCase("yes")) {
			path = "/src/main/resources/testData/sprint/" + env + "/" + WebHelper.getValue(WebServices.TEAM).toLowerCase() + "TestData.json";

		} else {
			path = "/src/main/resources/testData/regression/" + env + "/TestData.json";
			//path = "/src/main/resources/testData/" + env + "/TestData.json";
		}
		try {
			Object obj = parser.parse(new FileReader(
					System.getProperty("user.dir") + path));
			JSONObject jsonObject = (JSONObject) obj;
			JSONArray componentUrl = (JSONArray) jsonObject.get(component);
			for (int i = 0; i < componentUrl.size(); i++) {
				JSONObject json1 = (JSONObject) componentUrl.get(i);
				String loc = json1.get("locale").toString();
				if (loc.equalsIgnoreCase(locale)) {
					JSONArray urls = (JSONArray) json1.get("url");
					JSONObject json2 = (JSONObject) urls.get(0);
					testUrl = json2.get("1").toString();
					System.out.println("Test Url: " + testUrl);}
			}} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		if(testUrl.contains("bcdzuoraqa.azurewebsites.net")){
			return testUrl;
		}else{
			//return testUrl + "?nocache=" + randomStringGenerator(7) ;
			return testUrl;
		}
	}

	public static String randomStringGenerator(int no){
		String randomno = RandomStringUtils.randomNumeric(no);
		return randomno;
	}

	public static void addUrlStatus(String _message){
		urlStatus.add(currentScenarioName +"@"+_message);
	}
	public static void setCurrentScenarioName(String _currentScenarioName){
		currentScenarioName = _currentScenarioName;
	}

	public static void navigateToUrl(WebDriver driver, String url) {
		driver.get(url);
		//driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
	}

	public static void navigateToUrl(WebDriver driver, String url, ExtentTest extentTest){
		System.out.println(url);
		driver.get(url);
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		//handleSignupPopup(driver);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		extentTest.log(LogStatus.INFO, "I navigate to " + url);
	}	
	
	public static int convertStringToInt(String data){
		int intData = Integer.parseInt(data);
		return intData;
	}
	
	public static String convertIntToString(int intData){
		String data = Integer.toString(intData);
		return data;
	}
	
	public static ArrayList<String> getUrlStatusList(){
		return urlStatus; 
	} 
	
	public static String captureScreenshot(WebDriver driver, String screenshotName) {
        try {
            TakesScreenshot ts = ((TakesScreenshot) driver);
            File source = ts.getScreenshotAs(OutputType.FILE);
            String screenshotPath = System.getProperty("user.dir") + "/ScreenShots/" + screenshotName + ".jpg";
            FileUtils.copyFile(source, new File(screenshotPath));
            return screenshotPath;
        } catch (Exception e) {
            System.out.println("Exception while taking screenshots " + e.getMessage());
            return e.getMessage();
        }
    }

 

    public static String captureScreen(WebDriver driver, String imageName) {
        File sourceFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String encodedBase64 = null;
        FileInputStream fileInputStreamReader = null;
        try {
            fileInputStreamReader = new FileInputStream(sourceFile);
            byte[] bytes = new byte[(int) sourceFile.length()];
            fileInputStreamReader.read(bytes);
            encodedBase64 = new String(Base64.encodeBase64(bytes));
            String screenShotDestination = System.getProperty("user.dir") + "/ScreenShots/" + imageName + ".png";
            File destination = new File(screenShotDestination);
            FileUtils.copyFile(sourceFile, destination);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "data:image/png;base64," + encodedBase64;
    }

 

    public static void highlightElement(WebDriver dr, WebElement elm) {
        JavascriptExecutor js = (JavascriptExecutor) dr;
        js.executeScript("arguments[0].setAttribute('style','border: 1.5px solid red;');", elm);
    }

 

    public static void scrollDown(WebDriver dr, WebElement elm) {
        JavascriptExecutor js = (JavascriptExecutor) dr;
        js.executeScript("arguments[0].scrollIntoView();", elm);
    }
    
    public static void enterValue(WebDriver dr, WebElement elm, String value, ExtentTest extentTest, String message) {
        try {
            new WebDriverWait(dr, 15).until(ExpectedConditions.visibilityOf(elm));
            Utility.highlightElement(dr, elm);
            if (elm.isDisplayed()) {
                elm.clear();
                elm.sendKeys(value);
                extentTest.log(LogStatus.PASS, message);
            } 
                else {
                extentTest.log(LogStatus.FAIL, message + extentTest.addBase64ScreenShot(captureScreen(dr, message)));
            }
        } catch (Exception e) {    
            extentTest.log(LogStatus.FAIL,
                    message + extentTest.addBase64ScreenShot(captureScreen(dr, message)) + e.getMessage());
        }
    }

    public static void clearValue(WebDriver dr, WebElement elm, ExtentTest extentTest, String message) {
        try {
            new WebDriverWait(dr, 15).until(ExpectedConditions.visibilityOf(elm));
            //Utility.highlightElement(dr, elm);
            if (elm.isDisplayed()) {
                Actions act = new Actions(dr);
                act.click(elm)
                        .keyDown(Keys.CONTROL)
                        .sendKeys("a")
                        .keyUp(Keys.CONTROL)
                        .sendKeys(Keys.BACK_SPACE)
                        .build()
                        .perform();
                extentTest.log(LogStatus.PASS, message);
            } 
                else {
                extentTest.log(LogStatus.FAIL, message + extentTest.addBase64ScreenShot(captureScreen(dr, message)));
            }
        } catch (Exception e) {    
            extentTest.log(LogStatus.FAIL,
                    message + extentTest.addBase64ScreenShot(captureScreen(dr, message)) + e.getMessage());
        }
    }

    public static void click(WebDriver dr, WebElement elm, ExtentTest extentTest, String message) {
        try {
            //new WebDriverWait(dr, 45).until(ExpectedConditions.elementToBeClickable(elm));
            highlightElement(dr, elm);
            if (new WebDriverWait(dr, 45).until(ExpectedConditions.elementToBeClickable(elm))!=null) {

                  elm.click();
                  extentTest.log(LogStatus.PASS, message);

            } else {
                extentTest.log(LogStatus.FAIL,
                        message + extentTest.addBase64ScreenShot(captureScreen(dr, message)));
            }
        }catch (Exception e) {
            extentTest.log(LogStatus.FAIL,
                    message + extentTest.addBase64ScreenShot(captureScreen(dr, message)) + e.getMessage());
            throw e;
        }

    }
    
    public static void visible(WebDriver dr, WebElement elm, ExtentTest extentTest, String message) {
        try {
            new WebDriverWait(dr, 15).until(ExpectedConditions.visibilityOf(elm));
            highlightElement(dr, elm);
            if (elm.isDisplayed()) {
                extentTest.log(LogStatus.PASS, message);
            } else {
                extentTest.log(LogStatus.FAIL, message + extentTest.addBase64ScreenShot(captureScreen(dr, message)));
            }
        } catch (Exception e) {
            e.printStackTrace();
            extentTest.log(LogStatus.FAIL,
                    message + extentTest.addBase64ScreenShot(captureScreen(dr, message)) + e.getMessage());
            throw e;
        }
    }

 

    public static String getText(WebDriver dr, WebElement elm, ExtentTest extentTest, String message) {
        String innerText = null;
        try {
            new WebDriverWait(dr, 15).until(ExpectedConditions.visibilityOf(elm));
            Utility.highlightElement(dr, elm);
            if (elm.isDisplayed()) {
                innerText = elm.getText().replaceAll("\\n", " ").toString();
                extentTest.log(LogStatus.PASS, message);
            } else {
                extentTest.log(LogStatus.FAIL, message + extentTest.addBase64ScreenShot(captureScreen(dr, message)));
            }
        } catch (Exception e) {
            extentTest.log(LogStatus.FAIL,
                    message + extentTest.addBase64ScreenShot(captureScreen(dr, message)) + e.getMessage());
            innerText = "";
        }
        return innerText;
    }
    
    public static String randomNumberGenerator(int no) {
        String randomno = RandomStringUtils.randomNumeric(no);
        return randomno;
    }

 

    public static void selectDropdown(WebDriver dr, WebElement elm, ExtentTest extentTest, String message, int value) {
        try {
            new WebDriverWait(dr, 15).until(ExpectedConditions.visibilityOf(elm));
            highlightElement(dr, elm);
            if (elm.isDisplayed()) {

 

                Select drpdown = new Select(elm);
                drpdown.selectByIndex(value);
                extentTest.log(LogStatus.PASS, message);
            } else {
                extentTest.log(LogStatus.FAIL, message + extentTest.addBase64ScreenShot(captureScreen(dr, message)));
            }
        } catch (Exception e) {
            extentTest.log(LogStatus.FAIL,
                    message + extentTest.addBase64ScreenShot(captureScreen(dr, message)) + e.getMessage());
        }
    }

 

    public void explicitWait(WebDriver dr, WebElement elm, ExtentTest extentTest, String message) {
        if (elm != null) {
            try {
                WebDriverWait wait = new WebDriverWait(dr, 30);
                wait.until(ExpectedConditions.visibilityOf(elm));
                Utility.highlightElement(dr, elm);
            } catch (Exception e) {

 

            }
        }
    }
    
    public static void mouseHover(WebDriver dr, WebElement elm, ExtentTest extentTest, String message) {
        try {
            new WebDriverWait(dr, 15).until(ExpectedConditions.visibilityOf(elm));
            highlightElement(dr, elm);
            if (elm.isDisplayed()) {
                Actions action = new Actions(dr);
                action.moveToElement(elm).perform();
                extentTest.log(LogStatus.PASS, message);
            } else {
                extentTest.log(LogStatus.FAIL, message + extentTest.addBase64ScreenShot(captureScreen(dr, message)));
            }
        } catch (Exception e) {
            extentTest.log(LogStatus.FAIL,
                    message + extentTest.addBase64ScreenShot(captureScreen(dr, message)) + e.getMessage());
        }
    }

 

    public static void mouseHover(WebDriver dr, WebElement elm) {
        Actions action = new Actions(dr);
        action.moveToElement(elm).clickAndHold(elm).build().perform();
    }

    public static void mouseHoverJS(WebDriver driver, WebElement elm, ExtentTest extentTest, String message) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", elm);
        String strJavaScript = "var element = arguments[0];"
                + "var mouseEventObj = document.createEvent('MouseEvents');"
                + "mouseEventObj.initEvent( 'mouseover', true, true );"
                + "element.dispatchEvent(mouseEventObj);";
        ((JavascriptExecutor) driver).executeScript(strJavaScript, elm);
        extentTest.log(LogStatus.PASS, message);
    }

    public static void selectDropdownByValue(WebDriver dr, WebElement elm, ExtentTest extentTest, String message,
    		String value) {
    	try {
    		new WebDriverWait(dr, 15).until(ExpectedConditions.visibilityOf(elm));
    		highlightElement(dr, elm);
    		if (elm.isDisplayed()) {
    			Select drpdown = new Select(elm);
    			drpdown.selectByValue(value);
    			extentTest.log(LogStatus.PASS, message);
    		} else {
    			extentTest.log(LogStatus.FAIL, message + extentTest.addBase64ScreenShot(captureScreen(dr, message)));
    		}
    	} catch (Exception e) {
    		extentTest.log(LogStatus.FAIL,
    				message + extentTest.addBase64ScreenShot(captureScreen(dr, message)) + e.getMessage());
    	}
    }



    public static void selectFromDropdownByIndex(WebDriver dr, WebElement elm, ExtentTest extentTest, String message,
    		int index) {
    	try {
    		new WebDriverWait(dr, 15).until(ExpectedConditions.visibilityOf(elm));
    		highlightElement(dr, elm);
    		if (elm.isDisplayed()) {
    			Select drpdown = new Select(elm);
    			drpdown.selectByIndex(index);
    			extentTest.log(LogStatus.PASS, message);
    		} else {
    			extentTest.log(LogStatus.FAIL, message + extentTest.addBase64ScreenShot(captureScreen(dr, message)));
    		}
    	} catch (Exception e) {
    		extentTest.log(LogStatus.FAIL,
    				message + extentTest.addBase64ScreenShot(captureScreen(dr, message)) + e.getMessage());
    	}
    }
    
    public static String getText(WebDriver dr, WebElement elm) {
        String innerText = null;
        try {
            new WebDriverWait(dr, 15).until(ExpectedConditions.visibilityOf(elm));
            Utility.highlightElement(dr, elm);
            if (elm.isDisplayed()) {
                innerText = elm.getText().replaceAll("\\n", " ").toString();
            } else {
                System.out.println("FAIL");
            }
        } catch (Exception e) {
            System.out.println("FAIL");
        }
        return innerText;
    }
    
    public static void verifyValue(WebDriver dr, WebElement elm, String expectedResult, ExtentTest extentTest,
    		String message) {
    	try {
    		if (getText(dr, elm).contains(expectedResult)) {
    			extentTest.log(LogStatus.PASS, message);
    		} else {
    			extentTest.log(LogStatus.FAIL, message + extentTest.addBase64ScreenShot(captureScreen(dr, message))
    			+ "  || " + elm.getText() + " || " + expectedResult);
    		}
    	} catch (Exception e) {
    		extentTest.log(LogStatus.FAIL, message + extentTest.addBase64ScreenShot(captureScreen(dr, message))
    		+ "  || " + elm.getText() + " || " + expectedResult);
    	}
    }
}
