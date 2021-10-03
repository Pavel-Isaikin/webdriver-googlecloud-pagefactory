package steps;

import pages.GoogleCloudPage;
import pages.GoogleCloudPricingCalculatorPage;
import pages.YopmailServicePage;

public class ComplexSteps {

    private final GoogleCloudPage googleCloudMainPage = new GoogleCloudPage();
    private final GoogleCloudPricingCalculatorPage pricingCalculatorPage = new GoogleCloudPricingCalculatorPage();
    private final YopmailServicePage mailServicePage = new YopmailServicePage();

    public void searchForCalculatorStep() {
        googleCloudMainPage.goToSearchField();
        googleCloudMainPage.pasteSearchingQuery();
    }

    public void fillInCalculatorFormStep() {
        pricingCalculatorPage.changeFrames();
        pricingCalculatorPage.fillInNumberOfInstancesForm();
        pricingCalculatorPage.chooseMachineSeries();
        pricingCalculatorPage.chooseMachineType();
        pricingCalculatorPage.activateGPUCheckbox();
        pricingCalculatorPage.chooseGPUNumber();
        pricingCalculatorPage.chooseGPUType();
        pricingCalculatorPage.chooseSSD();
        pricingCalculatorPage.chooseDatacenterLocation();
        pricingCalculatorPage.chooseUsageLength();
        pricingCalculatorPage.clickToConfirm();
    }

    public void getTemporaryEmailAddressStep() {
        mailServicePage.generateTemporaryEMail();
        mailServicePage.copyGeneratedEMailLink();
    }

    public void sendCalculationsAsEmailStep() {
        pricingCalculatorPage.changeFrames();
        pricingCalculatorPage.clickToGetResultsAsMail();
        pricingCalculatorPage.fillInMyEmailField();
        pricingCalculatorPage.sendMailToMyEmailAddress();
    }

    public void getCalculationsEmailStep() {
        mailServicePage.goToMailbox();
        mailServicePage.changeFrameToActualMail();
    }
}