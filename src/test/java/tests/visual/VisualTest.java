/**
* Visual screen checks
*
* @author  Arquillos
*/
package tests.visual;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import services.Navigation;
import services.VisualCheck;
import tests.TestBaseClass;

import static org.testng.Assert.assertTrue;


public class VisualTest extends TestBaseClass {
	private VisualCheck vc;
	private Navigation nav;
	private String screenName;

	@DataProvider(name = "screenNames")
	public Object[][] createScreenNames() {
		return new Object[][] {
				{ "main" },
				{ "photoDemo" }
		};
	}

	@BeforeClass
	public void initializePageObjects() {
		logger.debug("Visual checks: Resetting app.");
		driver.resetApp();

		logger.debug("Initializing navigation system.");
		nav = new Navigation(driver);

		logger.debug("Initializing visual checker.");
		vc = new VisualCheck(driver,
				genericConfigMap.get("visualchecks.baseline.path"),
				genericConfigMap.get("visualchecks.baseline.prefix"),
				genericConfigMap.get("visualchecks.match.threshold"),
				genericConfigMap.get("jira.baseurl"),
				genericConfigMap.get("jira.ticket.creation"),
				genericConfigMap.get("jira.ticket.projectID"),
				genericConfigMap.get("jira.ticket.title.prefix"),
				genericConfigMap.get("jira.ticket.description.prefix"),
				genericConfigMap.get("jira.ticket.type"));
	}

	@AfterTest
	public void navigateBack() {
		try {
			nav.returnFrom(this.screenName);
		} catch (Exception e) {
			logger.error(("Cant not return from screen: " + this.screenName));
		}
	}

	/**
	 * Check for visual differences
	 */
	@Test(dataProvider = "screenNames")
	public void visualCheck(String screenName) throws Exception {
		this.screenName = screenName;

		// Given
		logger.info("Visual checking the " + screenName + " screen");
		nav.navigateTo(screenName);

		// When
		boolean visualCheckResult = vc.execute(screenName);

		// Then
		logger.debug("visualCheckResult: " + visualCheckResult);
		assertTrue(visualCheckResult);
	}
}