/**
* Properties reader helper service
*
* @author  Arquillos
*/
package services;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import tests.TestBaseClass;


public class PropertiesReader {
	static final Logger logger = Logger.getLogger(PropertiesReader.class);

	/**
	 * Load the application parameters
	 * 
	 * Some UK text labels needs the UTF-8 format support
	 * and so the InputStreamReader(<stream>, "UTF-8") method is needed.
	 */
	public HashMap<String,String> readPropertiesFile(String file, HashMap<String,String> map) {
		logger.debug("Loading generic properties... (" + file + ")");

		Properties prop = new Properties();

		try (InputStream inputStream = TestBaseClass.class.getClassLoader().getResourceAsStream(file)) {
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
			prop.load(inputStreamReader);
			logger.debug("...loaded.");

			copyPropertiesToMap(prop, map);

			printReadedProperties(map);
		} catch (IOException e) {
			logger.error("Could not read properties file: " + file + " - Error: " + e.getMessage());
			e.printStackTrace();
		}

		return map;
	}

	private void copyPropertiesToMap(Properties prop, HashMap<String,String> map) {
		logger.debug("Copying properties to map...");
		for (String key : prop.stringPropertyNames()) {
			String value = prop.getProperty(key);
			map.put(key, value);
		}
		logger.debug("...done.");
	}

	private void printReadedProperties(HashMap<String,String> map) {
		logger.debug("Printing properties values...");
		map.forEach((key, value) -> logger.debug(key + " : " + value));
	}

	public JSONObject readJSONFile(String fileName) {
		logger.debug("Reading JSON file: payloads/" + fileName);

		JSONObject jsonObject = null;

		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		String jsonFile = classLoader.getResource("payloads/" + fileName).getFile();
		logger.debug("   - File: " + jsonFile);

		FileReader fr;
		try {
			fr = new FileReader(jsonFile);
		} catch (FileNotFoundException e) {
			logger.error("Cant read file (FileNotFoundException)!!!");
			logger.error(e.getMessage());
			return null;
		}

		try {
			JSONParser parser = new JSONParser();
			jsonObject = (JSONObject) parser.parse(fr);
		} catch (IOException e) {
			logger.error("Cant parse file (IOException)!!!");
			logger.error(e.getMessage());
		} catch (ParseException e) {
			logger.error("Cant parse file (ParseException)!!!");
			logger.error(e.getMessage());
			return null;
		}

		// A JSON object. Key value pairs are unordered. JSONObject supports java.util.Map interface.
		logger.debug("JSON file content: ");
		logger.debug(jsonObject.toString());

		return jsonObject;

	}
}