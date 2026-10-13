package driverFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class GetFireFoxDriver {
    private static WebDriver driver = null;
    private static Logger log = LogManager.getLogger(GetFireFoxDriver.class);
    public static WebDriver getFireFoxDriver(){
        if (driver==null){
            log.info("initializing firefox");
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            firefoxOptions.addArguments("--private");
            driver = new FirefoxDriver(firefoxOptions);
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
