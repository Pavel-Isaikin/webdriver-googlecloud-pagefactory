package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static util.CommonConstants.MAILPAGEURL;
import static util.CommonConstants.PRICE_TO_COMPARE;

public class YopmailServicePage extends BasePage {

    private final Logger LOGGER = LogManager.getLogger();

    @FindBy(xpath = "//a[@href='email-generator' and @class='hlink']")
    private WebElement mailGenerationButton;
    @FindBy(xpath = "//div[@id='egen']")
    private WebElement generatedMailTab;
    @FindBy(xpath = "//button[@onclick='egengo();']")
    private WebElement mailboxButton;
    @FindBy(xpath = "//h3[contains (text(), 'USD')]")
    private WebElement currentPrice;
    @FindBy(id = "ifmail")
    private WebElement mailFrame;
    @FindBy(xpath = "//div[@id='nbmail']")
    private WebElement mailCounter;
    @FindBy(xpath = "//button[@id='refresh']")
    private WebElement refreshMailboxButton;

    public YopmailServicePage() {
        PageFactory.initElements(driver, this);
    }

    public void goToMailServicePage() {
        openNewTab(MAILPAGEURL);
        tabs = new ArrayList<>(driver.getWindowHandles());
        LOGGER.info("Navigate to mailing service");
    }

    public void generateTemporaryEMail() {
        driver.switchTo().window(tabs.get(1));
        clickTo(mailGenerationButton);
        LOGGER.info("Generate new email address");
    }

    public void copyGeneratedEMailLink() {
        GoogleCloudPricingCalculatorPage.newMail = generatedMailTab.getText();
        driver.switchTo().window(tabs.get(0));
    }

    public void goToMailbox() {
        driver.switchTo().window(tabs.get(1));
        clickTo(mailboxButton);
        do {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clickTo(refreshMailboxButton);
        } while (mailCounter.getText().contains("0"));
    }

    public void changeFrameToActualMail() {
        driver.switchTo().frame(mailFrame);
    }

    public boolean checkIfMailedPriceIsCorrect() {
        String checker = currentPrice.getText();
        LOGGER.info("Check price in mail: " + checker);
        return (checker.contains(PRICE_TO_COMPARE));
    }
}
