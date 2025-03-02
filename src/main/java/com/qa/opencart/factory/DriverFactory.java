package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.exception.FrameworkException;

import io.qameta.allure.Step;

public class DriverFactory {
	WebDriver driver;
	Properties prop;
	public static String highlight;
	public static ThreadLocal<WebDriver> tdriver = new ThreadLocal<WebDriver>();
	public static final Logger log = LogManager.getLogger(DriverFactory.class);
	@Step("inti the Driver using properties: {0}")
	public WebDriver intiDriver(Properties prop) {
		String browserName = prop.getProperty("browser");

		highlight = prop.getProperty("highlight");
		//System.out.println("Browser name is:" + browserName);
		log.info("Browser name is:" + browserName);
		OptionsManager optionsManager = new OptionsManager(prop);
		switch (browserName.trim().toLowerCase()) {
		case "chrome":
			tdriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
			// driver= new ChromeDriver(optionsManager.getChromeOptions());
			break;
		case "firefox":
			tdriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
			// driver = new FirefoxDriver(optionsManager.getFirefoxOptions());
			break;
		case "edge":
			tdriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
			// driver = new EdgeDriver(optionsManager.getEdgeOptions());
			break;
		case "safari":
			tdriver.set(new SafariDriver());
			// driver= new SafariDriver()
			break;
		default:
			//System.out.println("Plz pass the valid browser name: " + browserName);
			log.error("Plz pass the valid browser name: " + browserName);
			throw new FrameworkException("Enter valid Browser");
		}

		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(prop.getProperty("url"));
		return getDriver();
	}

	//getdriver used to return the thread local.
	public static WebDriver getDriver() {
		return tdriver.get();
	}

	/*
	 * This method used to init the properties from properties file. FileInputStream
	 * opens the file and reads its raw bytes. The Properties class loads the byte
	 * stream (provided by FileInputStream) and parses it into a format of key-value
	 * pairs.
	 */
	
	
	/*
	 * Supply enviroment using Maven command line 
	 * Mvn clean install -Denv='QA'
	 * 
	 */
	public Properties intprop() {
		
		String envName = System.getProperty("env");
		System.out.println("Running test suite on env: "+envName);
		FileInputStream ip  = null;
		prop = new Properties();
		try {
		if(envName == null) {
			//System.out.println("No Env is passed, Hence running QA Enviroment ");
			log.warn("No Env is passed, Hence running QA Enviroment ");
			ip = new FileInputStream(AppConstants.CONFIG_QA_PROP_FILE_PATH);
			
		}else {
			switch (envName.toLowerCase().trim()) {
			case "qa":
				ip = new FileInputStream(AppConstants.CONFIG_QA_PROP_FILE_PATH);
				break;
			case "dev":
				ip = new FileInputStream(AppConstants.CONFIG_DEV_PROP_FILE_PATH);
				break;
			case "stage":
				ip = new FileInputStream(AppConstants.CONFIG_STAGE_PROP_FILE_PATH);
				break;
			case "uat":
				ip = new FileInputStream(AppConstants.CONFIG_UAT_PROP_FILE_PATH);
				break;
			case "prod":
				ip = new FileInputStream(AppConstants.CONFIG_PROD_FILE_PATH);
				break;

			default:
				log.error("plz pass the right env name");
				throw new FrameworkException("Invalid enviroment ");
			}
		}
		prop.load(ip);
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		
		return prop;
	}
	
	public static String getScreenShot() {
		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir")+"/Screenshot/" +"_" +System.currentTimeMillis()+".png";
		File destination = new File(path);
		try {
			FileHandler.copy(srcFile,destination);
		}catch(IOException e ){
			e.printStackTrace();			
		}
        return path;
			}
	public static File getScreenShotFile() {
		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
			return srcFile;
			}
	public static String getScreenShotString() {
		String srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BASE64);
			return srcFile;
			}
	public static byte[] getScreenShotByte() {
		byte[] srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);
			return srcFile;
			}


}
