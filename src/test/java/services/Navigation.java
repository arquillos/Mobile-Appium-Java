/**
* Navigation helper service
*
* @author  Arquillos
*/
package services;

import org.apache.log4j.Logger;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import pages.*;


public class Navigation {
	static final Logger logger = Logger.getLogger(Navigation.class);
	private AppiumDriver<MobileElement> driver;
	private final MainPage mp;
	private final PhotoDemoPage pdp;


	public Navigation(AppiumDriver<MobileElement> driver) {
		logger.debug("Initializing Navigation class");
		this.driver = driver;

		mp = new MainPage(this.driver, logger);
		mp.waitForTitle();
		pdp = new PhotoDemoPage(this.driver, logger);
	}

	public void navigateTo(String screenName) throws Exception {
		switch (screenName) {
			case "main":
				break;
			case "photoDemo":
				mainToPhotoDemo();
				break;
			default:
				String errorMessage = "The screen name: " + screenName + " is NOT expected!";
				logger.error(errorMessage);
				throw new Exception(errorMessage);
		}
	}

	public void returnFrom(String screenName) throws Exception {
		switch (screenName) {
			case "main":
				break;
			case "photoDemo":
				photoDemoToMain();
				break;
			default:
				String errorMessage = "The screen name: " + screenName + " is NOT expected!";
				logger.error(errorMessage);
				throw new Exception(errorMessage);
		}
	}

	public void mainToPhotoDemo() throws Exception {
		logger.info("Navigating from main to photo demo screen");

		if (inMainScreen()) {
			mp.clickPhoto();
			mp.waitForTitle();

			if (inPhotoScreen()) {
				logger.debug("   Done navigating!...");
			} else {
				throw new Exception("Navigation exception! - Not in Photo demo screen");
			}
		} else {
			throw new Exception("Navigation exception! - Not in main screen");
		}
	}

	public void photoDemoToMain() throws Exception {
		logger.info("Navigating from photo demo to main screen");

		if (inPhotoScreen()) {
			pdp.clickBackButton();
			mp.waitForTitle();

			if (inMainScreen()) {
				logger.debug("   Done navigating!...");
			} else {
				throw new Exception("Navigation exception! - Not in main screen");
			}
		} else {
			throw new Exception("Navigation exception! - Not in Photo demo screen");
		}
	}

	public boolean inMainScreen() {
		logger.debug("Checking if we are in Main screen");
		return mp.isDisplayed();
	}

	public boolean inPhotoScreen() {
		logger.debug("Checking if we are in Photo demo screen");
		return pdp.isDisplayed();
	}
}