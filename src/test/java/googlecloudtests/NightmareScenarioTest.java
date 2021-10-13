package googlecloudtests;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.GoogleCloudPage;
import pages.GoogleCloudPricingCalculatorPage;
import pages.GoogleCloudSearchPage;
import pages.YopmailServicePage;
import steps.ComplexSteps;

public class NightmareScenarioTest extends BaseTest {

    private final GoogleCloudPage homePage = new GoogleCloudPage();
    private final GoogleCloudSearchPage searchResultsPage = new GoogleCloudSearchPage();
    private final GoogleCloudPricingCalculatorPage pricingCalculatorPage = new GoogleCloudPricingCalculatorPage();
    private final YopmailServicePage mailServicePage = new YopmailServicePage();
    private final ComplexSteps complexStep = new ComplexSteps();
    private final SoftAssert softAssert = new SoftAssert();

    @Test
    public void googleCloudPricingCalculatorTest() {

        homePage.goToHomePage();
        complexStep.searchForCalculatorStep();
        softAssert.assertTrue(searchResultsPage.checkIfIsResultsPage(), "Not on results page!");

        searchResultsPage.goToCalculatorPage();
        complexStep.fillInCalculatorFormStep();
        softAssert.assertTrue(pricingCalculatorPage.checkIfInstanceTypeIsCorrect(), "Instance type is not of awaited value!");
        softAssert.assertTrue(pricingCalculatorPage.checkIfRegionIsCorrect(), "Region is not Frankfurt!");
        softAssert.assertTrue(pricingCalculatorPage.checkIfVMClassIsCorrect(), "VM type is not correct!");
        softAssert.assertTrue(pricingCalculatorPage.checkIfCommitmentTypeIsCorrect(), "Commitment is not for 1 year!");
        softAssert.assertTrue(pricingCalculatorPage.checkIfLocalSSDIsCorrect(), "SSD type is not correct!");
        softAssert.assertTrue(pricingCalculatorPage.checkIfPaymentPerMonthIsCorrect(), "Monthly payment is not USD 1,085.25!");

        mailServicePage.goToMailServicePage();
        complexStep.getTemporaryEmailAddressStep();
        complexStep.sendCalculationsAsEmailStep();
        complexStep.getCalculationsEmailStep();
        softAssert.assertTrue(mailServicePage.checkIfMailedPriceIsCorrect(), "Price in mail is not USD 1,085.25!");

        softAssert.assertAll();
    }
}
