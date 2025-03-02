package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.util.ExcelUtil;

public class ProductPageInfoTest extends BaseTest {
	
	@BeforeClass
	public void homePageSetup() {
		homePage=loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@DataProvider
	public Object[][] getProductData(){
		return new Object[][]{
			{"MacBook", "MacBook Pro"},
			{"MacBook", "MacBook Air"},
			{"imac", "iMac"},
			{"Samsung", "Samsung SyncMaster 941BW"},
			{"Samsung", "Samsung Galaxy Tab 10.1"},
		};
	}
	@Test(dataProvider= "getProductData")
	public void productSearchHeaderTest(String searchKey, String productName )  {
		ChainTestListener.log(searchKey +":" +productName);
		searchResultPage = homePage.dosearch(productName);
		productPageInfo = searchResultPage.selectProduct(productName);
		String actualProductHeader =productPageInfo.getProductHeader();
		Assert.assertEquals(actualProductHeader, productName);
	}
	@DataProvider
	public Object[][] getProductImages(){
		return new Object[][]{
			{"MacBook", "MacBook Pro", 4},
			{"MacBook", "MacBook Air", 4},
			{"imac", "iMac", 3},
			{"Samsung", "Samsung SyncMaster 941BW", 1},
			{"Samsung", "Samsung Galaxy Tab 10.1", 7},
		};
	}
	
	@DataProvider
	public Object[][] getProductImagesSheetData(){
		Object productData[][]=ExcelUtil.getData(AppConstants.PRODUCT_SHEET_NAME);
		return productData;
	}
	@Test(dataProvider="getProductImagesSheetData")
	public void productImagesCountTest(String searchKey, String productName, String expectedCount)  {
		searchResultPage = homePage.dosearch(searchKey);
		productPageInfo = searchResultPage.selectProduct(productName);
		int actualProductImagesCount =productPageInfo.getProductImages();
		Assert.assertEquals(actualProductImagesCount, Integer.parseInt(expectedCount));
	}
	
	
	@Test
	public void productInfo() {
		searchResultPage = homePage.dosearch("Macbook");
		productPageInfo = searchResultPage.selectProduct("MacBook Pro");
		Map<String, String> productInfoMap = productPageInfo.getProductMetaDetail();
		productInfoMap.forEach((k,v)-> System.out.println(k +":"+v));
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertEquals(productInfoMap.get("Header"), "MacBook Pro");
		softAssert.assertEquals(productInfoMap.get("Brand"), "Apple");
		softAssert.assertEquals(productInfoMap.get("Availability"), "In Stock");
		softAssert.assertEquals(productInfoMap.get("Product Code"), "Product 18");
		softAssert.assertEquals(productInfoMap.get("Reward Points"), "800");
		softAssert.assertEquals(productInfoMap.get("Price"), "$2,000.00");
		softAssert.assertEquals(productInfoMap.get("ExPrice"), "$2,000.00");
		softAssert.assertAll();
		
	}
	
}
