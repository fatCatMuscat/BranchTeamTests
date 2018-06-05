package Tests;

import PageObjects.TeamPage;
import Utils.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;


public class TeamModuleTests extends BaseTest {


    @Test (groups = "team")
    public void verifyQuantityOfEmployeesFromAllTabEqualsSumOfEmployeesFromOtherTabs() {
        TeamPage teamPage = new TeamPage();
        Assert.assertEquals(teamPage.countEmployeeCardsFromAllTab(), teamPage.countSumOfEmployeesFromEachDepartment());
    }

    @Test (groups = "team")
    public void verifyEmployeeNamesMatchBetweenAllAndOtherTabs() {
        TeamPage teamPage = new TeamPage();
        Assert.assertTrue(teamPage.namesFromAllTabAndDepartmentTabsMatch());
    }


    @Test (groups = "team")
    public void VerifyEmployeeDepartmentsListedCorrectlyBetweenAllTabAndDepartmentsTabs() {
        TeamPage teamPage = new TeamPage();
        Map<String, String> allEmployeesNamesAndDepts = teamPage.getNamesAndDepartmentsOfEmployees();
        Map<String, String> otherEmployeesNamesAndDepts = teamPage.getNamesAndDptsOfEmployeesFromDepartmentTabs();
        Assert.assertTrue(teamPage.employeeDepartmentsMatchBetweenAllAndDepartmentsTabs(allEmployeesNamesAndDepts, otherEmployeesNamesAndDepts));
    }

    //Employee image file names contain first name of employee and the first letter of the last name,
    // so here we verify if image file names comply to this requirement.
    @Test (groups = "team")
    public void VerifyEmployeeNamesCorrespondPortraitImageFileNamesOnAllTab() {
        TeamPage teamPage = new TeamPage();
        List<String> images = teamPage.getDisplayedImageSourceAttributeText();
        List<String> names = teamPage.getNamesFromAllTab();
        Assert.assertTrue(teamPage.displayedDepartmentEmployeeImageMatch(names, images));
    }


    @Test
    public void test() {
        logger.error("ajldgjal");
        logger.debug("debug");
    }





}
