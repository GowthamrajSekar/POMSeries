package com.qa.opencart.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.util.ElementUtil;

public class SearchResultPage {
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	public SearchResultPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	private By productDetails = By.cssSelector("div.product-thumb");
	public int getProductResultscount() {
		int resultcount =	eleUtil.waitForElementsVisbile(productDetails, AppConstants.SHORT_TIME_OUT).size();
		System.out.println("Product Count are   " +resultcount);
		return resultcount;
	}
	
	public ProductPageInfo selectProduct(String ProductName)  {
		System.out.println("Product name : "+ProductName); 
		eleUtil.doClick(By.linkText(ProductName));
		return new ProductPageInfo(driver);
	}

}
