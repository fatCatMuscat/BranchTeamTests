package PageFactories;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class GoogleResultsPageFactory {
    @FindBy(xpath = "//*[contains(text(),'Branch - A mobile linking platform')]")
    public WebElement branchLink;
}
