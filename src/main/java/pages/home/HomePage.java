package pages.home;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.BasePage;
import pages.mainHeader.MainHeader;

public class HomePage extends BasePage {
    private final Logger log = LogManager.getLogger(HomePage.class);
    public HomePage(WebDriver driver) {
        super(driver);
    }
    private final By mainContent = By.id("noon-modules");
    public WebElement getMainPageContent(){
        log.info("loading main page");
        return findElement(mainContent);
    }
}
