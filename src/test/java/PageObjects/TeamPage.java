package PageObjects;

import PageFactories.TeamPageFactory;
import Utils.BaseTest;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

import java.util.*;


public class TeamPage extends BaseTest {

    private TeamPageFactory teamPageFactory = new TeamPageFactory();

    public TeamPage() {
        PageFactory.initElements(driver, teamPageFactory);
        waitForElementToLoad(teamPageFactory.heroImg);
    }

    public int countEmployeeCardsDisplayed() {
        if (teamPageFactory.displayedEmployeeCards.size() <= 0)
            logger.error("ERROR no employee cards found on the page");
        return teamPageFactory.displayedEmployeeCards.size();
    }

    public int countEmployeeCardsFromAllTab() {
        int allEmployeesQty = countEmployeeCardsDisplayed();
        Reporter.log("FAILURE: " + allEmployeesQty +
                " employee cards are found on 'All' departments tab", true);
        return allEmployeesQty;
    }

    public int countDepartmentTabs() {
        if (teamPageFactory.teamDepartments.size() <= 0)
            logger.error("ERROR no department tabs found on the page");
        return teamPageFactory.teamDepartments.size();
    }

    public int countSumOfEmployeesFromEachDepartment() {
        int sum = 0;
        int categoriesQty = countDepartmentTabs();
        List<WebElement> teamDepartments = teamPageFactory.teamDepartments;
        if (teamDepartments.size() <= 0)
            logger.error("ERROR no team departments tab web elements added to the list of departments");

        for (int i = 1; i < categoriesQty; i++) {
            teamDepartments.get(i).click();
            sum += countEmployeeCardsDisplayed();
        }
        Reporter.log("FAILURE: " + sum +
                " total employee cards are found on 'other' department tabs", true);
        return sum;
    }

    //convert a list of WebElements into a List of Strings copied from element's text
    public List<String> getTexts(List<WebElement> webElementList) {
        if (webElementList.isEmpty()) {
            logger.error("Error: no elements in the input list. Can not be converted to List<String>");
            return null;
        }
        ArrayList<String> stringArrayList = new ArrayList<String>();
        for (WebElement wE :webElementList) {
            stringArrayList.add(wE.getText());
        }
        return stringArrayList;
    }

    // store employee names in a List of Strings from 'All' tab
    public List<String> getNamesFromAllTab() {
        List<String> namesFromAllCategory = new ArrayList<>(getTexts(teamPageFactory.displayedEmployeeNames));
        if (namesFromAllCategory.isEmpty()) logger.error("ERROR: no employee names elements found");
        return namesFromAllCategory;
    }

    // parse employee names and store them in a List
    public List<String> getNamesFromDepartments() {
        List<String> employeeNames = new ArrayList<>();
        List<WebElement> teamDepartments = teamPageFactory.teamDepartments;
        for (int i = 1; i < countDepartmentTabs(); i++) {
            teamDepartments.get(i).click();
            employeeNames.addAll(getTexts(teamPageFactory.displayedEmployeeNames));
        }
        if (employeeNames.isEmpty()) Reporter.log("FAILURE: no employee name elements found", true);
        return employeeNames;
    }

    // verify that employee names match between All tab and other tabs and print non-matching names
    public boolean namesFromAllTabAndDepartmentTabsMatch() {

        List<String> namesFromAllTab = getNamesFromAllTab();
        List<String> namesFromDepartments = getNamesFromDepartments();

        if (namesFromAllTab.isEmpty() || namesFromDepartments.isEmpty())
            logger.error("ERROR can't collect names of employees from either All or Department tabs");

        List<String> temp = new ArrayList<>(namesFromDepartments);

        namesFromDepartments.removeAll(namesFromAllTab);
        namesFromAllTab.removeAll(temp);

        if (!namesFromAllTab.isEmpty() || !namesFromDepartments.isEmpty()) {
            Reporter.log("FAILURE: names found only in All tab: " +
                    Arrays.toString(namesFromAllTab.toArray()), true);
            Reporter.log("FAILURE: names found only in Other tabs: " +
                    Arrays.toString(namesFromDepartments.toArray()),true);
            return false;
        }
        return true;
    }



