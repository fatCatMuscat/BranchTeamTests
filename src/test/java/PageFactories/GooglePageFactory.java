package PageFactories;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class GooglePageFactory {

    @FindBy(id = "hplogo")
    public WebElement logo;

    @FindBy(id="lst-ib")
    public WebElement searchBar;

    @FindBy(xpath = "//*[@name='btnK']")
    public WebElement googleSearchBtn;

}
