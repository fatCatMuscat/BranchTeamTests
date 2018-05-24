package Utils;

import PageObjects.BranchPage;
import PageObjects.GooglePage;
import PageObjects.GoogleResultsPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    protected static WebDriver driver;

    @BeforeMethod(groups = "team")
    public void navigateToBranchViaGoogleSearchAndOpenTeamPage() {
        System.setProperty("webdriver.chrome.driver", "/Users/arsenal/webdrivers/chromedriver");
        driver = new ChromeDriver();
        driver.get("https://www.google.com");
        maximizeWindow();
        GooglePage googlePage = new GooglePage();
        googlePage.changeSearchQuery("branch metrics");
        GoogleResultsPage googleResultsPage = googlePage.hitEnterInSearchBar();
        googleResultsPage.clickBranchLink();
        BranchPage branchPage = new BranchPage();
        branchPage.scrollToTeamLink();
        branchPage.clickAcceptOnCookieBanner();
        branchPage.clickOnTeamLink();
    }

    @AfterMethod(groups = "team")
    public void closeBrowser() {
        driver.quit();
    }

    public void waitForElementToLoad(WebElement webElement) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(webElement));
    }

    public void maximizeWindow() {
        driver.manage().window().maximize();
    }


    public void scrollAndMoveToElement(WebElement webElement) {
        Actions actions = new Actions(driver);
        actions.moveToElement(webElement);
        actions.perform();
    }

}
