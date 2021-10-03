package util;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import pages.BasePage;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class ScreenshotMaker extends BasePage {

    String fileName = new Date().toString().replace(":", "_").replace(" ", "_");

    public void captureScreenShot() {
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(src, new File("target/screenshots/failed_" + fileName + ".png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}