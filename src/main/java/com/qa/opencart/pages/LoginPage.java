package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.util.ElementUtil;

import io.qameta.allure.Step;


public class LoginPage {
	private WebDriver driver;
	private ElementUtil eleUtil;
	public LoginPage(WebDriver driver) {
		this.driver=driver;
		eleUtil = new ElementUtil(driver);
	}
	//By locators for Logi Page 
	private By emailId = By.id("input-email");
	private By password = By.id("input-password");
	private By loginBtn = By.xpath("//input[@value='Login']");
	private By forgotPwdLink = By.linkText("Forgotten Password");
	@Step("getLoginPageTitle")
	public String getLoginPageTitle() {
		String title= eleUtil.waitForTitleContains(AppConstants.LOGIN_PAGE_TITLE, AppConstants.DEFAULT_TIME_OUT);
		System.out.println("Login Page Title is->" +title);
		return title;
	}
	@Step("getLoginPageURL")
	public String getLoginPageURL() {
		String URL =eleUtil.waitForURLcontains(AppConstants.LOGIN_PAGE_URL_FRACTION, AppConstants.DEFAULT_TIME_OUT);
		System.out.println("Login Page URL is->" +URL);
		return URL;
	}
	@Step("Checking Forgot password link is dispalyed.....")
	public boolean isForgotPwdLinkExist() {	
		return 	eleUtil.doElementDisplayed(forgotPwdLink);		
	}
	@Step("Login with Username: {0} and Password: {1}")
	public HomePage doLogin(String userName, String pwd) {
		System.out.println("Page Credentials are  " +userName+ ":" +pwd);
		eleUtil.waitForelementVisibility(emailId, AppConstants.SHORT_TIME_OUT).sendKeys(userName);
		eleUtil.doSendkeys(password, pwd);
		eleUtil.doClick(loginBtn);
		return new HomePage(driver);
	}

}
