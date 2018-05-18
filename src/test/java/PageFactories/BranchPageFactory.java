package PageFactories;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class BranchPageFactory {

    @FindBy(id = "logo")
    public WebElement branchLogo;

    @FindBy(xpath = "//*[@data-element-tag='team']")
    public WebElement teamLink;

    @FindBy(id ="CybotCookiebotDialogBodyButtonAccept")
    public WebElement cookieBanner;

    @FindBy(xpath = "//*[@src='/img/home/deeplinks.svg']")
    public WebElement deepLinksImg;

}
