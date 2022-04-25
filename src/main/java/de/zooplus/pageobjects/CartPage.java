package de.zooplus.pageobjects;

import de.zooplus.framework.common.BasePage;
import de.zooplus.framework.config.ConfigPicker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;


public class CartPage extends BasePage {
    private static final Logger log = LogManager.getLogger(CartPage.class);
    private static final String CART_PAGE_ENDPOINT = "/checkout/cartEmpty.htm";

    private By recommendationCardAddToCartButton = By.cssSelector("button.reco-addToCart");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        String url = String.format("%s%s",
                ConfigPicker.getInstance().getConfiguration().get("baseUrl"), CART_PAGE_ENDPOINT);
        log.info("Navigating to url: {}", url);
        driver.get(url);
        super.handleConsentPopup(true);
    }

    public CheckoutOverviewPage addAProductFromRecommendation() {
        waitForElementsToBeVisible(recommendationCardAddToCartButton);
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("document.getElementsByClassName('reco-addToCart')[0].click();");
        return new CheckoutOverviewPage(driver);
    }
}
