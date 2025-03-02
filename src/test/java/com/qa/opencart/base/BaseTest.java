package com.qa.opencart.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.CommonsPage;
import com.qa.opencart.pages.HomePage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.ProductPageInfo;
import com.qa.opencart.pages.SearchResultPage;


public class BaseTest {
		WebDriver driver;
		DriverFactory df;
		protected  Properties prop;
		protected LoginPage loginPage;
		protected HomePage homePage;
		protected CommonsPage commonsPage;
		protected SearchResultPage searchResultPage;
		protected ProductPageInfo productPageInfo;
		@Parameters({"browser"})
		@BeforeTest(description ="Setup: Init driver and properties")
		public void setup(String browserName) {
			df = new DriverFactory();
			prop=df.intprop();
			if(browserName!= null) {
				prop.setProperty("browser", browserName);
			}
			driver = df.intiDriver(prop);
			loginPage = new LoginPage(driver);
			commonsPage = new CommonsPage(driver);
		}
		
		@AfterTest(description ="TearDown: Closing the Browser ")
		public void tearDown()  {
			driver.quit();
		}
		
		@AfterMethod(description = " Taking the Screenshot only if Test is failed")
		public void attachScreenShot(ITestResult result) {
				if(!result.isSuccess()) {
					ChainTestListener.embed(DriverFactory.getScreenShotFile(), "image/png");
				}
		}
}
