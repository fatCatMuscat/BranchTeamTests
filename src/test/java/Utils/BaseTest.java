package Utils;

import PageObjects.BranchPage;
import PageObjects.GooglePage;
import PageObjects.GoogleResultsPage;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class BaseTest {

    protected static WebDriver driver;
    protected static org.apache.logging.log4j.Logger logger = LogManager.getLogger(Tests.TeamModuleTests.class);
    protected static Properties prop = new Properties();

    @BeforeSuite(alwaysRun = true)
    public void setUp() throws IOException {
        FileInputStream file = new FileInputStream(System.getProperty("user.dir") + "/src/test/base.properties");
        prop.load(file);
    }

    @BeforeMethod(groups = "team")
    public void navigateToBranchViaGoogleSearchAndOpenTeamPage() {
        System.setProperty(prop.getProperty("browserDriver"), prop.getProperty("pathToBrowserDriver"));
        driver = new ChromeDriver();
        driver.get(prop.getProperty("googleURL"));
        maximizeWindow();
        GooglePage googlePage = new GooglePage();
        googlePage.changeSearchQuery(prop.getProperty("queryBranchMetrics"));
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
