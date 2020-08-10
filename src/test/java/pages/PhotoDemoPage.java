/**
* Photo demo screen page methods
*
* @author  Arquillos
*/
package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import page.objects.PhotoDemoPageObjects;


public class PhotoDemoPage {
	private AppiumDriver<MobileElement> driver;
	private Logger logger;
	private PhotoDemoPageObjects pObjects = new PhotoDemoPageObjects();


	public PhotoDemoPage(AppiumDriver<MobileElement> driver, Logger logger) {
		this.logger = logger;

		this.logger.debug("Initializing Photo demo Factory");
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), pObjects);
	}

	public String getDescriptionText() {
		logger.debug("Trying to get the description text");
		String text = pObjects.descriptionText.getText();
		logger.debug("Main title text: " + text);
		return text;
	}

	public void clickBackButton() {
		logger.debug("Trying to click the back button");
		pObjects.backButton.click();
	}

	public void waitForDescription() {
		// TODO
		WebDriverWait wait = new WebDriverWait(driver, 10);

		logger.debug("Waiting for App title presence");
		Capabilities caps = driver.getCapabilities();
		String platform = caps.getPlatform().toString();
		logger.debug("Running checks on platform: " + platform);
		try {
			if (platform.equals("MAC")) {
				logger.debug("Waiting for iOS app title...");
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//XCUIElementTypeStaticText[@name=\"Photo Library. Tap a photo!\"]")));
			} else {
				logger.debug("Waiting for Android app title...");
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//android.widget.TextView[@text='Photo Library. Tap a photo!']")));
			}
		}
		catch (org.openqa.selenium.TimeoutException e) {
			logger.error("Title not found!");
		}
	}

	public boolean isDisplayed() {
		waitForDescription();
		return pObjects.descriptionText.isDisplayed();
	}
}