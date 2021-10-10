package driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverOrigin {

    private static DriverOrigin driverOrigin = null;

    private WebDriver driver;
    private final Logger LOGGER = LogManager.getLogger();


    private DriverOrigin() {

        String browser = System.getProperty("browser");
        if (browser != null) {
            switch (browser) {
                case "chrome" -> {
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver();
                    LOGGER.info("USING CHROME");
                }
                case "firefox" -> {
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();
                    LOGGER.info("USING FIREFOX");
                }
                case "edge" -> {
                    WebDriverManager.edgedriver().setup();
                    driver = new EdgeDriver();
                    LOGGER.info("USING EDGE");
                }
            }
        } else {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            System.out.println("USING CHROME as default");
        }
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
        driver = null;
    }
}
