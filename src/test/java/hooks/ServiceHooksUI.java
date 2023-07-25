package hooks;

import helper.TestBaseWeb;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utility.Utility;

import java.io.IOException;

public class ServiceHooksUI extends TestBaseWeb {
	public ServiceHooksUI() throws IOException {
	}

	@Before("@WebReport")
	public void setUp(Scenario scenario) throws InterruptedException {
		TestBaseWeb.initialize(scenario);
	}

	@After("@WebReport")
	public void tearUp(Scenario scenario) throws IOException {
		//Utility.updateTestResult(scenario, file);
		report.endTest(extentTest);
		report.flush();
		driver.quit();
	}
}
