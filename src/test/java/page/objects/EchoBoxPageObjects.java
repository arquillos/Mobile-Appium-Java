/**
* Echo box screen page objects
*
* @author  Arquillos
*/
package page.objects;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class EchoBoxPageObjects {
	@AndroidFindBy(xpath="//android.widget.ImageButton[@index='0']")
	@iOSXCUITFindBy(xpath="//XCUIElementTypeButton[@name=\"The App\"]")
	public MobileElement backButton;

	@AndroidFindBy(xpath="//android.widget.EditText[@content-desc=\"messageInput\"]")
	@iOSXCUITFindBy(xpath="//XCUIElementTypeTextField[@name=\"messageInput\"]")
	public MobileElement echoInputBox;

	@AndroidFindBy(xpath="//android.view.ViewGroup[@content-desc=\"messageSaveBtn\"]")
	@iOSXCUITFindBy(xpath="(//XCUIElementTypeOther[@name=\"messageSaveBtn\"])[2]")
	public MobileElement saveButton;

	@AndroidFindBy(xpath="//android.widget.TextView[@index='1']")
	@iOSXCUITFindBy(xpath="//XCUIElementTypeStaticText[@name=\"savedMessage\"]")
	public MobileElement echoedText;
}