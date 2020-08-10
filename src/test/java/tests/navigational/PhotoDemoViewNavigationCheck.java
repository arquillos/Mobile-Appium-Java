/**
* Photo demo screen navigation checks
*
* @author  Arquillos
*/
package tests.navigational;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.MainPage;
import pages.PhotoDemoPage;
import tests.TestBaseClass;

import static org.testng.Assert.assertEquals;


public class PhotoDemoViewNavigationCheck extends TestBaseClass {
	MainPage mainPage;
	PhotoDemoPage photoDemoPage;


	@BeforeClass
	public void initializePageObjects() {
		mainPage = new MainPage(driver, logger);
		mainPage.waitForTitle();

		photoDemoPage = new PhotoDemoPage(driver, logger);
	}

	/**
	 *  Navigate from main page to Photo Demo view
	 * @throws Exception
	 */
	@Test
	public void photoDemoUpNavigationCheck() throws Exception {
		String expectedText = "Photo Library. Tap a photo!";
		logger.info("Check main page to photo demo navigation");

		// When
		mainPage.clickPhoto();

		// Then check screen description text
		String actualText = photoDemoPage.getDescriptionText();
		assertEquals(expectedText, actualText);

		// Then get back to main screen
		photoDemoPage.clickBackButton();
		mainPage.waitForTitle();
	}
}