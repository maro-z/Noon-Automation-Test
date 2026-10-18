package pages.search;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.WheelInput;
import pages.BasePage;
import pages.mainHeader.MainHeader;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class SearchPage extends BasePage {
    private final Logger log = LogManager.getLogger(SearchPage.class);
    private final MainHeader mainHeader = new MainHeader(getDriver());
    public SearchPage(WebDriver driver) {
        super(driver);
    }
    private final By headerTitle = By.xpath("//div[@data-qa='searchHeader']//div[contains(@class,'title')]//h1");
    private final By priceTab = By.xpath("//div[@data-qa='Price']");
    private final By minimumPriceFilter = By.xpath("//input[@data-qa='filter-min-input']");
    private final By maximumPriceFilter = By.xpath("//input[@data-qa='filter-max-input']");
    private final By submitPriceFilter = By.xpath("//button[@type='submit']");
    private final By ratingTab = By.xpath("//div[@data-qa='Product Rating']");
    private final By ratesSlider = By.xpath("//input[@type='range']");
    private final By productBox = By.xpath("//div[@data-qa='plp-product-box']");
    private final By productNudges = By.xpath(".//div[contains(@class,'nudgesWrap')]//div[contains(@class,'Text')]");
    private final By addToCartButton = By.xpath(".//img[@alt='add-to-cart']");
    private final By productPrice = By.tagName("strong");
    private final By productRate = By.xpath(".//div[contains(@class,'text')]");
    private final By productTitle = By.xpath(".//h2[@data-qa='plp-product-box-name']");
    private final By topLoaderLine = By.xpath("//div[contains(@class,'topLineLoader')]");
    private final By noResultsMessage = By.tagName("h3");
    private final By sortBy = By.xpath("//div[contains(@class,'dropdown')]");
    private final By sortChoice = By.xpath("//li[contains(@class,'option')]//a");

    private WebElement getHeaderTitle() {
        log.info("getting header title");
        return findElement(headerTitle);
    }

    private WebElement getPriceTab(){
        log.info("getting price limits tab");
        return findElement(priceTab);
    }

    private WebElement getMinimumPriceFilter() {
        log.info("getting minimum price field");
        return findElement(minimumPriceFilter);
    }

    private WebElement getMaximumPriceFilter() {
        log.info("getting maximum price field");
        return findElement(maximumPriceFilter);
    }

    private WebElement getSubmitPriceFilter() {
        log.info("getting the go button");
        return findElement(submitPriceFilter);
    }

    private WebElement getRatingTab(){
        log.info("getting the product rating tab");
        return findElement(ratingTab);
    }

    private WebElement getRatesSlider() {
        log.info("getting the slider for product ratings");
        return findElement(ratesSlider);
    }

    private List<WebElement> getProductBoxes() {
        log.info("getting products");
        return findElements(productBox);
    }
    public WebElement getNoResultsMessage(){
        log.info("getting no results message");
        return findElement(noResultsMessage);
    }

    private WebElement getSortBy() {
        log.info("getting the sort by filter");
        return findElement(sortBy);
    }

    private List<WebElement> getSortChoices() {
        log.info("getting the choices for the sort by");
        return findElements(sortChoice);
    }

    public String getHeaderText(){
        log.info("getting header's text");
        String temp = getHeaderTitle().getText();
        log.debug("header's text: {}",temp);
        return temp;
    }

    public void setMinPriceByTyping(int price){
        WebElement min = getMinimumPriceFilter();
        log.info("clearing field");
        min.clear();
        log.debug("setting price: {} as minimum",price);
        min.sendKeys(Integer.toString(price));
    }
    public void setMaxPriceByTyping(int price){
        WebElement max = getMaximumPriceFilter();
        log.info("clearing field");
        max.clear();
        log.debug("setting price: {} as maximum",price);
        getMaximumPriceFilter().sendKeys(Integer.toString(price));
    }
    public void setMinPriceByScrolling(int price){
        log.debug("scrolling on minimum till: {}",price);
        WebElement min = getMinimumPriceFilter();
        min.click();
        int initialNumber =Integer.parseInt(min.getAttribute("value"));
        log.debug("initial value in minimum field: {}",initialNumber);
        int numberOfScrolls = price-initialNumber;
        log.debug("number of scrolls needed:{}",numberOfScrolls);
        log.info("initializing wheel input");
        WheelInput.ScrollOrigin scrollOrigin = WheelInput.ScrollOrigin.fromElement(min);
        log.info("initializing actions");
        Actions actions = new Actions(getDriver());
        for (int i=0;i<numberOfScrolls;i++){
            actions.scrollFromOrigin(scrollOrigin, 0, -1).perform();
        }
    }
    public void setMaxPriceByScrolling(int price){
        log.debug("scrolling on maximum till: {}",price);
        WebElement max = getMaximumPriceFilter();
        log.info("initializing it to zero for time saving");
        max.clear();
        max.sendKeys("0");
        max.click();
        log.info("initializing wheel input");
        WheelInput.ScrollOrigin scrollOrigin = WheelInput.ScrollOrigin.fromElement(max);
        log.info("initializing actions");
        Actions actions = new Actions(getDriver());
        for (int i=0;i<price;i++){
            actions.scrollFromOrigin(scrollOrigin, 0, -1).perform();
        }
    }
    public void setSortingFilter(String target){
        log.debug("setting filter to: {}",target);
        List<WebElement> options = getSortChoices();
        if (options.isEmpty()){
            log.warn("sorting options are not displayed so no choices were made");
            return;
        }
        for (WebElement e: options){
            log.debug("option: {}",e.getText());
            if (e.getText().trim().toLowerCase().contains(target.trim().toLowerCase())){
                log.info("option matched request clicking on it now");
                e.click();
                return;
            }
        }
        log.warn("your requested option doesn't exist please trt again with a valid option");
    }

    public void moveRatingSlider(double targetRate) {
        WebElement slider = getRatesSlider();
        log.info("initializing js executor");
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        log.info("writing the script for slider movement");
        String script =
                "let slider = arguments[0];" +
                "let newValue = arguments[1];" +
                "let nativeSetter = Object.getOwnPropertyDescriptor(window.HTMLInputElement.prototype, 'value').set;" +
                "nativeSetter.call(slider, newValue);" +
                "slider.dispatchEvent(new Event('input', { bubbles: true }));";
        log.info("executing the script");
        js.executeScript(script, slider, targetRate);
        log.info("new rate set");
    }

    public List<WebElement> getProductsWithNudge(String target){
        List<WebElement> allProducts = getProductBoxes();
        log.info("initializing empty list for results");
        List<WebElement> result = new ArrayList<>();
        for (WebElement e : allProducts){
            List<WebElement> nudges = e.findElements(productNudges);
            for (WebElement nudge:nudges){
                if (nudge.getText().trim().equalsIgnoreCase(target.trim())){
                    log.debug("found product: {} that is: {}",e.findElement(productTitle).getAttribute("title"),target);
                    result.add(e);
                    break;
                }
            }
        }
        if (result.isEmpty())
            log.warn("didn't find any products matching: {}",target);
        else
            log.debug("found {} products matching: {}",result.size(),target);
        return result;
    }
    public boolean checkAllProductsPriceRange(double min,double max){
        log.info("starting the price check");
        List<WebElement> allProducts = getProductBoxes();
        for (WebElement e: allProducts){
            log.debug("getting product: {} price",e.findElement(productTitle).getAttribute("title"));
            double price = Double.parseDouble(e.findElement(productPrice).getText().replaceAll(",", ""));
            if (price<min || price>max){
                log.error("found product {} with price {} that is out of range",e.findElement(productTitle).getAttribute("title"),price);
                return false;
            }
        }
        log.info("all products are in range");
        return true;
    }

    public boolean checkAllProductsRating(double min){
        log.info("starting the rating check");
        List<WebElement> allProducts = getProductBoxes();
        for (WebElement e: allProducts){
            log.debug("getting product: {} rate",e.findElement(productTitle).getAttribute("title"));
            if (Double.parseDouble(e.findElement(productRate).getText())<min){
                log.error("found product {} with rate {} that is less than the filter",e.findElement(productTitle).getAttribute("title"),e.findElement(productRate).getText());
                return false;
            }
        }
        log.info("all products are in range");
        return true;
    }

    public List<String> addToCart(List<String> shoppingList,List<Integer> budgets,boolean freeShipping){
        waitForLoad();
        log.debug("adding {} to cart following {} budgets",shoppingList,budgets);
        List<String> added = new ArrayList<>();
        int i = 0;
        for (String name : shoppingList){
            log.debug("searching for: {}",name);
            mainHeader.enterSearchQuery(name);
            waitForLoad();
            log.info("filtering the results to match the product's budget");
            clickPriceTab();
            setMaxPriceByTyping(budgets.get(i));
            setMinPriceByTyping(budgets.get(i)-Math.round(budgets.get(i)*(float)0.05));
            clickSubmitPriceFilter();
            waitForLoad();
            log.info("price filter submitted");
            log.info("sorting by price from high to low");
            clickSortBy();
            setSortingFilter("high to low");
            waitForLoad();
            log.info("sorting applied");
            List<WebElement> products = new ArrayList<>();
            log.info("getting the products");
            if (freeShipping)
                products = getProductsWithNudge("Free Delivery");
            else
                products = getProductBoxes();
            log.info("getting the first element");
            added.add(products.get(0).findElement(productTitle).getAttribute("title"));
            log.debug("adding: {} to cart",products.get(0).findElement(productTitle).getAttribute("title"));
            products.get(0).findElement(addToCartButton).click();
            waitForLoad();
            try {
                if (products.get(0).findElement(addToCartButton).isDisplayed())
                    log.info("product added successfully");
            }catch (org.openqa.selenium.NoSuchElementException e){
                log.error("product not added to cart");
            }
            finally {
                i++;
            }
        }
        return added;
    }

    public void clickPriceTab(){
        log.info("opening the price tab");
        getPriceTab().click();
    }
    public void clickSubmitPriceFilter(){
        log.info("clicking go");
        getSubmitPriceFilter().click();
    }
    public void clickRatingTab(){
        log.info("clicking ratingTab");
        getRatingTab().click();
    }
    public void clickSortBy(){
        log.info("clicking the sort by");
        getSortBy().click();
    }
    public void waitForLoad(){
        try {
            log.info("getting the top loader");
            findElement(topLoaderLine,Duration.ofSeconds(3));
            log.info("waiting for the top loader to disappear");
            waitTillInvisible(topLoaderLine);
        }catch (org.openqa.selenium.TimeoutException e){
            log.info("loaded too quickly didn't catch it proceeding with the rest of steps");
        }
    }
}
