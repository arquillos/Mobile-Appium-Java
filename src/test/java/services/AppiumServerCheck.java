/**
* Appium server checker service
*
* @author  Arquillos
*/
package services;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.net.UrlChecker;

public class AppiumServerCheck {
	static final Logger logger = Logger.getLogger(AppiumServerCheck.class);
	// TODO: Read parameter from resources general config file
	private static final long WAIT_FOR_APPIUM_TIMEOUT = 10000;

	/**
	 * Check for Appium server
	 * @return True if the Appium server is running, false otherwise
	 * @throws Exception
	 */
	public boolean waitUntilIsRunning(String serverUrl) throws Exception {
		logger.info("Waiting for Appium Server to be running...");
		final URL status = new URL(serverUrl + "/sessions");
		logger.debug("Appium Server URL: " + serverUrl);

		try {
			new UrlChecker().waitUntilAvailable(WAIT_FOR_APPIUM_TIMEOUT, TimeUnit.MILLISECONDS, status);
			logger.debug("   RUNNING!");
			return true;
		} catch (UrlChecker.TimeoutException e) {
			logger.debug("   NOT running!");
			return false;
		}
	}
}