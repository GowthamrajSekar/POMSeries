package com.qa.opencart.util;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.factory.DriverFactory;

import io.qameta.allure.Step;

public class ElementUtil {
	private WebDriver driver;
	private JavascriptUtil jsUtil;

	// Constructor
	public ElementUtil(WebDriver driver) {
		this.driver = driver;
		jsUtil = new JavascriptUtil(driver);

	}

	// Do click method	
	@Step("Clicking on WebElement Using Locator: {0}")
	public void doClick(By locator) {
		getElement(locator).click();
	}

	// Do click method override
	public void doClick(String loctorType, String locatorvalue) {
		getElement(getloacter(loctorType, locatorvalue)).click();
	}

	// Get Text method
	public String doGetElementGetText(By locator) {
		return getElement(locator).getText();

	}

	// Get Text method override
	public String doGetElementGetText(String loctorType, String locatorvalue) {
		return getElement(getloacter(loctorType, locatorvalue)).getText();

	}

	// If we send null in sendkeys it throw Illegal Argument Exception. So that we
	// need to check user can't enter null in sendkeys.
	private void nullCheck(CharSequence... value) {
		if (value == null) {
			throw new RuntimeException("=====Null can't send in Sendkeys========");
		}
	}
	private void highlightElement(WebElement element ) {
		if(Boolean.parseBoolean(DriverFactory.highlight)) {
			jsUtil.flash(element);
		}
	}

	// Do sendkeys method
	// Sendkeys can accept String buffer, String builder, String. Hence change
	// String to Charsequence.... char Sequence is array used to accept all String.
	@Step("Fill value: {1} into WebElement{0}")
	public void doSendkeys(By locator, CharSequence... value) {
		nullCheck(value);
		WebElement element = getElement(locator);
		element.clear();
		element.sendKeys(value);
	}
	@Step("Fill value: {1} into WebElement{0}")
	public void doSendkeys(WebElement element , CharSequence... value) {
		nullCheck(value);
		element.clear();
		element.sendKeys(value);
	}
	// Do Sendkeys method override

	public void doSendkeys(String loctorType, String locatorvalue, CharSequence... value) {
		nullCheck(value);
		WebElement element = getElement(getloacter(loctorType, locatorvalue));
		element.clear();
		element.sendKeys(value);
	}
		@Step("Get Webelement using locator: {0}")
	// Get element -> findelement
	public WebElement getElement(By locator) {
		
		WebElement element = driver.findElement(locator);
		highlightElement(element);
		return element;
	}
	// Get element -> Findelements

