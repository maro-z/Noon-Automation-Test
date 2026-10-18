package filtersFlow;

import baseTest.BaseTest;
import dataProvider.DataProviderTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.category.CategoryPage;
import pages.home.HomePage;
import pages.mainHeader.MainHeader;
import pages.search.SearchPage;

public class FiltersFlowTest extends BaseTest {
    private final Logger log = LogManager.getLogger(FiltersFlowTest.class);
    @Test(dataProvider = "firstFlow",dataProviderClass = DataProviderTest.class)
    public void firstFlow(String category,String brand,String min,String max,String rate){
        log.debug("starting the first flow test with category:{}|brand:{}|min price:{}|max price:{}|rate:{}"
                            ,category,brand,min,max,rate);
        log.info("initializing all the needed pages");
        MainHeader mainHeader = new MainHeader(driver);
        HomePage homePage = new HomePage(driver);
        CategoryPage categoryPage = new CategoryPage(driver);
        SearchPage searchPage = new SearchPage(driver);
        log.info("waiting for page to load");
        Assert.assertTrue(homePage.getMainPageContent().isDisplayed());
        log.info("choosing the category");
        mainHeader.clickOnCategory(category);
        log.info("making sure we entered the correct page");
        Assert.assertTrue(categoryPage.getElectronicsImage().isDisplayed(),"you are not in the electronics category page");
        log.info("choosing the brand");
        categoryPage.clickBrand(brand);
        Assert.assertTrue(searchPage.getHeaderText().trim().toLowerCase().contains(brand),"the brand's choice failed");
        log.info("entering minimum and maximum prices");
        searchPage.clickPriceTab();
        searchPage.setMinPriceByTyping(Integer.parseInt(min));
        searchPage.setMaxPriceByTyping(Integer.parseInt(max));
        searchPage.clickSubmitPriceFilter();
        log.info("filter applied waiting for the page to load");
        searchPage.waitForLoad();
        softAssert.assertTrue(searchPage.checkAllProductsPriceRange(Integer.parseInt(min),Integer.parseInt(max)),"found products out of range");
        log.info("entering the minimum rate");
        searchPage.clickRatingTab();
        searchPage.moveRatingSlider(Double.parseDouble(rate));
        log.info("filter applied waiting for the page to load");
        searchPage.waitForLoad();
        softAssert.assertTrue(searchPage.checkAllProductsRating(Double.parseDouble(rate)),"found products that is below the rate");
        softAssert.assertAll();
        log.info("first flow finished");
    }

    @Test(dataProvider = "firstFlow",dataProviderClass = DataProviderTest.class)
    public void firstFlowWithScrollFilter(String category,String brand,String min,String max,String rate){
        log.debug("starting the first flow test with category:{}|brand:{}|min price:{}|max price:{}|rate:{} and setting the price by scroll"
                ,category,brand,min,max,rate);
        log.info("initializing all the needed pages");
        MainHeader mainHeader = new MainHeader(driver);
        HomePage homePage = new HomePage(driver);
        CategoryPage categoryPage = new CategoryPage(driver);
        SearchPage searchPage = new SearchPage(driver);
        log.info("waiting for page to load");
        Assert.assertTrue(homePage.getMainPageContent().isDisplayed());
        log.info("choosing the category");
        mainHeader.clickOnCategory(category);
        log.info("making sure we entered the correct page");
        Assert.assertTrue(categoryPage.getElectronicsImage().isDisplayed(),"you are not in the electronics category page");
        log.info("choosing the brand");
        categoryPage.clickBrand(brand);
        Assert.assertTrue(searchPage.getHeaderText().trim().toLowerCase().contains(brand),"the brand's choice failed");
        log.info("entering minimum and maximum prices");
        searchPage.clickPriceTab();
        searchPage.setMinPriceByScrolling(Integer.parseInt(min)/10);
        searchPage.setMaxPriceByScrolling(Integer.parseInt(max)/10);
        searchPage.clickSubmitPriceFilter();
        log.info("filter applied waiting for the page to load");
        searchPage.waitForLoad();
        softAssert.assertTrue(searchPage.checkAllProductsPriceRange(Double.parseDouble(min)/10,Double.parseDouble(max)/10),"found products out of range");
        log.info("entering the minimum rate");
        searchPage.clickRatingTab();
        searchPage.moveRatingSlider(Double.parseDouble(rate));
        log.info("filter applied waiting for the page to load");
        searchPage.waitForLoad();
        softAssert.assertTrue(searchPage.checkAllProductsRating(Double.parseDouble(rate)),"found products that is below the rate");
        softAssert.assertAll();
        log.info("first flow finished");
    }
}
