/**
* Visual check service
*
* @author  Arquillos
*/
package services;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.imagecomparison.SimilarityMatchingOptions;
import io.appium.java_client.imagecomparison.SimilarityMatchingResult;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class VisualCheck {
	static final Logger logger = Logger.getLogger(VisualCheck.class);

	private AppiumDriver<MobileElement> driver;

	private String validationPath;
	private String baselinePrefix;
	private double matchThreshold;
	private JiraHelper jh;
	private String jiraTitle, jiraDescription, jiraTicketType;


	public VisualCheck(AppiumDriver<MobileElement> driver, String path, String prefix,
					   String threshold, String jiraBaseURL, String jiraTicketCreationEndpoint,
					   String jiraProjectID, String jiraTitle, String jiraDescription, String jiraTicketType) {
		this.driver = driver;
		this.validationPath = path;
		this.baselinePrefix = prefix + "_";
		this.matchThreshold = Double.parseDouble(threshold);
		this.jiraTitle = jiraTitle;
		this.jiraDescription = jiraDescription;
		this.jiraTicketType = jiraTicketType;
		logger.debug("Parameters: ");
		logger.debug("   Validation path:   " + this.validationPath);
		logger.debug("   Prefix:            " + this.baselinePrefix);
		logger.debug("   Match threshold:   " + this.matchThreshold);
		logger.debug("   Match threshold:   " + this.matchThreshold);
		logger.debug("   JIRA base URL:                  " + jiraBaseURL);
		logger.debug("   JIRA new issue endpoint:        " + jiraTicketCreationEndpoint);
		logger.debug("   JIRA ticket title prefix:       " + this.jiraTitle);
		logger.debug("   JIRA ticket description prefix: " + this.jiraDescription);
		logger.debug("   JIRA ticket type:               " + this.jiraTicketType);
		jh = new JiraHelper(jiraBaseURL, jiraTicketCreationEndpoint, jiraProjectID);
	}

	/**
	 * Execute image comparision
	 * @return True if the image is "equal" to the saved baseline or when a new baseline is created
	 */
	public boolean execute(String checkName) {
		String baselineFilename = this.validationPath + "/baselines/" + this.baselinePrefix + checkName + ".png";
		File baselineImg = new File(baselineFilename);

		if (!baselineImg.exists()) {
			logger.debug("No baseline found for " + checkName + " screen check. Capturing a new baseline instead of checking");
			File newBaseline = driver.getScreenshotAs(OutputType.FILE);
			try {
				FileUtils.copyFile(newBaseline, new File(baselineFilename));
			} catch (IOException e) {
				logger.error("Failed trying to copy the new baseline image to: " + baselineFilename);
				e.printStackTrace();
				return false;
			}
			return true;
		} else {
			SimilarityMatchingOptions opts = new SimilarityMatchingOptions();
			opts.withEnabledVisualization(); // Active to see what went wrong if something did.
			SimilarityMatchingResult res;
			try {
				logger.debug("Checking image with existing baseline");
				res = driver.getImagesSimilarity(baselineImg, driver.getScreenshotAs(OutputType.FILE), opts);
				logger.debug("   Result: " + res.getScore());
			} catch (IOException e) {
				logger.error("Failed trying to compare the two images");
				e.printStackTrace();
				return false;
			}

			if (res.getScore() < matchThreshold) {
				logger.debug("Image comparision found discrepancies above the threshold (" + this.matchThreshold + ")");
				File failViz = new File(validationPath + "/failedChecks/FAIL_" + checkName + "_" + getDate() + ".png");
				try {
					logger.debug("   Saving image comparision results to a file: " + failViz.getAbsolutePath());
					res.storeVisualization(failViz);
				} catch (IOException e) {
					logger.error("Failed trying to save the comparision image");
					e.printStackTrace();
					return false;
				}

				jh.createTicket(this.jiraTicketType, this.jiraTitle, this.jiraDescription);
				return false;
			} else {
				logger.debug("Visual check of " + checkName + " passed! (Similarity match: " + res.getScore() + ")");
				return true;
			}
		}
	}

	/**
	 * Get the actual date in a predefined format
	 * @return String with the date
	 */
	private String getDate() {
		// TODO - Read from properties
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HHmmss");
		Date date = new Date();
		String formattedDate = formatter.format(date);
		logger.debug("Formatted date: " + formattedDate);
		return formattedDate;
	}
}