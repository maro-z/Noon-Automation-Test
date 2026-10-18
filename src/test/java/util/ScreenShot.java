package util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;

public class ScreenShot {
    private static Logger log = LogManager.getLogger(ScreenShot.class);
    public static File takeScreenShot(WebDriver driver){
        try {
            log.info("trying to take a screen shot using driver: {}",driver);
            File image = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            return image;
        }catch (Exception e){
            log.error("error taking a screenshot: {}",e);
        }
        return null;
    }
}
