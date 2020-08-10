/**
* Rest helper service
*
* @author  Arquillos
*/
package services;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;


public class RestHelper {
	static final Logger logger = Logger.getLogger(RestHelper.class);
	private String uRLBase;
	private String user, password;
	private String responseMessage;
	private int responseCode = 0;
	private JSONObject jsonParams;

	public RestHelper(String uRLBase) {
		this.uRLBase = uRLBase;
	}

	public String POSTRequest(String endpoint, String user, String password, String jsonBody) {
		logger.debug("POST /" + endpoint + " request with:");
		// TODO - Obfuscate password
		logger.debug("   Credentials: " + user + "/" + password);
		logger.debug("   Body: " + jsonBody);

		logger.debug("Setting Json body from String parameter");
		JSONParser parser = new JSONParser();
		try {
			this.jsonParams = (JSONObject) parser.parse(jsonBody);
		} catch (org.json.simple.parser.ParseException e) {
			logger.error("Cant parse file (ParseException)!!!");
			logger.error(e.getMessage());
			return "";
		}

		this.user = user;
		this.password = password;

		return POSTRequest(endpoint);
	}

	private String POSTRequest(String endpoint) {
		logger.debug("POST /" + endpoint + " request");

		logger.debug("   - Making HTTP request");
		CloseableHttpResponse response = postEndpoint();
		if (response != null) {
			if (responseCode != HttpStatus.SC_OK) {
				logger.error("POST not worked!!!");
			}
		}

		return responseMessage;
	}

	private URL getURL(String url) {
		logger.debug("Parsing URL: " + url);
		try {
			return new URL(url);
		} catch (MalformedURLException e) {
			printErrorMessage("   - Not a valid URL (MalformedURLException)!!!", e);
			return null;
		}
	}

	/**
	 * Consume the endpoint using the POST verb
	 * @return The HTTP response object (null if there is an error
	 */
	private CloseableHttpResponse postEndpoint() {
		URL backendURL = getURL(this.uRLBase);
		if (backendURL == null) {
			return null;
		}

		HttpPost post = new HttpPost(backendURL.toString());
		logger.debug("Setting HTTP Content-Type header");
		post.addHeader(HttpHeaders.CONTENT_TYPE, "application/json");
		String encoding = Base64.getEncoder().encodeToString((this.user + ":" + this.password).getBytes(StandardCharsets.UTF_8));
		post.setHeader(HttpHeaders.AUTHORIZATION, "Basic " + encoding);
		logger.debug("Adding Json body");
		StringEntity jsonBody = getJsonBody();
		post.setEntity(jsonBody);

		try (CloseableHttpClient httpClient = HttpClients.createDefault();
				CloseableHttpResponse response = httpClient.execute(post)) {

			this.responseMessage = getResponseMessage(response);
			this.responseCode = getResponseCode(response);

			httpClient.close();

			return response;
		} catch (IOException e) {
			printErrorMessage("Cant execute Post, maybe the connection was aborted (IOException)", e);
			return null;
		}
	}

	/**
	 * Print an error message and the associated exception
	 * @param message String message
	 * @param e Exception
	 */
	private void printErrorMessage(String message, Exception e) {
		logger.error(message);
		logger.error(e.toString());
	}

	/**
	 * Convert the JSON object to a Apache HTTP entity 
	 * @return An Apache HTTP entity
	 */
	private StringEntity getJsonBody() {
		logger.debug("Readying the Json body payload");
		try {
			return new StringEntity(this.jsonParams.toString());
		} catch (UnsupportedEncodingException e) {
			printErrorMessage("Cant create StringEntity (UnsupportedEncodingException)!!!", e);
			return null;
		}
	}

	/**
	 * Get the HTTP response body message
	 * @param response String with the HTTP body response
	 */
	private String getResponseMessage(CloseableHttpResponse response) {
		logger.debug("Getting the HTTP response body");

		String responseMessage;

		try {
			responseMessage = EntityUtils.toString(response.getEntity());
			logger.debug(responseMessage);
			
			return responseMessage;
		} catch (ParseException e) {
			printErrorMessage("Cant convert response to String (ParseException)", e);
			return "";
		} catch (IOException e) {
			printErrorMessage("Cant convert response to String (IOException)", e);
			return "";
		}
	}

	/** 
	 * Get the HTTP response code
	 * @param response The response from the HTTP request
	 * @return The response code from the HTTP request
	 */
	private int getResponseCode(CloseableHttpResponse response) {
		logger.debug("   - Getting HTTP response code");

		int responseCode = response.getStatusLine().getStatusCode();
		logger.debug("   - Response code: " + responseCode);

		return responseCode;

	}
}