package pages.mainHeader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import pages.BasePage;

public class  MainHeader extends BasePage {
    private final Logger log = LogManager.getLogger(MainHeader.class);
    private final By logo = By.id("btn_logo");
    private final By searchBar = By.id("search-input");
    private final By cart = By.xpath("//a[@title='Cart']");
    private final By cartBadge = By.xpath("//span[@data-qa='btn_cart_count']");
    private final String baseCategoryXpath = "//li[@data-qa='btn_main_menu_";

    public MainHeader(WebDriver driver) {
        super(driver);
    }

    private WebElement getLogo() {
        log.info("getting noon logo");
        return findElement(logo);
    }

    private WebElement getSearchBar() {
        log.info("getting the search bar");
        return findElement(searchBar);
    }

    private WebElement getCart() {
        log.info("getting the cart icon");
        return findElement(cart);
    }

    private WebElement getCartBadge() {
        log.info("getting the cart badge");
        return findElement(cartBadge);
    }

    private WebElement getCategory(String cat) {
        log.debug("getting category: {}",cat);
        return findElement(By.xpath(baseCategoryXpath+cat+"']"));
    }
    public int getNumberOfItemsInCart(){
        log.info("getting number of items in cart");
        int x = Integer.parseInt(getCartBadge().getText());
        log.debug("found {} items",x);
        return x;
    }
    public void enterSearchQuery(String query){
        log.info("clearing any old searches");
        clearSearchBar();
        log.debug("entering {} in the search bar",query);
        getSearchBar().sendKeys(query);
        new Actions(getDriver()).keyDown(Keys.ENTER).keyUp(Keys.ENTER).perform();
        log.info("clicked enter");
    }
    private void clearSearchBar(){
        log.info("clicking the search bar");
        getSearchBar().click();
        log.info("performing the clear action");
        new Actions(getDriver())
                .keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL)
                .keyDown(Keys.BACK_SPACE).keyUp(Keys.BACK_SPACE)
                .perform();
        log.info("search bar cleared");
    }
    public void clickOnCategory(String cat){
        getCategory(cat).click();
        log.info("clicked category: {}",cat);
        moveToTop();
    }
    public void openCart(){
        getCart().click();
        log.info("clicked on the cart");
    }
    private void moveToTop(){
        log.info("moving mouse away");
        new Actions(getDriver())
                .moveToElement(getLogo())
                .perform();
    }
}
