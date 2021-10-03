package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static util.CommonConstants.HOMEURL;
import static util.CommonConstants.TEXT_DEMANDED;

public class GoogleCloudPage extends BasePage {

    private final Logger LOGGER = LogManager.getLogger();

    @FindBy(xpath = "//*[@name='q']")
    WebElement searchButton;

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

    public void pasteSearchingQuery() {
        fillIn(searchButton, TEXT_DEMANDED).pressEnter(searchButton);
        LOGGER.info("Search for Pricing Calculator page\n");
    }
}

