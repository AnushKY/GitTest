package stepdefination.component;

import com.relevantcodes.extentreports.LogStatus;

import helper.TestBaseWeb;
import io.cucumber.java.en.Then;

public class HomePage extends TestBaseWeb {

	@Then("I verify the page title as {string}")
	public void verifyPageTitle(String expectedTitle){
		String actualTitle = driver.getTitle();
		if(expectedTitle.equalsIgnoreCase(actualTitle)){
			extentTest.log(LogStatus.PASS, "Title is Matching");	
		}else{
			extentTest.log(LogStatus.FAIL, "Title is not Matching");
		}
		
	}
	
	
}
