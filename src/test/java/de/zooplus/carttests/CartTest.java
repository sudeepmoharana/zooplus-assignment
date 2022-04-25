package de.zooplus.carttests;

import de.zooplus.framework.config.WebDriverProvider;
import de.zooplus.pageobjects.CartPage;
import de.zooplus.pageobjects.CheckoutOverviewPage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CartTest {
    private WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        driver = WebDriverProvider.getDriver("chrome", true);
        driver.manage().deleteAllCookies();
    }

    @Test()
    public void testCart() {
        CartPage cartPage = new CartPage(driver);
        cartPage.open();
        CheckoutOverviewPage checkoutOverviewPage = cartPage.addAProductFromRecommendation();
        Assert.assertTrue(checkoutOverviewPage.doesUrlContainKeyword(), "Validating if the url contains 'overview'");
        checkoutOverviewPage.addProductsFromRecommendation(3);
        checkoutOverviewPage.sortPrices();
        checkoutOverviewPage.incrementLowestPricedArticleQuantityBy(1);
        checkoutOverviewPage.removeHighestPricedArticle();
        Assert.assertEquals(checkoutOverviewPage.getSubTotalValue(), checkoutOverviewPage.calculateExpectedSubTotal());
        Assert.assertEquals(checkoutOverviewPage.getTotalValue(), checkoutOverviewPage.calculateExpectedTotal());
        checkoutOverviewPage.changeShippingCountry("Portugal", "5000");
        Assert.assertEquals(checkoutOverviewPage.getSubTotalValue(), checkoutOverviewPage.calculateExpectedSubTotal());
        Assert.assertEquals(checkoutOverviewPage.getTotalValue(), checkoutOverviewPage.calculateExpectedTotal());
    }

    @AfterTest()
    public void tearDown() {
        driver.close();
        driver.quit();
    }
}
