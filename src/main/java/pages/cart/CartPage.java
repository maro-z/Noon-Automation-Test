package pages.cart;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.BasePage;

import java.util.List;

public class CartPage extends BasePage {
    private final Logger log = LogManager.getLogger(CartPage.class);
    public CartPage(WebDriver driver) {
        super(driver);
    }
    private final By product = By.xpath("//div[contains(@class,'mainRow')]");
    private final By productPrice = By.xpath(".//div[@data-qa='item-unit-price']//span[text()]");
    private final By productQuantity = By.xpath(".//input[@data-qa='item-quantity-input']");
    private final By productName = By.xpath(".//div[contains(@class,'title')]//semibold");
    private final By totalAmount = By.xpath("//div[contains(@class,'amount')]//b");

    public List<WebElement> getProducts() {
        log.info("getting all products in cart");
        return findElements(product);
    }

    private List<WebElement> getTotalAmounts() {
        return findElements(totalAmount);
    }
    public WebElement getTotalElement(){return getTotalAmounts().get(2);}
    public double getSubTotal(){
        log.info("getting the cart's subtotal");
        return Double.parseDouble(getTotalAmounts().get(0).getText().replaceAll("EGP", "").trim().replaceAll(",", ""));
    }
    public double getShippingFee(){
        log.info("getting the shipping fee");
        try {
            return Double.parseDouble(getTotalAmounts().get(1).getText().replaceAll("EGP", "").trim().replaceAll(",", ""));
        }catch (Exception e){
            log.info("shipping is FREE");
            return 0;
        }
    }
    public double getTotal(){
        log.info("getting the cart's total");
        return Double.parseDouble(getTotalAmounts().get(2).getText().replaceAll("EGP", "").trim().replaceAll(",", ""));
    }
    public double getCalculatedSubTotal(){
        log.info("calculating the subtotal from cart");
        List<WebElement> products = getProducts();
        log.info("initializing total to zero");
        double total = 0;
        for (WebElement e:products){
            log.debug("adding {} price",e.findElement(productName).getText());
            total += Double.parseDouble(e.findElement(productPrice).getText().replaceAll(",", ""));
            log.debug("current total: {}",total);
        }
        return total;
    }
    public boolean ensureOnlyOneItemForEachProduct(){
        log.info("making sure we added only one item for each product");
        List<WebElement> products = getProducts();
        log.info("starting the check");
        for (WebElement e:products){
            log.debug("checking {} amount",e.findElement(productName).getText());
            if(Integer.parseInt(e.findElement(productQuantity).getAttribute("value"))!=1){
                log.warn("item exceeded allowed amount");
                return false;
            }
        }
        log.info("all products have only one item in cart");
        return true;
    }
    public boolean ensureAllAddedProductsAreInCart(List<String> addedNames){
        log.info("making sure all the products that we added are in cart");
        List<WebElement> products = getProducts();
        log.info("starting the check");
        if (getProducts().size()!=addedNames.size()){
            log.warn("the amount of added items and items in cart don't match");
            return false;
        }
        for (WebElement e:products){
            int added = 0;
            for (String name:addedNames){
                log.debug("checking that {} is added",name);
                if(name.equals(e.findElement(productName).getText())){
                    added++;
                }
                log.info("currently in cart items:{}",added);
            }
        }
        log.info("all products are in cart");
        return true;
    }
}
