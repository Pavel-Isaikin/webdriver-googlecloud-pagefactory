package driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DriverOrigin {
    private static DriverOrigin driverOrigin = null;

    private final WebDriver driver;


    private DriverOrigin() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
    }

    public static DriverOrigin getDriverOrigin() {
        if (driverOrigin == null) {
            driverOrigin = new DriverOrigin();
        }
        return driverOrigin;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void killDriver() {
        driver.quit();
    }
}
