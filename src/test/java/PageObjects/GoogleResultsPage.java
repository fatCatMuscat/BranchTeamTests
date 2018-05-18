package PageObjects;

import PageFactories.GoogleResultsPageFactory;
import Utils.BaseTest;
import org.openqa.selenium.support.PageFactory;

public class GoogleResultsPage extends BaseTest {

    GoogleResultsPageFactory googleResultsPageFactory = new GoogleResultsPageFactory();

    public GoogleResultsPage() {
        PageFactory.initElements(driver, googleResultsPageFactory);
        waitForElementToLoad(googleResultsPageFactory.branchLink);
    }

    public void clickBranchLink() {
        googleResultsPageFactory.branchLink.click();
    }

}
