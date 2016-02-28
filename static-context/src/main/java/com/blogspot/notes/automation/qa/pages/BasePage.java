package com.blogspot.notes.automation.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.blogspot.notes.automation.qa.core.StaticWebDriverRunner.getDriver;

public class BasePage {

	private WebDriver driver;
	private WebDriverWait wait;

	private static final long VISIBILITY_TIMEOUT = 5;

	public BasePage() {
		this.driver = getDriver();

		if (driver != null) {
			wait = new WebDriverWait(driver, VISIBILITY_TIMEOUT);
		}
	}

	public WebElement findElement(final By locator) {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public List<WebElement> findElements(final By locator) {
		return driver.findElements(locator);
	}

	public int waitForItemsAppearance(final By locator) {
		final AtomicInteger size = new AtomicInteger(0);
		wait.until((WebDriver driver) -> {
			final int collectionSize = findElements(locator).size();
			if (collectionSize > 0) {
				size.set(collectionSize);
			}
			return collectionSize > 0;
		});
		return size.get();
	}

	public void setText(final By locator, final CharSequence text) {
		findElement(locator).sendKeys(text);
	}

	public String getValue(final By locator) {
		return findElement(locator).getAttribute("value");
	}
}
