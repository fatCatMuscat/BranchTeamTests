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
        int allEmployees = teamPage.countEmployeeCardsFromAllTab();
        int otherEmployees = teamPage.countSumOfEmployeesFromEachDepartment();
        Assert.assertEquals(allEmployees, otherEmployees);
    }

    @Test (groups = "team")
    public void verifyEmployeeNamesMatchBetweenAllAndOtherTabs() {
        TeamPage teamPage = new TeamPage();
        Assert.assertTrue(teamPage.namesFromAllTabAndDepartmentTabsMatch());
    }


    @Test (groups = "team")
    public void VerifyEmployeeDepartmentsListedCorrectlyBetweenAllAndOtherTabs() {
        TeamPage teamPage = new TeamPage();
        Map<String, String> allEmployeesNamesDepts = teamPage.getNamesAndDepartmentsOfEmployees();
        Map<String, String> otherEmployeesNamesDepts = teamPage.getNamesAndDptsOfEmployeesFromOtherCategories();
        Assert.assertTrue(teamPage.employeeDepartmentsMatchBetweenAllAndDepartmentsTabs(allEmployeesNamesDepts, otherEmployeesNamesDepts));
    }

    //Employee image file names contain first name of employee and the first letter of the last name,
    // so here we verify if image file names comply to this requirement.
    @Test (groups = "team")
    public void VerifyEmployeeNamesMatchEmployeePortraitsFileNames() {
        TeamPage teamPage = new TeamPage();
        List<String> images = teamPage.getDisplayedImageSourceAttributeText();
        List<String> names = teamPage.getNamesFromAllTab();
        Assert.assertTrue(teamPage.displayedDepartmentEmployeeImageMatch(names, images));
    }





}
