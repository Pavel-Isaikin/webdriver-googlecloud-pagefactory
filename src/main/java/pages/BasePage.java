package pages;

import driver.DriverOrigin;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static util.CommonConstants.TIMEOUT;

public abstract class BasePage {

    protected WebDriver driver;
    protected JavascriptExecutor js;
    protected static List<String> tabs;
    protected static String newMail;

    public BasePage() {
        try {
            driver = DriverOrigin.getDriverOrigin().getDriver();
            js = (JavascriptExecutor) driver;
        } catch (Exception e) {
            System.out.println("Driver issue!");
        }
    }

    public void captureScreenShot() {
        String fileName = new Date().toString().replace(":", "_").replace(" ", "_");
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(src, new File("target/screenshots/failed_" + fileName + ".png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void openNewTab(String URL) {
        js.executeScript(String.format("window.open('%s', '_blank');", URL));
    }

    public BasePage scrollToView(WebElement element) {
        (js).executeScript("arguments[0].scrollIntoView(true);", element);
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this;
    }

    public BasePage clickTo(WebElement element) {
        WebElement clicker = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT))
                .until(ExpectedConditions.elementToBeClickable(element));
        clicker.click();
        return this;
    }

    public void pressEnter(WebElement element) {
        WebElement clicker = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT))
                .until(ExpectedConditions.elementToBeClickable(element));
        clicker.sendKeys(Keys.ENTER);
    }

    public BasePage fillIn(WebElement element, String textToFillIn) {
        WebElement typeIn = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT))
                .until(ExpectedConditions.visibilityOf(element));
        clickTo(element);
        typeIn.sendKeys(textToFillIn);
        return this;
    }

    public WebElement getShadowEle(WebElement element) {
        return (WebElement) (js.executeScript("return arguments[0].shadowRoot", element));
    }

    public void shadowClick(WebElement baseEle, String shadowCSS) {
        //find base element
        //get shadow root element under base one
        WebElement shadowArea = getShadowEle(baseEle);
        //find shadowed element by CSS and click
        WebElement shadowEle = shadowArea.findElement(By.cssSelector(shadowCSS));
        shadowEle.click();
    }

}
