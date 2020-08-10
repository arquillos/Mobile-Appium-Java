/**
* Photo demo screen page objects
*
* @author  Arquillos
*/
package page.objects;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class PhotoDemoPageObjects {
	@AndroidFindBy(xpath="//android.widget.ImageButton[@index='0']")
	@iOSXCUITFindBy(xpath="//XCUIElementTypeButton[@name=\"The App\"]")
	public MobileElement backButton;

	@AndroidFindBy(xpath="//android.widget.TextView[@text='Photo Library. Tap a photo!']")
	@iOSXCUITFindBy(xpath="//XCUIElementTypeStaticText[@name=\"Photo Library. Tap a photo!\"]")
	public MobileElement descriptionText;
}