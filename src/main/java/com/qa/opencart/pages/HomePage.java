package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.util.ElementUtil;

public class HomePage {

	private WebDriver driver;
	private ElementUtil eleUtil;
	
	public HomePage( WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	} 
	
	private By logoutLink = By.linkText("Logout");
	private By headers = By.cssSelector("div#content h2 ");
	private By search = By.name("search");
	private By searchIcon = By.cssSelector("div#search  button");
	
	public String getLoginPageTitle() {
		String title= eleUtil.waitForTitleContains(AppConstants.HOME_PAGE_TITLE, AppConstants.DEFAULT_TIME_OUT);
		System.out.println("Home Page Title is->" +title);
		return title;
	}
	public String getLoginPageURL() {
		String URL =eleUtil.waitForURLcontains(AppConstants.HOME_PAGE_URL_FRACTION, AppConstants.DEFAULT_TIME_OUT);
		System.out.println("Home Page URL is->" +URL);
		return URL;
	}
	public boolean isLogOutLinkExist() {
		return	eleUtil.doElementDisplayed(logoutLink);
	}

	public void logOut() {
		if(isLogOutLinkExist()) {
			eleUtil.doClick(logoutLink);
		}
	}
	public List<String> getHeaderList(){
		List<WebElement> headerList =	eleUtil.waitForElementsVisbile(headers, AppConstants.SHORT_TIME_OUT);
		List<String> headersVaList = new ArrayList<String>();
		for(WebElement e:headerList ) {
			String text = e.getText();
			headersVaList.add(text);
		}
		return headersVaList;
		
	}
	
	public SearchResultPage dosearch(String searchKey)  {
		System.out.println("Search Key: "+searchKey);
		WebElement element = eleUtil.waitForelementVisibility(search, AppConstants.SHORT_TIME_OUT);
		eleUtil.doSendkeys(element, searchKey);
		eleUtil.doClick(searchIcon);
		return new SearchResultPage(driver);
		
		
	}
}
