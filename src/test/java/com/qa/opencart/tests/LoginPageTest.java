package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.constants.AppError;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("Epic 100: Design login page for Open cart")
@Story("US 101: Design the various features of open cart login page")
@Feature("Feature 50: Login page feature")
public class LoginPageTest extends BaseTest {
	@Description("Checking Login page title")
	@Severity(SeverityLevel.MINOR)
	@Test
	public void loginPageTitleTest() {
		String actTilte = loginPage.getLoginPageTitle();
		Assert.assertEquals(actTilte, AppConstants.LOGIN_PAGE_TITLE, AppError.TTITLE_NOT_FOUND_ERROR); 
	}
	@Description("Checking Login URL Checking")
	@Severity(SeverityLevel.MINOR)
	@Test(enabled = false)
	public void loginPageURLTest() {
		String actURL = loginPage.getLoginPageURL();
		Assert.assertTrue(actURL.contains(AppConstants.LOGIN_PAGE_URL_FRACTION), AppError.URL_NOT_FOUND_ERROR); 
	}
	@Description("Checking Forget Password  Link ")
	@Severity(SeverityLevel.CRITICAL)
	@Test(enabled = false)
	public void forgotPwdLinkExistTest() {
		Assert.assertTrue(loginPage.isForgotPwdLinkExist(), "Link not available");
		
	}
	@Description("Checking Login with Username and Password ")
	@Severity(SeverityLevel.BLOCKER)
	@Test(priority = Integer.MAX_VALUE, enabled = false)
	public void loginTest() {
		homePage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		Assert.assertEquals(homePage.getLoginPageTitle(), AppConstants.HOME_PAGE_TITLE , AppError.TTITLE_NOT_FOUND_ERROR);
		
	}
	@Description("Checking Login page Logo")
	@Severity(SeverityLevel.CRITICAL)
	@Test(enabled = false)
	public void logoTest() {
		Assert.assertTrue(commonsPage.isLogoDisaplayed(), AppError.LOGO_NOT_FOUND_ERROR);
	}
	@DataProvider
	public Object[][] getFooter() {
		return new Object[][] {
			{"About Us"},
			{"Contact Us"},
			{"Brands"},
			{"My Account"},
		};
	}
	@Description("Checking Login Footer ")
	@Severity(SeverityLevel.CRITICAL)
	@Test(dataProvider="getFooter", enabled = false)
	public void footerTest(String footerLink) {
		Assert.assertTrue(commonsPage.checkFooterLink(footerLink));
		
	}


}
