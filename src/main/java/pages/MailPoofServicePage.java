package pages;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MailPoofServicePage extends BasePage{

    private final Logger LOGGER = LogManager.getLogger();

    @FindBy (xpath = "//input[@type = 'submit' and @action = 'Random']")
    private WebElement getRandomMailButton;

    @FindBy (xpath = "//input[@id = 'current-id']")
    private WebElement createdMail;

    public void generateMail() {
        driver.switchTo().window(tabs.get(1));
        clickTo(getRandomMailButton);
        LOGGER.info("Generate new email address on MailPoof");
    }

    public void getCreatedMailText() {
        GoogleCloudPricingCalculatorPage.newMail = createdMail.getAttribute("value");
        driver.switchTo().window(tabs.get(0));
    }



}
