/**
* Main screen page methods
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
import page.objects.MainPageObjects;


public class MainPage {
	private AppiumDriver<MobileElement> driver;
	private Logger logger;
	private MainPageObjects mObjects = new MainPageObjects();


	public MainPage(AppiumDriver<MobileElement> driver, Logger logger) {
		this.logger = logger;

		this.logger.debug("Initializing Main Page Factory");
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), mObjects);
	}

	public String getTitleText() {
		logger.debug("Trying to get the title text");
		String text = mObjects.appTitle.getText();
		logger.debug("Main title text: " + text);
		return text;
	}

	public String getPhotoDemoText() {
		logger.debug("Trying to get photo demo text");
		String text = mObjects.photoDemo.getText();
		logger.debug("Main title text: " + text);
		return text;
	}

	public void clickPhoto() {
		logger.debug("Trying to click the photo demo");
		mObjects.photoDemo.click();
	}

	public void clickEchoBox() {
		logger.debug("Trying to click the echo box");
		mObjects.echoBox.click();
	}

	public void waitForTitle() {
		WebDriverWait wait = new WebDriverWait(driver, 10);

		logger.debug("Waiting for App title presence");
		Capabilities caps = driver.getCapabilities();
		String platform = caps.getPlatform().toString();
		logger.debug("Running checks on platform: " + platform);
		try {
			if (platform.equals("MAC")) {
				logger.debug("Waiting for iOS app title...");
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//XCUIElementTypeStaticText[@name=\"The App\"]")));
			} else {
				logger.debug("Waiting for Android app title...");
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//android.view.ViewGroup[1]/android.widget.TextView[@index='0']")));
			}
		}
		catch (org.openqa.selenium.TimeoutException e) {
			logger.error("Title not found!");
		}
	}

	public boolean isDisplayed() {
		waitForTitle();
		return mObjects.appTitle.isDisplayed();
	}
}