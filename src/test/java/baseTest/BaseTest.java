package baseTest;

import driverFactory.DriverFactory;
import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;
import util.ScreenShot;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class BaseTest {
    private final Logger log = LogManager.getLogger(BaseTest.class);
    public static WebDriver driver;
    private static int completedTests = 0;
    public SoftAssert softAssert;
    @BeforeMethod
    public void setup(){
        log.info("Initializing Soft assert");
        softAssert = new SoftAssert();
        log.info("Getting Web driver");
        driver = DriverFactory.getWebDriver(System.getProperty("BROWSERNAME", "chrome"));
        log.info("Opening the window");
        driver.get("https://www.noon.com/egypt-en/");
        log.info("Maximizing the window");
        driver.manage().window().maximize();
    }
    @AfterMethod
    public void failedTestCase(ITestResult result) throws IOException {
        if (result.getStatus()==ITestResult.FAILURE){
            log.info("test case failed taking a screenshot");
            File image =ScreenShot.takeScreenShot(driver);
            Allure.addAttachment("Failure screenshot for TC: "+result.getMethod().getMethodName(),"image/png", FileUtils.openInputStream(image),"png");
        }
    }
    @AfterMethod
    public void tearDown(){
        log.info("Quitting the diver");
        DriverFactory.quitDriver(System.getProperty("BROWSERNAME", "chrome"));
        driver=null;
    }

    @AfterTest
    public void addLogToReport(ITestContext context) {
        completedTests++;
        int totalTests = context.getSuite().getXmlSuite().getTests().size();
        if (completedTests == totalTests) {
            log.info("Final test block completed. Attaching complete suite logs.");
            try {
                File logFile = new File("logs/application.log");
                if (logFile.exists()) {
                    Allure.addAttachment("Full Execution Logs", "text/plain", Files.newInputStream(logFile.toPath()), "txt");
                }
            } catch (Exception e) {
                log.error("Failed to attach logs to Allure: " + e.getMessage());
            }
        }
    }
}
