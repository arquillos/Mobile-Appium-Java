/**
* Main screen page objects
*
* @author  Arquillos
*/
package page.objects;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class MainPageObjects {
	@AndroidFindBy(xpath="//android.view.ViewGroup[1]/android.widget.TextView[@index='0']")
	@iOSXCUITFindBy(xpath="//XCUIElementTypeStaticText[@name=\"The App\"]")
	public MobileElement appTitle;

	@AndroidFindBy(xpath="//android.view.ViewGroup[@content-desc=\"Photo Demo\"]/android.widget.TextView[1]")
	@iOSXCUITFindBy(xpath="//XCUIElementTypeOther[@name=\"Photo Demo\"]")
	public MobileElement photoDemo;

	@AndroidFindBy(xpath="//android.view.ViewGroup[@content-desc=\"Echo Box\"]/android.widget.TextView[1]")
	@iOSXCUITFindBy(xpath="//XCUIElementTypeOther[@name=\"Echo Box\"]")
	public MobileElement echoBox;
}