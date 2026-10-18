package pages.category;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.BasePage;

import java.util.List;

public class CategoryPage extends BasePage {
    private final Logger log = LogManager.getLogger(CategoryPage.class);
    public CategoryPage(WebDriver driver) {
        super(driver);
    }
    private final By brand = By.xpath("//div[@data-qa='widget_bannerModuleStrip']//a");
    private final By electronicsImage = By.xpath("//img[@alt='/electronics-accessories/']");

    public WebElement getElectronicsImage(){
        return findElement(electronicsImage);
    }
    private WebElement getBrand(String brandName) {
        log.debug("getting button for brand: {}",brandName);
        List<WebElement> brands = findElements(brand);
        for(WebElement e : brands){
            if (e.getAttribute("href").toLowerCase().contains(brandName.trim().toLowerCase())){
                log.info("found brand button");
                return e;
            }
        }
        log.warn("brand not found");
        return null;
    }
    public void clickBrand(String brandName){
        try {
            getBrand(brandName).click();
            log.debug("clicked brand: {}",brandName);
        }catch (Exception e){
            log.error("caught error: {}",e);
        }
    }
}
