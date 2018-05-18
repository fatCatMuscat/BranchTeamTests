package Tests;

import PageObjects.BranchPage;
import PageObjects.GooglePage;
import PageObjects.GoogleResultsPage;
import PageObjects.TeamPage;
import Utils.BaseTest;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;


public class TeamModuleTests extends BaseTest {

    @BeforeMethod (groups = "team")
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

    @AfterMethod (groups = "team")
    public void closeBrowser() {
        driver.quit();
    }

    @Test (groups = "team")
    public void verifyQuantityOfEmployeesFromAllTabEqualsSumOfEmployeesFromOtherTabs() {
        TeamPage teamPage = new TeamPage();
        int allEmployees = teamPage.countEmployeeCardsFromAllCategory();
        int otherEmployees = teamPage.countSumOfEmployeesFromEachDepartment();
        Assert.assertEquals(allEmployees, otherEmployees);
    }

    @Test (groups = "team")
    public void verifyEmployeeNamesMatchBetweenAllAndOtherTabs() {
        TeamPage teamPage = new TeamPage();
        Assert.assertTrue(teamPage.namesFromAllAndOtherTabsMatch());
    }


    @Test (groups = "team")
    public void VerifyEmployeeDepartmentsListedCorrectlyBetweenAllAndOtherTabs() {
        TeamPage teamPage = new TeamPage();
        Map<String, String> allEmployeesNamesDepts = teamPage.getNamesAndDepartmentsOfEmployees();
        Map<String, String> otherEmployeesNamesDepts = teamPage.getNamesAndDptsOfEmployeesFromOtherCategories();
        Assert.assertTrue(teamPage.dptMatchAllOtherTabs(allEmployeesNamesDepts, otherEmployeesNamesDepts));
    }

    //Employee image file names contain first name of employee and the first letter of the last name,
    // so here we verify if image file names comply to this requirement.
    @Test (groups = "team")
    public void VerifyEmployeeNamesMatchEmployeePortraitsFileNames() {
        TeamPage teamPage = new TeamPage();
        List<String> images = teamPage.getDisplayedImageSourceAttributeText();
        List<String> names = teamPage.getNamesFromAllCategory();
        Assert.assertTrue(teamPage.displayedCategoryEmployeeImageMatch(names, images));
    }





}
