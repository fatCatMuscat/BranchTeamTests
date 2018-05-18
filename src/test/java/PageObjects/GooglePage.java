package PageObjects;

import PageFactories.GooglePageFactory;
import Utils.BaseTest;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.PageFactory;

public class GooglePage extends BaseTest {

    private GooglePageFactory googlePageFactory = new GooglePageFactory();

    public GooglePage() {
        PageFactory.initElements(driver, googlePageFactory);
        waitForElementToLoad(googlePageFactory.logo);
    }

    public void changeSearchQuery(String query) {
        googlePageFactory.searchBar.clear();
        googlePageFactory.searchBar.sendKeys(query);
    }

    public GoogleResultsPage hitEnterInSearchBar() {
        googlePageFactory.searchBar.sendKeys(Keys.RETURN);
        return new GoogleResultsPage();
    }

    public GoogleResultsPage clickSearch() {
        googlePageFactory.googleSearchBtn.click();
        return new GoogleResultsPage();
    }


}
