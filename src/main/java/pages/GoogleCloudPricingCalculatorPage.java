package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static util.CommonConstants.NUMBER_OF_INSTANCES;
import static util.CommonConstants.TIMEOUT;

public class GoogleCloudPricingCalculatorPage extends BasePage {

    private final Logger LOGGER = LogManager.getLogger();

    private static final String COMMON_DROPDOWN_LABEL_PATTERN = "//md-select [@placeholder='%s']/md-select-value[starts-with(@id,'select_value_label_')]";
    private static final String COMMON_DROPDOWN_OPTION_PATTERN = "//md-option[@class='ng-scope md-ink-ripple' and @value='%s']";

    @FindBy(xpath = "//md-select [@placeholder='Number of GPUs']/md-select-value[starts-with(@id,'select_value_label_')]")
    WebElement GPUNumberDropdown;
    @FindBy(xpath = "//md-option[@ng-repeat='item in listingCtrl.supportedGpuNumbers[listingCtrl.computeServer.gpuType]' and @value='1']")
    WebElement GPUNumberChoice;
    @FindBy(xpath = "//md-select [@placeholder='Local SSD']/md-select-value[starts-with(@id,'select_value_label_')]")
    WebElement SSDDropdown;
    @FindBy(xpath = "//md-option[@ng-repeat='item in listingCtrl.dynamicSsd.computeServer' and @value='2']")
    WebElement SSDChoice;
    @FindBy(xpath = "//md-select [@placeholder='Datacenter location']/md-select-value[starts-with(@id,'select_value_label_')]")
    WebElement regionDropdown;
    @FindBy(xpath = "//md-option[@ng-repeat='item in listingCtrl.fullRegionList | filter:listingCtrl.inputRegionText.computeServer' and @value='europe-west3']")
    WebElement regionChoice;
    @FindBy(xpath = "//md-select [@placeholder='Committed usage']/md-select-value[starts-with(@id,'select_value_label_')]")
    WebElement commitmentTermDropdown;
    @FindBy(xpath = "//md-option[@id='select_option_106']")
    WebElement commitmentTermChoice;
    @FindBy(xpath = "//iframe[@allow='clipboard-write https://cloud-dot-devsite-v2-prod.appspot.com']")
    WebElement outerFrame;
    @FindBy(id = "myFrame")
    WebElement innerFrame;
    @FindBy(xpath = "//*[@id='input_71']")
    WebElement instancesNumberField;
    @FindBy(xpath = "//*[@aria-label='Add to Estimate']")
    WebElement confirmButton;
    @FindBy(xpath = "//*[@ng-model='listingCtrl.soleTenant.addGPUs']")
    WebElement GPUCheckboxArea;
    @FindBy(xpath = "//*[@aria-label='Add GPUs']")
    WebElement GPUCheckbox;
    @FindBy(xpath = "//button[@id='email_quote']")
    WebElement eMailButton;
    @FindBy(xpath = "//*[@aria-label='Send Email']")
    WebElement sendEMailButton;
    @FindBy(xpath = "//input[@ng-model='emailQuote.user.email']")
    WebElement eMailAddressField;

    @FindBy(xpath = "//*[@class='md-1-line md-no-proxy ng-scope' and @ng-if='item.items.editHook && item.items.editHook.initialInputs.class']")
    WebElement chosenVMClass;
    @FindBy(xpath = "//*[@class='md-list-item-text ng-binding cpc-cart-multiline flex' and contains(., 'n1')]")
    WebElement chosenInstanceType;
    @FindBy(xpath = "//div[@class='md-list-item-text ng-binding' and contains (., 'Frankfurt')]")
    WebElement chosenRegion;
    @FindBy(xpath = "//md-list-item[@class='md-1-line md-no-proxy ng-scope']/div[@class='md-list-item-text ng-binding cpc-cart-multiline flex']")
    WebElement chosenSSD;
    @FindBy(xpath = "//md-list-item[@ng-if='item.items.termText && item.items.termText.length != 0']/div[@class='md-list-item-text ng-binding']")
    WebElement chosenCommitmentTerm;
    @FindBy(xpath = "//h2[@class='md-title']/b[@class='ng-binding']")
    WebElement calculatedPrice;

