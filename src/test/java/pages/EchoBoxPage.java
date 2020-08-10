/**
* Echo box screen page methods
*
* @author  Arquillos
*/
package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import page.objects.EchoBoxPageObjects;


public class EchoBoxPage {
	private AppiumDriver<MobileElement> driver;
	private Logger logger;
	private EchoBoxPageObjects eObjects = new EchoBoxPageObjects();


	public EchoBoxPage(AppiumDriver<MobileElement> driver, Logger logger) {
		this.logger = logger;

		this.logger.debug("Initializing Echo box Factory");
		this.driver = driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), eObjects);
	}

	public void clickBackButton() {
		logger.debug("Trying to click the back button");
		eObjects.backButton.click();
	}

	public void setText(String text) {
		logger.debug("Typing text: " + text);
		eObjects.echoInputBox.clear();
		eObjects.echoInputBox.setValue(text);
		eObjects.saveButton.click();
	}

	public String getEchoedText() {
		String echoedText = eObjects.echoedText.getText();
		logger.debug("Echoed text: " + echoedText);
		return echoedText;
	}
}