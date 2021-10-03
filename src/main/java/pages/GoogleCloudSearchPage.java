package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static util.CommonConstants.TEXT_DEMANDED;
import static util.CommonConstants.TIMEOUT;

public class GoogleCloudSearchPage extends BasePage {

    private final Logger LOGGER = LogManager.getLogger();

    @FindBy(xpath = "//*[@class='devsite-search-title']")
    private WebElement resultsPageTitle;
    @FindBy(xpath = "//*[contains(@class,'gsc-thumbnail-inside') or contains(@class,'gsc-result')]//a[contains (., 'Google Cloud Platform Pricing Calculator')]")
    private WebElement resultDemanded;


    public GoogleCloudSearchPage() {
        PageFactory.initElements(driver, this);
    }

    public void goToCalculatorPage() {
        clickTo(resultDemanded);
        LOGGER.info("Navigate to Pricing Calculator page");
    }

    public boolean checkIfIsResultsPage() {
        return new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT))
                .until(ExpectedConditions.elementToBeClickable(resultsPageTitle))
                .getText().contains("Search results for " + TEXT_DEMANDED);
    }
}