    public GoogleCloudPricingCalculatorPage() {
        PageFactory.initElements(driver, this);
    }

    private void dropdownChoice(String labelID, String optionID) {
        clickTo(driver.findElement(By.xpath(String.format(COMMON_DROPDOWN_LABEL_PATTERN, labelID))))
                .clickTo(driver.findElement(By.xpath(String.format(COMMON_DROPDOWN_OPTION_PATTERN, optionID))));
    }

    public void changeFrames() {
        scrollToView(outerFrame);
        new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT)).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(outerFrame));
        scrollToView(innerFrame);
        new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT)).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(innerFrame));
    }

    public void fillInNumberOfInstancesForm() {
        clickTo(instancesNumberField)
                .fillIn(instancesNumberField, NUMBER_OF_INSTANCES);
    }

    public void chooseMachineSeries() {
        dropdownChoice("Series", "n1");
    }

    public void chooseMachineType() {
        dropdownChoice("Instance type", "CP-COMPUTEENGINE-VMIMAGE-N1-STANDARD-8");
    }

    public void chooseDatacenterLocation() {
        scrollToView(regionDropdown);
        clickTo(regionDropdown).clickTo(regionChoice);
    }

    public void activateGPUCheckbox() {
        scrollToView(GPUCheckboxArea).clickTo(GPUCheckbox);
    }

    public void chooseGPUNumber() {
        clickTo(GPUNumberDropdown).clickTo(GPUNumberChoice);
    }

    public void chooseGPUType() {
        dropdownChoice("GPU type", "NVIDIA_TESLA_P4");
    }

    public void chooseSSD() {
        scrollToView(SSDDropdown);
        clickTo(SSDDropdown).clickTo(SSDChoice);
    }

    public void chooseUsageLength() {
        clickTo(commitmentTermDropdown).clickTo(commitmentTermChoice);
    }

    public void clickToConfirm() {
        clickTo(confirmButton);
        LOGGER.info("Fill in VM commitment form and confirm\n");
    }

    public void clickToGetResultsAsMail() {
        clickTo(eMailButton);
    }

    public void fillInMyEmailField() {
        LOGGER.info("temporary email gained: " + newMail);
        clickTo(eMailAddressField).fillIn(eMailAddressField, newMail);
    }

    public void sendMailToMyEmailAddress() {
        clickTo(sendEMailButton);
        LOGGER.info("Send mail to generated email address\n");
    }


    public boolean checkIfRegionIsCorrect() {
        String checker = chosenRegion.getText();
        return (checker.contains("Region: Frankfurt"));
    }

    public boolean checkIfVMClassIsCorrect() {
        String checker = chosenVMClass.getText();
        LOGGER.info("Check VM class: " + checker);
        return (checker.contains("VM class: regular"));
    }

    public boolean checkIfInstanceTypeIsCorrect() {
        String checker = chosenInstanceType.getText();
        LOGGER.info("Check instance type: n1-standard-8");
        return (checker.contains("Instance type: n1-standard-8"));
    }

    public boolean checkIfLocalSSDIsCorrect() {
        String checker = chosenSSD.getText();
        LOGGER.info("Check SSD: 2x375 GiB");
        return (checker.contains("Local SSD: 2x375 GiB"));
    }

    public boolean checkIfCommitmentTypeIsCorrect() {
        String checker = chosenCommitmentTerm.getText();
        LOGGER.info("Check commitment: " + checker);
        return (checker.contains("Commitment term: 1 Year"));
    }

    public boolean checkIfPaymentPerMonthIsCorrect() {
        String checker = calculatedPrice.getText();
        LOGGER.info("Check final price: " + checker + "\n");
        return (checker.contains("USD 1,083.33"));
    }
}
