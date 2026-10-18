package invalidSearch;

import baseTest.BaseTest;
import dataProvider.DataProviderTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.home.HomePage;
import pages.mainHeader.MainHeader;
import pages.search.SearchPage;

public class InvalidSearchTest extends BaseTest {
    private final Logger log = LogManager.getLogger(InvalidSearchTest.class);
    @Test(dataProvider = "invalidSearch",dataProviderClass = DataProviderTest.class)
    public void fourthFlow(String query){
        log.debug("starting the fourth flow test with search query:{}",query);
        log.info("initializing all the needed pages");
        MainHeader mainHeader = new MainHeader(driver);
        HomePage homePage = new HomePage(driver);
        SearchPage searchPage = new SearchPage(driver);
        log.info("waiting for page to load");
        Assert.assertTrue(homePage.getMainPageContent().isDisplayed());
        log.info("sending the query");
        mainHeader.enterSearchQuery(query);
        searchPage.waitForLoad();
        log.info("making sure no results are shown");
        Assert.assertTrue(searchPage.getNoResultsMessage().getText().contains("error"),"results are shown for an invalid search");
        log.info("fourth flow finished");
    }
}
