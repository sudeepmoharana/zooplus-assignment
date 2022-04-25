package de.zooplus.carttests;

import de.zooplus.BaseTest;
import de.zooplus.data.PostalData;
import de.zooplus.pageobjects.CartPage;
import de.zooplus.pageobjects.CheckoutOverviewPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CartTest extends BaseTest {
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
        checkoutOverviewPage.changeShippingCountry(PostalData.PORTUGAL, PostalData.PORTUGAL_POSTCODE);
        Assert.assertEquals(checkoutOverviewPage.getSubTotalValue(), checkoutOverviewPage.calculateExpectedSubTotal());
        Assert.assertEquals(checkoutOverviewPage.getTotalValue(), checkoutOverviewPage.calculateExpectedTotal());
    }
}
