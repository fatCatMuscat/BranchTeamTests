package PageFactories;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class TeamPageFactory {

    @FindBy(xpath = "//*[@src='/img/team/offsite-23-b.jpg']")
    public WebElement heroImg;

    @FindBy(xpath = "//*[@data-element-tag='team']")
    public WebElement teamLink;

    @FindBy(xpath = "//div/ul[@class='team-categories']/li/a")
    public List<WebElement> teamDepartments;

    //This xpath locates employee cards that are currently displayed in browser utilizing
    //attribute 'display: inline-block' (as opposed to 'display:none' when web element is hidden)
    //and class 'category-all'.
    @FindBy(xpath = "//*[contains(@class, 'category-all') and contains(@style, 'inline')]")
    public List<WebElement> displayedEmployeeCards;

    @FindBy(xpath = "//*[contains(@class, 'category-all') and contains(@style, 'inline')]/div/div/div/h2")
    public List<WebElement> displayedEmployeeNames;

    @FindBy(xpath = "//*[contains(@class, 'category-all') and contains(@style, 'inline')]/div/div/div/h4")
    public List<WebElement> displayedEmployeeDepartment;

    @FindBy(xpath = "//*[contains(@class, 'category-all') and contains(@style, 'inline')]/div/div/div[contains(@style, 'jpg')]")
    public List<WebElement> displayedEmployeeImages;
}
