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
    private WebElement GPUNumberDropdown;
    @FindBy(xpath = "//md-option[@ng-repeat='item in listingCtrl.supportedGpuNumbers[listingCtrl.computeServer.gpuType]' and @value='1']")
    private WebElement GPUNumberChoice;
    @FindBy(xpath = "//md-select [@placeholder='Local SSD']/md-select-value[starts-with(@id,'select_value_label_')]")
    private WebElement SSDDropdown;
    @FindBy(xpath = "//md-option[@ng-repeat='item in listingCtrl.dynamicSsd.computeServer' and @value='2']")
    private WebElement SSDChoice;
    @FindBy(xpath = "//md-select [@placeholder='Datacenter location']/md-select-value[starts-with(@id,'select_value_label_')]")
    private WebElement regionDropdown;
    @FindBy(xpath = "//md-option[@ng-repeat='item in listingCtrl.fullRegionList | filter:listingCtrl.inputRegionText.computeServer' and @value='europe-west3']")
    private WebElement regionChoice;
    @FindBy(xpath = "//md-select [@placeholder='Committed usage' and @ng-disabled='listingCtrl.isCudDisabled']/md-select-value[starts-with(@id,'select_value_label_')]")
    private WebElement commitmentTermDropdown;
    @FindBy(xpath = "//div[@class='md-select-menu-container md-active md-clickable']/md-select-menu/md-content/md-option [@ng-value='1']")
    private WebElement commitmentTermChoice;
    @FindBy(xpath = "//iframe[@allow='clipboard-write https://cloud-dot-devsite-v2-prod.appspot.com']")
    private WebElement outerFrame;
    @FindBy(id = "myFrame")
    private WebElement innerFrame;
    @FindBy(xpath = "//input[@ng-model='listingCtrl.computeServer.quantity']")
    private WebElement instancesNumberField;
    @FindBy(xpath = "//*[@aria-label='Add to Estimate']")
    private WebElement confirmButton;
    @FindBy(xpath = "//*[@ng-model='listingCtrl.soleTenant.addGPUs']")
    private WebElement GPUCheckboxArea;
    @FindBy(xpath = "//*[@aria-label='Add GPUs']")
    private WebElement GPUCheckbox;
    @FindBy(xpath = "//button[@id='email_quote']")
    private WebElement eMailButton;
    @FindBy(xpath = "//*[@aria-label='Send Email']")
    private WebElement sendEMailButton;
    @FindBy(xpath = "//input[@ng-model='emailQuote.user.email']")
    private WebElement eMailAddressField;
    @FindBy(xpath = "//*[@class='md-1-line md-no-proxy ng-scope' and @ng-if='item.items.editHook && item.items.editHook.initialInputs.class']")
    private WebElement chosenVMClass;
    @FindBy(xpath = "//*[@class='md-list-item-text ng-binding cpc-cart-multiline flex' and contains(., 'n1')]")
    private WebElement chosenInstanceType;
    @FindBy(xpath = "//div[@class='md-list-item-text ng-binding' and contains (., 'Frankfurt')]")
    private WebElement chosenRegion;
    @FindBy(xpath = "//md-list-item[@class='md-1-line md-no-proxy ng-scope']/div[@class='md-list-item-text ng-binding cpc-cart-multiline flex']")
    private WebElement chosenSSD;
    @FindBy(xpath = "//md-list-item[@ng-if='item.items.termText && item.items.termText.length != 0']/div[@class='md-list-item-text ng-binding']")
    private WebElement chosenCommitmentTerm;
    @FindBy(xpath = "//h2[@class='md-title']/b[@class='ng-binding']")
    private WebElement calculatedPrice;

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

    public void pasteGeneratedEmailAddress() {
        LOGGER.info("temporary email gained: " + newMail);
        clickTo(eMailAddressField).fillIn(eMailAddressField, newMail);
    }

    public void sendMailToPastedEmailAddress() {
        clickTo(sendEMailButton);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        return (checker.contains("USD 1,085.25"));
    }
}
