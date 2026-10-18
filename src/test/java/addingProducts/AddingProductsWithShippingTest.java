package addingProducts;

import baseTest.BaseTest;
import dataProvider.DataProviderTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.cart.CartPage;
import pages.home.HomePage;
import pages.mainHeader.MainHeader;
import pages.search.SearchPage;

import java.util.ArrayList;
import java.util.List;

public class AddingProductsWithShippingTest extends BaseTest {
    private final Logger log = LogManager.getLogger(AddingProductsWithShippingTest.class);
    @Test(dataProvider = "shoppingListWithShipping",dataProviderClass = DataProviderTest .class)
    public void secondFlow(List<String> shoppingList,String budget){
        log.debug("starting the second flow test with {} as the shopping list and budget:{}",shoppingList,budget);
        log.info("initializing all the needed pages");
        MainHeader mainHeader = new MainHeader(driver);
        HomePage homePage = new HomePage(driver);
        SearchPage searchPage = new SearchPage(driver);
        CartPage cartPage = new CartPage(driver);
        List<Integer> budgets = new ArrayList<>();
        int fullBudget = Integer.parseInt(budget);
        log.info("dividing the budget over the list");
        for(int i =0;i<shoppingList.size()-1;i++){
            budgets.add(i,Math.round(fullBudget/(float)2.0));
            fullBudget -= budgets.get(i);
            log.debug("set:{} pounds for element number {}",budgets.get(i),i+1);
        }
        log.debug("adding the remaining '{}' for the last element",fullBudget);
        budgets.add(fullBudget);
        softAssert.assertTrue(budgets.size()==shoppingList.size(),"error in dividing the budget");
        log.info("waiting for page to load");
        Assert.assertTrue(homePage.getMainPageContent().isDisplayed());
        log.info("adding the items to cart");
        List<String>addedItems = searchPage.addToCart(shoppingList,budgets,false);
        softAssert.assertTrue(addedItems.size()==shoppingList.size(),"shopping list and added items size don't match");
        softAssert.assertTrue(mainHeader.getNumberOfItemsInCart()==addedItems.size(),"cart badge number doesn't match the added number");
        softAssert.assertAll();
        log.info("all checks passed in the results page");
        log.info("starting checks in the cart page");
        log.info("opening cart");
        mainHeader.openCart();
        Assert.assertTrue(cartPage.getTotalElement().isDisplayed(),"failed to open cart");
        log.info("making sure that all our added items are in cart");
        softAssert.assertTrue(cartPage.ensureAllAddedProductsAreInCart(addedItems),"items in cart don't match added products");
        log.info("making sure only one of each element is added");
        softAssert.assertTrue(cartPage.ensureOnlyOneItemForEachProduct(),"found more than one product for some items");
        log.info("making sure cart's calculations are correct");
        softAssert.assertTrue(cartPage.getCalculatedSubTotal()==cartPage.getSubTotal(),"added items total and calculated total doesn't match");
        softAssert.assertTrue((cartPage.getSubTotal()+cartPage.getShippingFee())==cartPage.getTotal(),"subtotal+shipping doesn't match calculated total");
        log.info("making sure that total is within budget");
        softAssert.assertTrue(cartPage.getTotal() <= Double.parseDouble(budget) && (Double.parseDouble(budget)-Double.parseDouble(budget)*0.05) <= cartPage.getTotal());
        softAssert.assertAll();
        log.info("flow two finished");
    }
}
