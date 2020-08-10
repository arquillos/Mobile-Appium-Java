/**
* Echo Box screen functional checks
*
* @author  Arquillos
*/
package tests.functional;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.EchoBoxPage;
import pages.MainPage;
import tests.TestBaseClass;

import static org.testng.Assert.assertEquals;

public class EchoBoxCheck extends TestBaseClass {
	private MainPage mainPage;
	private EchoBoxPage echoBoxPage;


	@BeforeClass
	public void initializePageObjects() throws Exception {
		logger.debug("Resetting app.");
		driver.resetApp();

		// Given
		mainPage = new MainPage(driver, logger);
		mainPage.waitForTitle();

		// Navigate to Echo Box
		mainPage.clickEchoBox();

		echoBoxPage = new EchoBoxPage(driver, logger);
	}

	/**
	 * Set a text and check it is echoed
	 */
	@Test
	public void setTextCheck() {
		String expectedText = "Hello functional check!";
		// When
		echoBoxPage.setText(expectedText);

		// Then
		String actualText = echoBoxPage.getEchoedText();
		assertEquals(actualText, expectedText);
	}
}