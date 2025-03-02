package com.qa.opencart.pages;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.util.ElementUtil;

public class ProductPageInfo {
	private WebDriver driver;
	private ElementUtil eleUtil;
	private Map<String, String> productMap;
	public ProductPageInfo(WebDriver driver) {
		this.driver= driver;
		eleUtil = new ElementUtil(driver);
	}
	
	private By productResult  = By.tagName("h1");
	private By productImages =By.cssSelector("ul.thumbnails a");
	private By productMetaData = By.xpath("(//div[@class='btn-group']/following-sibling::ul)[1]/li");
	private By productPriceData = By.xpath("(//div[@class='btn-group']/following-sibling::ul)[2]/li");
	
	public String getProductHeader() {
		String header =  eleUtil.doGetElementGetText(productResult);
		System.out.println("Product header : "+header);
		return header;
	}
	
	public int getProductImages() {
		int imagesCount = eleUtil.waitForElementsPresence(productImages, AppConstants.DEFAULT_TIME_OUT).size();
		System.out.println(getProductHeader() +"  Images Count =   "+imagesCount);
		return imagesCount;
	}
	/*
	 * HapMap - doesn't maintain any order
	 * LinkedHashMap- It maintain Order to print-put key value in Map
	 * TreeMap - It maintain Order(Ascending, Chronological)
	 */
	public Map<String, String> getProductMetaDetail() {
		//productMap = new HashMap<String, String>();
		//productMap = new LinkedHashMap<String, String>();
		productMap = new TreeMap<String, String>();
		productMap.put("Header", getProductHeader());
		productMap.put("imagesCount ", getProductImages()+"");
		getProductMetaData();
		getProductPriceData();
		return productMap;
		
	}
	/*
	 * Brand: Apple
       Product Code: Product 17
       Reward Points: 700
       Availability: Out Of Stock
	 */
	private void getProductMetaData() {
		List<WebElement> metaList = eleUtil.waitForElementsPresence(productMetaData, AppConstants.DEFAULT_TIME_OUT);
		for(WebElement e: metaList) {
			String metaText = e.getText();
			String meta[]=metaText.split(":");
			String metaKey = meta[0].trim();
			String metaValue =  meta[1].trim();
			productMap.put(metaKey, metaValue);

		}
	}
	private void getProductPriceData() {
		List<WebElement> priceList = eleUtil.waitForElementsPresence(productPriceData, AppConstants.DEFAULT_TIME_OUT);
		String productPrice = priceList.get(0).getText().trim();
		String productExTax = priceList.get(1).getText().split(":")[1].trim();
		productMap.put("Price", productPrice);
		productMap.put("ExPrice", productExTax);
		
	}
	

}
