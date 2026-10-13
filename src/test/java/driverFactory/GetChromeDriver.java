package driverFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class GetChromeDriver {
    private static WebDriver driver = null;
    private static Logger log = LogManager.getLogger(GetChromeDriver.class);
    public static WebDriver getChromeDriver(){
        if (driver==null){
            log.info("initializing chrome");
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--incognito");
            driver = new ChromeDriver(chromeOptions);
        }
        return driver;
    }
    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
