package stepdefination.common;

import java.io.FileNotFoundException;

import helper.TestBaseWeb;
import io.cucumber.java.en.Given;
import utility.Utility;

public class WebCommon extends TestBaseWeb{

	String testUrl;
	
	@Given("I want to launch application for {string} in {string}")
	public void iNavigateToApplicationFor(String component, String locale) throws FileNotFoundException {
		testUrl = Utility.getUrlAndCheckHealth(component, locale, extentTest);
		System.out.println(testUrl);
		Utility.navigateToUrl(driver,testUrl,extentTest);
		
	}
	
}