    public List<String> getEmployeeDepartmentsFromDisplayedDptTab() {

        List<String> employeeDepartmentsFromCurrentDptTab = getTexts(teamPageFactory.displayedEmployeeDepartment);

        if (employeeDepartmentsFromCurrentDptTab.isEmpty())
            Reporter.log("FAILURE: no employee department elements found", true);
        return employeeDepartmentsFromCurrentDptTab;
    }

    public List<String> getNamesOfDepartments() {
        List<WebElement> listOfDepartments = teamPageFactory.teamDepartments;
        List<String> namesOfDpts = new ArrayList<>();
        for (WebElement wE : listOfDepartments) {
            namesOfDpts.add(wE.getText());
        }
        return namesOfDpts;
    }

    // return a Map, Key = Employee's name, Value = Employee's dept
    public Map<String, String> getNamesAndDepartmentsOfEmployees() {
        HashMap<String, String> nameDpt = new HashMap<>();
        List<String> names = getNamesFromAllTab();
        List<String> dpts = getEmployeeDepartmentsFromDisplayedDptTab();

        for (int i = 0; i < countEmployeeCardsDisplayed(); i++) {
            nameDpt.put(names.get(i), dpts.get(i));
        }
        return nameDpt;
    }

    // traverse department tabs 1 by 1 and  return a Map, Key = Employee's name, Value = Employee's dept
    public Map<String, String> getNamesAndDptsOfEmployeesFromDepartmentTabs() {

        List<WebElement> teamCats = teamPageFactory.teamDepartments;
        int qtyCategories = teamCats.size();
        Map<String, String> otherEmployeesNamesDpts = new HashMap<>();

        for (int i = 1; i < qtyCategories; i++) {
            teamCats.get(i).click();
            otherEmployeesNamesDpts.putAll(getNamesAndDepartmentsOfEmployees());
        }
        return otherEmployeesNamesDpts;
    }

    public boolean employeeDepartmentsMatchBetweenAllAndDepartmentsTabs
            (Map<String, String> employeesInAllTab, Map<String, String> employeesInDptTab) {
        if(employeesInAllTab.size() != employeesInDptTab.size())
            Reporter.log("FAILURE: number of employees don't match between all tab and other tabs", true);
        List<String> nonMatchingDptEmployees = new ArrayList<String>();
        for (String name: employeesInAllTab.keySet()) {
            if (!employeesInAllTab.get(name).equalsIgnoreCase(employeesInDptTab.get(name))) {
                nonMatchingDptEmployees.add(name);
            }
        }
        if (nonMatchingDptEmployees.size() > 0) {
            Reporter.log("FAILURE: names of employees who's department value don't match" +
                    " between 'All' and other tabs: " +
                    Arrays.toString(nonMatchingDptEmployees.toArray()), true);
            return false;
        }
        else return true;
    }

    public List<String> getDisplayedImageSourceAttributeText() {
        List<WebElement> images = teamPageFactory.displayedEmployeeImages;

        ArrayList<String> stringArrayList = new ArrayList<String>();
        for (WebElement wE :images) {
            stringArrayList.add(wE.getAttribute("style"));
        }
        return stringArrayList;
    }

    // match names of employees to their portrait image file names and return names of non matching employees
    public boolean displayedDepartmentEmployeeImageMatch(List<String> names, List<String> images) {
        List<String> nonMatchingRecords = new ArrayList<>();
        for (int i = 0; i < names.size(); i++) {

            String imageSource = images.get(i).toLowerCase();
            String imageFileName = createImageName(names.get(i).toLowerCase());

            if (!imageSource.contains(imageFileName)) {
                nonMatchingRecords.add(names.get(i));
            }
        }
        if (nonMatchingRecords.size() > 0) {
            Reporter.log("FAILURE: Names of employees with non matching images" +
                    " or inconsistently named imageFiles: " +
                    Arrays.toString(nonMatchingRecords.toArray()), true);
            return false;
        }
        return true;
    }

    // modify employee name and return portrait image file name
    public String createImageName(String empName) {
        StringBuilder iN = new StringBuilder();

        String[] fnLn = empName.split(" ");
        iN.append(fnLn[0]);
        iN.append(fnLn[fnLn.length-1].substring(0, 1));
        return iN.toString().trim();
    }



}
