package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static util.CommonConstants.*;

public class GoogleCloudPage extends BasePage {

    private final Logger LOGGER = LogManager.getLogger();

    @FindBy()
    private WebElement gCloudPageTitle;

    @FindBy(xpath = "//*[@name='q']")
    private WebElement searchButton;

    public GoogleCloudPage() {
        PageFactory.initElements(driver, this);
    }

    public void goToHomePage() {
        driver.get(HOMEURL);
        LOGGER.info("Navigate to Google Cloud page");
    }

    public void goToSearchField() {
        clickTo(searchButton);
    }

    public void pasteSearchQuery() {
        fillIn(searchButton, TEXT_DEMANDED).pressEnter(searchButton);
        LOGGER.info("Search for Pricing Calculator page\n");
    }

    public boolean checkIfIsGoogleCloudPage() {
        String checker = driver.getTitle();
        return checker.contains("Cloud Computing, Hosting Services, and APIs  |  Google Cloud");
    }
}

