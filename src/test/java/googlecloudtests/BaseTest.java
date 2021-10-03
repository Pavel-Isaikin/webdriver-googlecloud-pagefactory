package googlecloudtests;

import driver.DriverOrigin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import pages.BasePage;
import util.ScreenshotMaker;

public class BaseTest extends BasePage {

    private final Logger LOGGER = LogManager.getLogger();
    ScreenshotMaker sm = new ScreenshotMaker();


    @BeforeSuite(alwaysRun = true)
    public void onStartLog() {
        LOGGER.info("- - -TEST STARTED- - -");
    }

    @AfterMethod
    public void failedTeardown(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            sm.captureScreenShot();
        }
        DriverOrigin.getDriverOrigin().killDriver();
        LOGGER.info("- - - -TEST FAILED- - - -\n\n");
    }

    @AfterSuite(alwaysRun = true)
    public void teardown() {
        DriverOrigin.getDriverOrigin().killDriver();
        LOGGER.info("- - - -TEST ENDED- - - -\n\n");
    }
}
