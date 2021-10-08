package driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class DriverOrigin {

    private static DriverOrigin driverOrigin = null;

    private WebDriver driver;


    private DriverOrigin() {

//        File browserOptions = new File("src/main/resources/driverOption.properties");
//        FileInputStream inputStream = null;
//        try {
//            inputStream = new FileInputStream(browserOptions);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        Properties prop = new Properties();
//        try {
//            prop.load(inputStream);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        String browser = System.getProperty("browser");
        if (browser != null) {
            switch (browser) {
                case "chrome" -> {
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver();
                    System.out.println("USING CHROME");
                }
                case "firefox" -> {
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();
                    System.out.println("USING FIREFOX");
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
