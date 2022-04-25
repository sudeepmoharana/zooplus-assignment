package de.zooplus.framework.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {
    private static final Logger log = LogManager.getLogger(BasePage.class);

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected int defaultElementWaitTimeout = 20;

    private By consentPopup = By.cssSelector("div#onetrust-banner-sdk>div.ot-sdk-container");
    private By acceptCookiesButton = By.id("onetrust-accept-btn-handler");
    private By rejectCookiesButton = By.id("onetrust-reject-all-handler");

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(this.driver, Duration.ofSeconds(defaultElementWaitTimeout));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
    }

    public void waitForPageLoad() {
        wait.until(webDriver -> "complete".equals(((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState")));
    }

    protected WebElement findElement(By by) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        return element;
    }

    protected void waitForElementsToBeVisible(By by) {
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(by, 0));
    }

    protected void clickElementWhenClickable(By by) {
        wait.until(ExpectedConditions.elementToBeClickable(by)).click();
    }

    protected void waitForNumberOfElementsToBeMoreThan(By by, int n) {
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(by, n));
    }

    protected void waitForNumberOfElementsToBeLessThan(By by, int n) {
        wait.until(ExpectedConditions.numberOfElementsToBeLessThan(by, n));
    }

    protected void waitForElementToHaveValue(WebElement element, String attribute, String text) {
        wait.until(ExpectedConditions.attributeToBe(element, attribute, text));
    }

    protected boolean isConsentPopupDisplayed() {
        try {
            findElement(consentPopup);
            return true;
        } catch (Exception e) {
            log.info("Consent pop up was not present in the page");
            return false;
        }
    }

    protected void handleConsentPopup(boolean acceptCookies) {
        if (!isConsentPopupDisplayed()) {
            return;
        }
        if (acceptCookies) {
            findElement(acceptCookiesButton).click();
        } else {
            findElement(rejectCookiesButton).click();
        }
    }
}
