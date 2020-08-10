/**
* Main screen content checks
*
* @author  Arquillos
*/
package tests.content;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.MainPage;
import tests.TestBaseClass;

import static org.testng.Assert.assertEquals;


public class MainScreenChecks extends TestBaseClass {
	MainPage mainPage;

	@BeforeClass
	public void initializePageObjects() {
		// Given
		mainPage = new MainPage(driver, logger);
		mainPage.waitForTitle();
	}

	/**
	 *  Checking the title text
	 */
	@Test
	public void titleCheck()  {
		String expectedTitle = "The App";
		logger.debug("Getting main screen title.");

		// When
		String text = mainPage.getTitleText();
		logger.debug("   - text: " + text);

		// Then
		assertEquals(text, expectedTitle);
	}

	/**
	 *  Checking the photo demo text
	 */
	@Test
	public void photoDemoViewNameCheck()  {
		String expectedTitle = "Photo Demo";
		logger.debug("Getting \"Photo Demo\" text.");

		// When
		String text = mainPage.getPhotoDemoText();
		logger.debug("   - text: " + text);

		// Then
		assertEquals(text, expectedTitle);
	}
}