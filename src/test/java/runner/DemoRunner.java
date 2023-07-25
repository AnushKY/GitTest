package runner;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import helper.TestBaseWeb;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import utility.AzureTestPlan;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = {"src/test/resources/web/component"},
		glue = {"stepdefination","hooks"},
		tags = "@ipl",
		plugin = {"pretty",
				"html:target/cucumber-html-report.html",
				"json:target/cucumber-reports/cucmber.json",
				"junit:target/cucumner-reports/cucumber.xml",
				"rerun:target/rerun.txt"
		}
)
public class DemoRunner extends TestBaseWeb {

/*	@BeforeClass
	public static void generateRunID(){
		AzureTestPlan.createPlanId();
	}
	
	@AfterClass
	public static void updateResult(){
		AzureTestPlan.updateAzureTestStatus(file);
	}
*/
	
	
}
