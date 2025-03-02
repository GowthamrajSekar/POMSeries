package com.qa.opencart.util;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JavascriptUtil {

	private WebDriver driver;
	private JavascriptExecutor js;

	// Constructor
	public JavascriptUtil(WebDriver driver) {
		this.driver = driver;
		js= (JavascriptExecutor)driver;
		

	}
	
	public String getPageTitle() {
		return 	js.executeScript("return document.title").toString();
	}
	public String getPageURL() {
		return 	js.executeScript("return document.URL").toString();
	}
	
	public void refershBrowserByJS() {
		js.executeScript("history.go(0)");
	}
	public void navigateToBackPage() {
		js.executeScript("history.go(-1)");
	}
	public void navigateToForwardPage() {
		js.executeScript("history.go(1)");
	}
	public void generateJSAlert(String message) {
		js.executeScript("alert("+message+")");
	}
	public String getPageInnerText() {
		return js.executeScript("document.documentElement.innerText").toString();
	}
	public void pageDown() {
		 js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
	}
	public void pageUP() {
		 js.executeScript("window.scrollTo(document.body.scrollHeight, 0)");
	}
	
	public void pageDown(String height) {
		 js.executeScript("window.scrollTo(0, "+height+")");
	}
	
	public void scrollIntoView(WebElement element) {
		js.executeScript("arguments[0].scrollIntoView(true)", element);
		
	}
	
	public void drawBorder(WebElement element) {
		js.executeScript("arguments[0].style.border='3px solid red'", element);
	}
	public void flash(WebElement element) {
		String bgcolor = element.getCssValue("backgroundColor");
		for(int i=0;i<10;i++) {
			changeColor("rgb(0,200,0)", element);
			changeColor(bgcolor,element); 
		}
	}
	public void changeColor(String color, WebElement element) {
		js.executeScript("arguments[0].style.backgroundColor = '"+color+"'",element);
		try {
			Thread.sleep(20);
		}catch(InterruptedException e) {
			
		}
	}
	public void clickElementByJs(WebElement element) {
		js.executeScript("arguments[0].click()", element);
		
	}
	public void sendKeysByUsingId(String id, String name) {
		js.executeScript("document.getElementById('" + id + "').value='" + name + "'");
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
