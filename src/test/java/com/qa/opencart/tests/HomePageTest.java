package com.qa.opencart.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.constants.AppError;

public class HomePageTest extends BaseTest{
	@BeforeClass
	public void homePageSetup() {
		homePage=loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@Test
	public void loginPageTitleTest() {
		String actTilte = homePage.getLoginPageTitle();
		Assert.assertEquals(actTilte, AppConstants.HOME_PAGE_TITLE, AppError.TTITLE_NOT_FOUND_ERROR); 
	}
	@Test
	public void loginPageURLTest() {
		String actURL = homePage.getLoginPageURL();
		Assert.assertTrue(actURL.contains(AppConstants.HOME_PAGE_URL_FRACTION), AppError.URL_NOT_FOUND_ERROR); 
	}
	
	@Test
	public void logOutLinkExistTest() {
		Assert.assertTrue(homePage.isLogOutLinkExist(), AppError.ELEMENT_NOT_FOUND_ERROR);
		
	}
	
	@Test
	public void	 headerTest() {
		List<String> actualHeaders = homePage.getHeaderList();
		System.out.println("Home Page headers are "+actualHeaders);
	}
	@DataProvider
	public Object[] getSearchData() {
		return new Object[] []{
				{"macbook", 3},
				{"imac", 1},
				{"samsung", 2},
				{"canon", 1},
				{"airtel", 0},
		};
	}
	@Test(dataProvider = "getSearchData")
	public void searchTest(String searchKey, int resultCount) {
		searchResultPage = 	homePage.dosearch(searchKey);
		Assert.assertEquals(searchResultPage.getProductResultscount(), resultCount);
	}
	
	@Test(description = "Checking Logo on Home page")
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
	@Test(dataProvider="getFooter", description ="Checking Footer link on Home Page")
	public void footerTest(String footerLink) {
		Assert.assertTrue(commonsPage.checkFooterLink(footerLink));
		
	}
	
	

}
