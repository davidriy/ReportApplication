package entities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/*
 * Configuration class. Used to pass and hold configuration data between classes
 * 		Reads config.properties - if it does not exist an error will be thrown
 */
public class Configuration {
	private String ip = "";
	private String port = "";
	private String username = "";
	private String password = "";
	private String path = "";
	private boolean isFirstPDF = false;
	private boolean isFirstHTML = false;
	private boolean isFirstCSV = false;
	private boolean isSecondPDF = false;
	private boolean isSecondHTML = false;
	private boolean isSecondCSV = false;
	private boolean isThirdPDF = false;
	private boolean isThirdHTML = false;
	private boolean isThirdCSV = false;
	private boolean greetingDisplayed = false;
	private boolean configurationChecked = false;
	
	public static File configFile = new File("./src/Utils/config.properties");

	// Retrieves the configuration from the configuration file
	public static Configuration obtainConfig() {
		Configuration configuration = new Configuration();
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(configFile));
			configuration.setIp(properties.getProperty("IP"));
			configuration.setPort(properties.getProperty("PORT"));
			configuration.setPath(properties.getProperty("PATH"));
			configuration.setUsername(properties.getProperty("USER"));
			configuration.setPassword(properties.getProperty("PASSWORD"));
			configuration.setFirstPDF(properties.getProperty("PDF_FIRST").equals("0") ? false : true);
			configuration.setFirstHTML(properties.getProperty("HTML_FIRST").equals("0") ? false : true);
			configuration.setFirstCSV(properties.getProperty("CSV_FIRST").equals("0") ? false : true);
			configuration.setSecondPDF(properties.getProperty("PDF_SECOND").equals("0") ? false : true);
			configuration.setSecondHTML(properties.getProperty("HTML_SECOND").equals("0") ? false : true);
			configuration.setSecondCSV(properties.getProperty("CSV_SECOND").equals("0") ? false : true);
			configuration.setThirdPDF(properties.getProperty("PDF_THIRD").equals("0") ? false : true);
			configuration.setThirdHTML(properties.getProperty("HTML_THIRD").equals("0") ? false : true);
			configuration.setThirdCSV(properties.getProperty("CSV_THIRD").equals("0") ? false : true);
			configuration.setGreetingDisplayed(properties.getProperty("GREETING").equals("0") ? false : true);
			configuration.setConfigurationChecked(properties.getProperty("CONFIGURATION").equals("0") ? false : true);
		} catch (IOException e) {
			e.printStackTrace();
	    }
		return configuration;
	}
	// Writes new configuration into configuration file
	public static void writeConfig(Configuration config) {
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(configFile));
			properties.setProperty("IP", config.getIp());
			properties.setProperty("PORT", config.getPort());
			properties.setProperty("USER", config.getUsername());
			properties.setProperty("PATH", config.getPath());
			properties.setProperty("PASSWORD", config.getPassword());
			properties.setProperty("PDF_FIRST", config.isFirstPDF() ? "1" : "0");
			properties.setProperty("HTML_FIRST", config.isFirstHTML() ? "1" : "0");
			properties.setProperty("CSV_FIRST", config.isFirstCSV() ? "1" : "0");
			properties.setProperty("PDF_SECOND", config.isSecondPDF() ? "1" : "0");
			properties.setProperty("HTML_SECOND", config.isSecondHTML() ? "1" : "0");
			properties.setProperty("CSV_SECOND", config.isSecondCSV() ? "1" : "0");
			properties.setProperty("PDF_THIRD", config.isThirdPDF() ? "1" : "0");
			properties.setProperty("HTML_THIRD", config.isThirdHTML() ? "1" : "0");
			properties.setProperty("CSV_THIRD", config.isThirdCSV() ? "1" : "0");
			properties.setProperty("GREETING", config.isGreetingDisplayed() ? "1" : "0");
			properties.setProperty("CONFIGURATION", config.isConfigurationChecked() ? "1" : "0");
			properties.store(new FileOutputStream(configFile), null);
		} catch (IOException e) {
	      e.printStackTrace();
	    } 
	}
	
	public Configuration(String ip, String port, String username, String password, String path) {
		super();
		this.ip = ip;
		this.port = port;
		this.username = username;
		this.password = password;
		this.path = path;
	}
	public Configuration(String ip, String port, String username, String password, String path, boolean firstPDF, boolean firstHTML, boolean firstCSV, boolean secondPDF, boolean secondHTML, boolean secondCSV, boolean thirdPDF, boolean thirdHTML, boolean thirdCSV, boolean greeting, boolean configurationChecked) {
		super();
		this.ip = ip;
		this.port = port;
		this.username = username;
		this.password = password;
		this.path = path;
		this.isFirstPDF = firstPDF;
		this.isFirstHTML = firstHTML;
		this.isFirstCSV = firstCSV;
		this.isSecondPDF = secondPDF;
		this.isSecondHTML = secondHTML;
		this.isSecondCSV = secondCSV;
		this.isThirdPDF = thirdPDF;
		this.isThirdHTML = thirdHTML;
		this.isThirdCSV = thirdCSV;
		this.greetingDisplayed = greeting;
		this.configurationChecked = configurationChecked;
	}
	public Configuration() {
		super();
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public boolean isFirstPDF() {
		return isFirstPDF;
	}
	public void setFirstPDF(boolean isFirstPDF) {
		this.isFirstPDF = isFirstPDF;
	}
	public boolean isFirstHTML() {
		return isFirstHTML;
	}
	public void setFirstHTML(boolean isFirstHTML) {
		this.isFirstHTML = isFirstHTML;
	}
	public boolean isFirstCSV() {
		return isFirstCSV;
	}
	public void setFirstCSV(boolean isFirstCSV) {
		this.isFirstCSV = isFirstCSV;
	}
	public boolean isSecondPDF() {
		return isSecondPDF;
	}
	public void setSecondPDF(boolean isSecondPDF) {
		this.isSecondPDF = isSecondPDF;
	}
	public boolean isSecondHTML() {
		return isSecondHTML;
	}
	public void setSecondHTML(boolean isSecondHTML) {
		this.isSecondHTML = isSecondHTML;
	}
	public boolean isSecondCSV() {
		return isSecondCSV;
	}
	public void setSecondCSV(boolean isSecondCSV) {
		this.isSecondCSV = isSecondCSV;
	}
	public boolean isThirdPDF() {
		return isThirdPDF;
	}
	public void setThirdPDF(boolean isThirdPDF) {
		this.isThirdPDF = isThirdPDF;
	}
	public boolean isThirdHTML() {
		return isThirdHTML;
	}
	public void setThirdHTML(boolean isThirdHTML) {
		this.isThirdHTML = isThirdHTML;
	}
	public boolean isThirdCSV() {
		return isThirdCSV;
	}
	public void setThirdCSV(boolean isThirdCSV) {
		this.isThirdCSV = isThirdCSV;
	}
	public boolean isGreetingDisplayed() {
		return greetingDisplayed;
	}
	public void setGreetingDisplayed(boolean greetingDisplayed) {
		this.greetingDisplayed = greetingDisplayed;
	}
	public boolean isConfigurationChecked() {
		return configurationChecked;
	}
	public void setConfigurationChecked(boolean configurationChecked) {
		this.configurationChecked = configurationChecked;
	}
}
