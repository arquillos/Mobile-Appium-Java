/**
* Test base class
*
* @author  Arquillos
*/
package tests;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import io.appium.java_client.remote.AndroidMobileCapabilityType;
import org.apache.log4j.Logger;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.remote.MobileCapabilityType;
import services.AppiumServerCheck;
import services.Navigation;
import services.PropertiesReader;


public class TestBaseClass {
	protected static final Logger logger = Logger.getLogger(TestBaseClass.class);
	static final PropertiesReader propReader = new PropertiesReader();
	protected Navigation nav;
	protected static AppiumDriver<MobileElement> driver;
	protected static HashMap<String,String> genericConfigMap = new HashMap<String, String>();

	@BeforeSuite
	public void setupSuite() throws Exception {
		URL appiumServerUrl = null;

		loadPropertyFiles();

		String selectedPlatform = genericConfigMap.get("appium.platform.name");
		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
		setDesiredCapabilities(selectedPlatform, desiredCapabilities);

		try {
			appiumServerUrl = new URL(genericConfigMap.get("appium.server.url"));
		} catch(MalformedURLException e) {
			logger.error("Exception setting Appium URL: " + e.getMessage());
			logger.error(e.getCause());
			e.printStackTrace();
		}

		AppiumServerCheck appiumServerChecker = new AppiumServerCheck();
		if (!appiumServerChecker.waitUntilIsRunning(appiumServerUrl.toString())) {
			logger.error("Aborting tests! Please run first the Appium server.");
			System.exit(1);
		}

		driver = new AppiumDriver<MobileElement>(appiumServerUrl, desiredCapabilities);
	}

	@AfterSuite
	public void teardownSuite() {
		logger.debug("Closing driver.");
		if (driver != null) {
			driver.quit();
		}
	}

	/**
	 * Load the properties from resource files
	 */
	private void loadPropertyFiles() {
		logger.debug("Loading generic properties...");
		propReader.readPropertiesFile("generic.properties", genericConfigMap);
	}

	/**
	 * Set Appium driver desired capabilities
	 * 
	 * @param desiredCapabilities
	 */
	private void setDesiredCapabilities(String selectedPlatform, DesiredCapabilities desiredCapabilities) {
		logger.debug("Setting Appium driver capabilities");
		
		switch (selectedPlatform) {
		case "Android":
			setAndroidDesiredCapabilities(desiredCapabilities);
			break;
		case "iOS":
			setIOSDesiredCapabilities(desiredCapabilities);
			break;
		default:
			logger.error("This platform is not expected!!!! (" + selectedPlatform + ")");
			break;
		}
	}

	private void setAndroidDesiredCapabilities(DesiredCapabilities desiredCapabilities) {
		logger.debug("   Android capabilities");

		desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, genericConfigMap.get("appium.automation.name"));
		desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, genericConfigMap.get("appium.platform.name"));
		desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "");
		// IGNORED for ANDROID but necessary
		desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, genericConfigMap.get("Android"));

		// Example app
		desiredCapabilities.setCapability(MobileCapabilityType.APP, genericConfigMap.get("appium.and.app.apk"));
		desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, genericConfigMap.get("appium.and.app.package"));
		desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, genericConfigMap.get("appium.and.app.activity"));
	}

	private void setIOSDesiredCapabilities(DesiredCapabilities desiredCapabilities) {
		logger.debug("   iOS capabilities");

		desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
		desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "13.6");
		desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone 8 Plus");

		// Example app
		desiredCapabilities.setCapability(MobileCapabilityType.APP, genericConfigMap.get("appium.ios.app.app"));
	}
}