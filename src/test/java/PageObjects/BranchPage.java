package PageObjects;

import PageFactories.BranchPageFactory;
import Utils.BaseTest;
import org.openqa.selenium.support.PageFactory;

public class BranchPage extends BaseTest {

    BranchPageFactory branchPageFactory = new BranchPageFactory();

    public BranchPage() {
        PageFactory.initElements(driver, branchPageFactory);
        waitForElementToLoad(branchPageFactory.deepLinksImg);
    }

    public void scrollToTeamLink() {
        scrollAndMoveToElement((branchPageFactory.teamLink));
    }

    public void clickAcceptOnCookieBanner() {
        branchPageFactory.cookieBanner.click();
    }

    public void clickOnTeamLink() {
        branchPageFactory.teamLink.click();
    }



}
