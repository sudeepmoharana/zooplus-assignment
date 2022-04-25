package de.zooplus.pageobjects;

import de.zooplus.framework.common.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

public class CheckoutOverviewPage extends BasePage {
    private static final Logger log = LogManager.getLogger(CheckoutOverviewPage.class);

    private By productRow = By.cssSelector("div.cart__table__row.two__column");
    private By recommendationCardAddToCartButton = By.cssSelector("button.reco-addToCart");
    private By priceContainer = By.xpath("//*[@role='row']/*[@class='value two__column']");
    private By quantityInputField = By.xpath("//*[@class='articleQtyForm']/div//*[@name='articleCount']");
    private By increaseQuantityButton = By.xpath("//*[@data-zta='increaseQuantityBtn']");
    private By removeArticleButton = By.xpath("//*[@data-zta='removeArticleBtn']");
    private By subTotalContainer = By.xpath("//*[@data-zta='overviewSubTotalValue']");
    private By shippingValueContainer = By.xpath("//*[@data-zta='shippingCostValueOverview']");
    private By totalValueContainer = By.xpath("//*[@data-zta='total__price__value']");
    private By shippingCountryContainer = By.xpath("//*[@data-zta='shippingCountryName']");
    private By shippingCountrySelector = By.xpath("//*[@data-zta='shippingCostDropdown']/button");
    private By postCodeField = By.xpath("//input[@data-zta='shippingCostZipcode']");
    private By updateButton = By.xpath("//button[@data-zta='shippingCostPopoverAction']");
    private By portugalOption = By.xpath("//*[@data-label='Portugal']");

    Map<Integer, Double> sortedMap = null;

    public CheckoutOverviewPage(WebDriver driver) {
        super(driver);
    }

    public boolean isCheckoutOverviewPageLoaded() {
        try {
            waitForElementsToBeVisible(productRow);
            return true;
        } catch (Exception e) {
            log.error("Checkout overview page did not load");
            return false;
        }
    }

    public boolean doesUrlContainKeyword() {
        if (isCheckoutOverviewPageLoaded()) {
            String currentUrl = driver.getCurrentUrl();
            log.info("Current url is: {}", currentUrl);
            return currentUrl.contains("overview");
        } else {
            log.error("Current url is: {}", driver.getCurrentUrl());
            return false;
        }
    }

    public void addProductsFromRecommendation(int noOfProducts) {
        waitForElementsToBeVisible(recommendationCardAddToCartButton);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        for (int i = 0; i < noOfProducts; i++) {
            String script = String.format("%s%s%s", "document.getElementsByClassName('reco-addToCart')[", i, "].click();");
            js.executeScript(script);
            waitForNumberOfElementsToBeMoreThan(productRow, i + 1);
            waitForElementsToBeVisible(recommendationCardAddToCartButton);
        }
    }

    public void incrementLowestPricedArticleQuantityBy(int n) {
        Double lowestPrice = 0.0;
        for (Map.Entry<Integer, Double> entry : sortedMap.entrySet()) {
            lowestPrice = entry.getValue();
        }
        Double finalLowestPrice = lowestPrice;
        List<Integer> keysForLowestPrice = sortedMap.entrySet().stream()
                .filter(entry -> entry.getValue().equals(finalLowestPrice))
                .map(Map.Entry::getKey).collect(Collectors.toList());
        List<WebElement> increaseQuantityButtons = driver.findElements(increaseQuantityButton);
        for(int i = 0; i < n; i++) {
            keysForLowestPrice.stream().forEach(x -> {
                List<WebElement> quantityInputFields = driver.findElements(quantityInputField);
                Integer currentQuantity = Integer.valueOf(quantityInputFields.get(x).getAttribute("value"));
                increaseQuantityButtons.get(x).click();
                waitForPageLoad();
                waitForElementToHaveValue(quantityInputFields.get(x), "value", String.valueOf(currentQuantity+1));
            });
        }
    }

    public void removeHighestPricedArticle() {
        int productIndexToBeDeleted = sortedMap.entrySet().iterator().next().getKey();
        List<WebElement> removeArticleButtons = driver.findElements(removeArticleButton);
        removeArticleButtons.get(productIndexToBeDeleted).click();
        waitForNumberOfElementsToBeLessThan(productRow, removeArticleButtons.size());
    }

    public void sortPrices() {
        Map<Integer, Double> priceMap = buildPriceMap();
        log.info("Prices of items before sorting {}", priceMap.values());
        sortedMap =
                priceMap.entrySet().stream()
                        .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                (e1, e2) -> e1, LinkedHashMap::new));
        log.info("Prices after sorting {}", sortedMap.values());
    }

    private Map<Integer, Double> buildPriceMap() {
        Map<Integer, Double> priceMap = new HashMap<>();
        List<WebElement> priceContainers = driver.findElements(priceContainer);
        int i = 0;
        for (WebElement item : priceContainers) {
            String price = item.getText();
            priceMap.put(i, Double.valueOf(price.substring(1)));
            i++;
        }
        return priceMap;
    }

    public Double calculateExpectedSubTotal() {
        Double expectedSubTotal = 0.00;

        List<WebElement> priceContainers = driver.findElements(priceContainer);
        for (WebElement item: priceContainers) {
            String price = item.getText().trim().substring(1);
            expectedSubTotal += Double.parseDouble(price);
        }
        BigDecimal bd = new BigDecimal(expectedSubTotal).setScale(2, RoundingMode.HALF_UP);
        expectedSubTotal = bd.doubleValue();
        return expectedSubTotal;
    }

    public Double getSubTotalValue() {
        String subTotal = driver.findElement(subTotalContainer).getText();
        return Double.parseDouble(subTotal.substring(1));
    }

    public Double calculateExpectedTotal() {
        double expectedTotal = calculateExpectedSubTotal() +
                Double.parseDouble(driver.findElement(shippingValueContainer).getText().trim().substring(1));
        BigDecimal bd = new BigDecimal(expectedTotal).setScale(2, RoundingMode.HALF_UP);
        expectedTotal = bd.doubleValue();
        return expectedTotal;
    }

    public Double getTotalValue() {
        return Double.valueOf(driver.findElement(totalValueContainer).getText().trim().substring(1));
    }

    public void changeShippingCountry(String country, String postCode) {
        driver.findElement(shippingCountryContainer).click();
        clickElementWhenClickable(shippingCountrySelector);
        String countryOptionXpath = String.format("%s%s%s","//*[@data-label='", country, "']");
        clickElementWhenClickable(By.xpath(countryOptionXpath));
        driver.findElement(postCodeField).sendKeys(postCode);
        clickElementWhenClickable(updateButton);
    }
}
