package driverFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

public class GetEdgeDriver {
    private static WebDriver driver = null;
    private static Logger log = LogManager.getLogger(GetEdgeDriver.class);
    public static WebDriver getEdgeDriver(){
        if (driver==null){
            log.info("initializing edge");
            EdgeOptions edgeOptions = new EdgeOptions();
            edgeOptions.addArguments("--incognito");
            driver = new EdgeDriver(edgeOptions);
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
