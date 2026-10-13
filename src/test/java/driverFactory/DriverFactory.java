package driverFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class DriverFactory {
    public static WebDriver driver;
    private static Logger log = LogManager.getLogger(DriverFactory.class);
    public static WebDriver getWebDriver(String browserName){
        switch (browserName.toLowerCase().trim()){
            case "chrome":
                log.info("chrome browser selected");
                driver=GetChromeDriver.getChromeDriver();
                break;
            case "firefox":
                log.info("firefox browser selected");
                driver=GetFireFoxDriver.getFireFoxDriver();
                break;
            case "edge":
                log.info("edge browser selected");
                driver=GetEdgeDriver.getEdgeDriver();
                break;
            default:
                log.warn("Invalid browser name : {}",browserName);
                throw new IllegalArgumentException("Invalid browser name: "+browserName);
        }
        return driver;
    }
    public static void quitDriver(String browser) {

        switch (browser.toLowerCase().trim()) {
            case "chrome":
                log.info("quitting chrome browser");
                GetChromeDriver.quitDriver();
                break;
            case "edge":
                log.info("quitting edge browser");
                GetEdgeDriver.quitDriver();
                break;
            case "firefox":
                log.info("quitting firefox browser");
                GetFireFoxDriver.quitDriver();
                break;
            default:
                log.warn("Invalid browser name : {}",browser);
                throw new IllegalArgumentException(
                        "Unsupported browser: " + browser
                );
        }
    }
}
