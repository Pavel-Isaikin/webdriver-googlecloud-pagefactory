package googlecloudtests;

import org.testng.annotations.Test;
import pages.GoogleCloudPage;

public class TestingTest extends BaseTest {

    private final GoogleCloudPage homePage = new GoogleCloudPage();

    @Test
    public void starterTest() {

        homePage.goToHomePage();

    }
}
