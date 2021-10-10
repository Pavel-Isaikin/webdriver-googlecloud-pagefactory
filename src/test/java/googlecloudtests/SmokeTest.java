package googlecloudtests;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.GoogleCloudPage;
import pages.GoogleCloudPricingCalculatorPage;
import pages.GoogleCloudSearchPage;
import pages.YopmailServicePage;
import steps.ComplexSteps;

public class SmokeTest extends BaseTest {

    private final GoogleCloudPage homePage = new GoogleCloudPage();
    private final GoogleCloudSearchPage searchResultsPage = new GoogleCloudSearchPage();
    private final GoogleCloudPricingCalculatorPage pricingCalculatorPage = new GoogleCloudPricingCalculatorPage();
    private final YopmailServicePage mailServicePage = new YopmailServicePage();
    private final ComplexSteps complexStep = new ComplexSteps();
    private final SoftAssert softAssert = new SoftAssert();

    @Test
    public void canNavigateToGoogleCloudPlatformPageTest() {
        homePage.goToHomePage();
        softAssert.assertTrue(homePage.checkIfIsGoogleCloudPage(), "Not on Google CLoud Platform page!");
    }

    @Test
    public void canNavigateToPricingCalculatorPageBySearchTest() {
        homePage.goToHomePage();
        complexStep.searchForCalculatorStep();
        softAssert.assertTrue(searchResultsPage.checkIfIsResultsPage(), "Not on results page!");
    }

    @Test
    public void canChooseAndConfirmDemandedParametersTest() {
        homePage.goToHomePage();
        complexStep.searchForCalculatorStep();
        searchResultsPage.goToCalculatorPage();
        complexStep.fillInCalculatorFormStep();
        softAssert.assertTrue(pricingCalculatorPage.checkIfInstanceTypeIsCorrect(), "Instance type is not of awaited value!");
        softAssert.assertTrue(pricingCalculatorPage.checkIfRegionIsCorrect(), "Region is not Frankfurt!");
        softAssert.assertTrue(pricingCalculatorPage.checkIfVMClassIsCorrect(), "VM type is not correct!");
        softAssert.assertTrue(pricingCalculatorPage.checkIfCommitmentTypeIsCorrect(), "Commitment is not for 1 year!");
        softAssert.assertTrue(pricingCalculatorPage.checkIfLocalSSDIsCorrect(), "SSD type is not correct!");
    }

    @Test
    public void canCheckPriceAfterCommitmentConfirmationTest() {
        homePage.goToHomePage();
        complexStep.searchForCalculatorStep();
        searchResultsPage.goToCalculatorPage();
        complexStep.fillInCalculatorFormStep();
        softAssert.assertTrue(pricingCalculatorPage.checkIfPaymentPerMonthIsCorrect(), "Monthly payment is not USD 1,083.33!");
    }

    @Test
    public void canSendAndReceiveCommitmentLetterTest() {
        homePage.goToHomePage();
        complexStep.searchForCalculatorStep();
        searchResultsPage.goToCalculatorPage();
        pricingCalculatorPage.changeFrames();
        pricingCalculatorPage.fillInNumberOfInstancesForm();
        pricingCalculatorPage.clickToConfirm();
        mailServicePage.goToMailServicePage();
        complexStep.getTemporaryEmailAddressStep();
        complexStep.sendCalculationsAsEmailStep();
        mailServicePage.goToMailbox();
        softAssert.assertFalse(mailServicePage.checkIfMailReceived(), "No mail in the box!");
    }
}