	public List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
	}

	// Getlocator method
	public By getloacter(String locatorType, String locatorValue) {
		By locator = null;

		switch (locatorType.toUpperCase().trim()) {
		case "ID":
			locator = By.id(locatorValue);
			break;
		case "NAME":
			locator = By.name(locatorValue);
			break;
		case "XPATH":
			locator = By.xpath(locatorValue);
			break;
		case "CSS":
			locator = By.cssSelector(locatorValue);
			break;
		case "LINKTEXT":
			locator = By.linkText(locatorValue);
			break;
		case "PARTIALLINKTEXT":
			locator = By.partialLinkText(locatorValue);
			break;
		case "TAGNAME":
			locator = By.tagName(locatorValue);
			break;
		case "CLASSNAME":
			locator = By.className(locatorValue);
			break;
		default:
			System.out.println("Enter correct value");
		}
		return locator;
	}
	
	// IsDieaplay method
	// Note. If element not present in page, it doesn't return false instead it gave
	// no such element exception.
	public Boolean isDisplay(By locator) {
		return getElement(locator).isDisplayed();
	}

	public Boolean isDisplay(String loctorType, String locatorvalue) {
		return getElement(getloacter(loctorType, locatorvalue)).isDisplayed();
	}

	public boolean isElementDisplayed(By locator) {
		if (getElements(locator).size() == 1) {
			System.out.println("Element is present ");
			return true;
		} else {
			System.out.println("Elemet is not present ");
			return false;
		}
	}

	// Need to use below method when need to check element not present for negative
	// scenarios
	public boolean isElementDisplayed(By locator, int elementCount) {
		if (getElements(locator).size() == elementCount) {
			System.out.println("Element is not present ");
			return true;
		} else {
			System.out.println("Elemet is  present ");
			return false;
		}
	}

	// with help of try catch we can print isDisplayed() to print false when element
	// not present
	@Step("Element  {0} is display")
	public Boolean doElementDisplayed(By locator) {
		try {
			return getElement(locator).isDisplayed();

		} catch (NoSuchElementException e) {
			System.out.println("Element is not present ");
			return false;
		}

	}

	// To get value with the help of Dom Attribute.
	public String domAtrribute(By locator, String value) {
		return getElement(locator).getDomAttribute(value);

	}

	// To get value with the help of Dom Property
	public String domProperty(By locator, String value) {
		return getElement(locator).getDomProperty(value);

	}

	// Isenabled method  
	public Boolean isEnabled(By locator) {
		return getElement(locator).isEnabled();
	}

	// IsSelected method
	public Boolean isselected(By locator) {
		return getElement(locator).isSelected();
	}

	// *******************Select Drop Down methods***************************

	// Select Drop Down based on Index
	public void doSelectDropDownByIndex(By loactor, int index) {
		Select dropdown = new Select(getElement(loactor));
		dropdown.selectByIndex(index);
	}

	// Select Drop Down based on Value
	public void doSelectDropDownByValue(By locator, String Value) {
		Select dropdown = new Select(getElement(locator));
		dropdown.selectByValue(Value);
	}

	// Select Drop Down based on VisibleText
	public void doSelectDropDownByVisibleText(By locator, String Value) {
		Select dropdown = new Select(getElement(locator));
		dropdown.selectByContainsVisibleText(Value);
	}

	// List out all Text in Drop Down

	public List<String> getDropDownTextList(By locator) {
		Select dropdown = new Select(getElement(locator));
		List<WebElement> allValues = dropdown.getOptions();
		List<String> country = new ArrayList<String>();
		System.out.println(allValues.size());
		for (WebElement e : allValues) {
			String text = e.getText();
			country.add(text);
		}
		return country;

		// List out the number of element in Drop down
	}

	public int getDropDownCount(By locator) {
		Select dropdown = new Select(getElement(locator));
		List<WebElement> allValues = dropdown.getOptions();
		return allValues.size();

	}

	// Select Drop Down with Contains method
	public void doSelectDropDownByContainsValue(By locator, String partialText) {
		Select dropdown = new Select(getElement(locator));
		dropdown.selectByContainsVisibleText(partialText);
	}

	// Select Element from Search Engine

	public void doSearch(By locator, By suggestions, String searchKey, String actualValue) throws InterruptedException {
		getElement(locator).sendKeys(searchKey);
		Thread.sleep(4000);
		List<WebElement> suggList = getElements(suggestions);
		System.out.println(suggList.size());
		for (WebElement e : suggList) {
			String text = e.getText();
			if (text.contains(actualValue)) {
				e.click();
				break;
			}
		}

	}

	/**
	 * 
	 * @param doprdown
	 * @param choices
	 * @param choices1
	 */
	public void multiSelection(By dopddown, By choices, String... choices1) {

		driver.findElement(dopddown).click();
		List<WebElement> choicesList = driver.findElements(choices);
		if (choices1[0].equalsIgnoreCase("all")) {
			for (WebElement e : choicesList) {
				e.click();
			}
		} else {
			for (WebElement e : choicesList) {
				String text = e.getText();
				System.out.println(text);
				for (String ch : choices1) {
					if (text.equals(ch)) {
						e.click();
					}

				}
			}
		}
	}
	
	//****************Actions Utils**************************
	public  void doActionsSendKeys(By locator, CharSequence...value) {
		Actions act = new Actions(driver);
		act.sendKeys(getElement(locator), value).build().perform();
		
	}
	public  void doActionsClick(By locator) {
		Actions act = new Actions(driver);
		act.click(getElement(locator)).build().perform();
		
	}
	
	//2 Level Menu Handling
	
	public  void handleTwoLevelMenuSubMenuHandlinig(By parentMenuLocator, By childMenuLocator) throws InterruptedException {
		/*
		 * act.moveToElement(getElemnet(parentMenuLocator)).build().perform(); - Will work
		 * act.moveToElement(getElemnet(parentMenuLocator)).perform(); - will work because perform internally call build()
		 * act.moveToElement(getElemnet(parentMenuLocator)).build(); - will not work because without perform() Actions class will not work
		 */
		
		Actions act = new Actions(driver);
		act.moveToElement(getElement(parentMenuLocator)).build().perform();
		Thread.sleep(5000);
		getElement(childMenuLocator).click();
		
	}
	
	//4 level menu Handling
	public  void level4subMenuHandling(By level1Menu,By level2Menu,By level3Menu,By level4Menu) throws InterruptedException {
		Actions act = new Actions(driver);
		driver.findElement(level1Menu).click();
		Thread.sleep(2000);
		act.moveToElement(driver.findElement(level2Menu)).build().perform();
		Thread.sleep(2000);
		act.moveToElement(driver.findElement(level3Menu)).build().perform();
		Thread.sleep(2000);
		act.click(driver.findElement(level4Menu)).build().perform();
	}
	//Enter the value with some time interval
	public  void doSendkeysWithPause(By firstName ,String name, int puaseTime) {
		Actions act = new Actions(driver);
		char first[]=name.toCharArray();
		for(char e: first) {
			act.sendKeys(getElement(firstName), String.valueOf(e)).pause(puaseTime).build().perform();
		}
	}
	
	
	//Implicity Wait methods: WebDriverWait Type
	/**
	 * An expectation for checking that an element is present on the DOM of a page. This does notnecessarily mean that the element is visible.
	 * @param locator
	 * @param timeout
	 * @return
	 */
	public  WebElement waitForElementPresence(By locator, long timeout) {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeout));
		WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		highlightElement(element);
		return element;
	}
	
	//Overlodading for polling time
	public  WebElement waitForElementPresence(By locator, long timeout, long pollingTime) {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeout), Duration.ofSeconds(pollingTime));
		WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		highlightElement(element);
		return element;
	}
	/**
	 * when element is ready and enable, then it will click button.
	 * An expectation for checking an element is visible and enabled such that you can click it.
	 * @param locator
	 * @param timeout
	 */
	
	public  void clickElementWhenReady(By locator, long timeout) {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();;
	}
	/**
	 * An expectation for checking that an element is present on the DOM of a page and visible.
	 * Visibility means that the element is not only displayed but also has a height and width that isgreater than 0.
	 * @param locator
	 * @param timeout
	 * @return
	 */
	@Step("Waiting for WebElement Using locator: {0} and timeout{1}")
	public  WebElement waitForelementVisibility(By locator, long timeout){
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeout));
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		highlightElement(element);
		return element;
		
	}
	//Overlodading for polling time
	public  WebElement waitForelementVisibility(By locator, long timeout, long pollingTime){
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeout), Duration.ofSeconds(pollingTime));
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		highlightElement(element);
		return element;
	}
	
	/**
	 * An expectation for checking that there is at least one element present on a web page.
	 *  * If element not present within time, it will 0  -> Collections.emptyList();
	 * @param locator
	 * @param timeout
	 * @return
	 */
	public  List<WebElement>  waitForElementsPresence(By locator, long timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
		return wait.until((ExpectedConditions.presenceOfAllElementsLocatedBy(locator)));
		}
		catch(TimeoutException e) {
			return Collections.emptyList();
		}
	}
	/**An expectation for checking that all elements present on the web page that match the locatorare visible. 
	 * Visibility means that the elements are not only displayed but also have a heightand width that is greater than 0.
	 * If element not present within time, it will 0  -> Collections.emptyList();
	 * @param locator
	 * @param timeout 
	 * @return
	 */
	public  List<WebElement>  waitForElementsVisbile(By locator, long timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
		return wait.until((ExpectedConditions.visibilityOfAllElementsLocatedBy(locator)));
		}catch(TimeoutException e) {
			return Collections.emptyList();
		}
	}
	public  WebElement waitForElementvisibleUsingFluentFeatures(By locator, long timeout, long pollingTime) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeout))
				                     .pollingEvery(Duration.ofSeconds(pollingTime)).ignoring(NoSuchElementException.class)
				                       .ignoring(StaleElementReferenceException.class).withMessage("========element is not found====");
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		
	}
	@Step("Wait for Title is:{0) and 	within Timeout: {1}")
	// To Get Page title with Wait Implicit 
	public   String waitForTitleContains(String fractionTitle, long timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		/*
		If Page tile doesn't get within timeout it will throw an Tiemout exception.
		Also It doesn't goes to else part since it throws a exp
		To Handle this, we need to use Try and Catch Block. 
		titleContains()- We can Enter Partial text to check title present or not.
		*/
		try {
		if(wait.until(ExpectedConditions.titleContains(fractionTitle))) {
			return driver.getTitle();
		}
		}catch(TimeoutException e) {
			System.out.println("Title not found after "+timeout+" seconds" );
			return null;
		}
		return null;
		
	}
	public   String waitForTitleIs(String title, long timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		/*
		If Page tile doesn't get within timeout it will throw an Tiemout exception.
		Also It doesn't goes to else part since it throws a exp
		To Handle this, we need to use Try and Catch Block. 
		titleIs();-> We need to enter full title to check title present or not.
		*/
		try {
		if(wait.until(ExpectedConditions.titleIs(title))) {
			return driver.getTitle();
		}
		}catch(TimeoutException e) {
			System.out.println("Title not found after "+timeout+" seconds" );
			return null;
		}
		return null;
		
	}
	@Step("Wait for URL contian: {0} and within Timeout{1}")
	// To Get Current URL with wait implicit. 	
	public  String  waitForURLcontains(String fractionURL, long timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		/*
		If Page tile doesn't get within timeout it will throw an Tiemout exception.
		Also It doesn't goes to else part since it throws a exp
		To Handle this, we need to use Try and Catch Block. 
		urlContains()- We can Enter Partial URL to check title present or not.
		*/
		try {
		if(wait.until(ExpectedConditions.urlContains(fractionURL))) {
			return driver.getCurrentUrl();
		}	
		}catch(TimeoutException e){
			System.out.println("URL not found after "+timeout+" seconds" );
		}
		return null;
	}
	public  String  waitForURLToBe(String URL, long timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		/*
		If Page tile doesn't get within timeout it will throw an Tiemout exception.
		Also It doesn't goes to else part since it throws a exp
		To Handle this, we need to use Try and Catch Block. 
		urlToBe()- We need to  Enter full  URL to check title present or not.
		*/
		try {
		if(wait.until(ExpectedConditions.urlToBe(URL))) {
			return driver.getCurrentUrl();
		}	
		}catch(TimeoutException e){
			System.out.println("URL not found after "+timeout+" seconds" );
		}
		return null;
	}
	
	/*  Alert wait - Implicit
	 * We Don't need to Change driver to alert popup.
	 * It will automatically switch driver to alert popup.
	 * Once Popup accept or dismiss, driver will automatically switch to main page.
	 */
	public  Alert waitForAlert(long timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.alertIsPresent());
		
	}
	public  String getAlertText(long timeout) {
		return waitForAlert(timeout).getText();
		
	}
	public  void acceptAlert(long timeout) {
		 waitForAlert(timeout).accept();
		
	}
	public  void dismissAlert(long timeout) {
		 waitForAlert(timeout).dismiss();
		
	}
	public  void alertSendkeys(String text, long timeout) {
		 waitForAlert(timeout).sendKeys(text);
		
	}
	/* Frame Wait - Implicit wait
	 * We Don't need to Change driver to alert popup.
	 * It will automatically switch driver to Frame .
	 * Once Frame  wrok done, we need to switch our driver to main page to access content in mai  page using driver.switchTo().defaultContent();
	 *  
	 */
	public  void waitForFrameByLocaotrAndSwitchToIt(By locator, long timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
		
	}
	public  void waitForFrameByIndexAndSwitchToIt(int frameIndex, long timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
		
	}

	public  void waitForFrameByStringAndSwitchToIt(String frameIDOrName, long timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIDOrName));
		
	}

	public  void waitForFrameByWebElementAndSwitchToIt(WebElement frameElement, long timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
		
	}
	//window wait - Implicit wait. 
	public  Boolean waitForWindow(int numberOfWindows, long timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		//Incase window not apper within timeout it throw an error then we need to catch the execptions.
		try {
		return wait.until(ExpectedConditions.numberOfWindowsToBe(2));
		}catch(TimeoutException e) {
			return false;
		}
	}
	//Page loading - Implicit wait
	public  boolean isPageLoaded(long timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		String wa = wait.until(ExpectedConditions.jsReturnsValue("return document.readyState=='complete'")).toString();
		//Change string type to boolean. To Check if statement.
		return Boolean.parseBoolean(wa);
	}


}
