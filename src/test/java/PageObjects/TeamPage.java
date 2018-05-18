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
        return teamPageFactory.displayedEmployeeCards.size();
    }

    public int countEmployeeCardsFromAllCategory() {
        int allEmployeesQty = countEmployeeCardsDisplayed();
        Reporter.log("DEBUG: " + allEmployeesQty +
                " employee cards are found on 'All' departments tab", true);
        return allEmployeesQty;
    }

    public int countSumOfEmployeesFromEachDepartment() {
        int sum = 0;
        int categoriesQty = teamDepartmentsCount();
        List<WebElement> listOfCategories = teamPageFactory.teamDepartments;

        for (int i = 1; i < categoriesQty; i++) {
            listOfCategories.get(i).click();
            sum += countEmployeeCardsDisplayed();
        }

        Reporter.log("DEBUG: " + sum +
                " total employee cards are found on 'other' department tabs", true);
        return sum;
    }

    //convert a list of WebElements into a List of Strings copied from element's text
    public List<String> getListOfStrings(List<WebElement> webElementList) {
        if (webElementList.isEmpty()) {
            Reporter.log("Error: no elements in the input list. Can not be converted to List<String>", true);
            return null;
        }
        ArrayList<String> stringArrayList = new ArrayList<String>();
        for (WebElement wE :webElementList) {
            stringArrayList.add(wE.getText());
        }
        return stringArrayList;
    }

    // store employee names in a List of Strings from 'All' tab
    public List<String> getNamesFromAllCategory() {
        List<String> namesFromAllCategory = new ArrayList<String>();
        namesFromAllCategory.addAll(getListOfStrings(teamPageFactory.displayedEmployeeNames));
        if (namesFromAllCategory.isEmpty()) Reporter.log("DEBUG: no employee names elements found", true);
        return namesFromAllCategory;
    }

    // parse employee names and store them in a List
    public List<String> getNamesFromOtherCategories() {
        List<String> namesFromOtherCategories = new ArrayList<String>();
        List<WebElement> currentCategory = teamPageFactory.teamDepartments;
        for (int i = 1; i < teamDepartmentsCount(); i++) {
            currentCategory.get(i).click();
            namesFromOtherCategories.addAll(getListOfStrings(teamPageFactory.displayedEmployeeNames));
        }
        if (namesFromOtherCategories.isEmpty()) Reporter.log("DEBUG: no employee name elements found", true);
        return namesFromOtherCategories;
    }

    // verify that employee names match between All tab and other tabs and print non-matching names
    public boolean namesFromAllAndOtherTabsMatch() {

        List<String> namesFromAllCategory = getNamesFromAllCategory();
        List<String> namesFromOtherCategory = getNamesFromOtherCategories();
        List<String> temp = new ArrayList<String>();
        temp.addAll(namesFromOtherCategory);

        namesFromOtherCategory.removeAll(namesFromAllCategory);
        namesFromAllCategory.removeAll(temp);

        if (!namesFromAllCategory.isEmpty() || !namesFromOtherCategory.isEmpty()) {
            Reporter.log("DEBUG: names found only in All tab: " +
                    Arrays.toString(namesFromAllCategory.toArray()), true);
            Reporter.log("DEBUG: names found only in Other tabs: " +
                    Arrays.toString(namesFromOtherCategory.toArray()),true);
            return false;
        }
        return true;
    }

    public int teamDepartmentsCount() {
        return teamPageFactory.teamDepartments.size();
    }

    public List<String> getDepartmentsFromDisplayedCategory() {
        List<String> departmentsFromDisplayedCategory = new ArrayList<String>();

        departmentsFromDisplayedCategory = getListOfStrings(teamPageFactory.displayedEmployeeDepartment);

        if (departmentsFromDisplayedCategory.isEmpty())
            Reporter.log("DEBUG: no employee department elements found", true);
        return departmentsFromDisplayedCategory;
    }

    public List<String> getNamesOfDepartments() {
        List<WebElement> listOfDepartments = teamPageFactory.teamDepartments;
        List<String> namesOfDpts = new ArrayList<>();
        for (WebElement wE : listOfDepartments) {
            namesOfDpts.add(wE.getText());
        }
        return namesOfDpts;
    }

    public Map<String, String> getNamesAndDepartmentsOfEmployees() {
        HashMap<String, String> nameDpt = new HashMap<String, String>();
        List<String> names = getNamesFromAllCategory();
        List<String> dpts = getDepartmentsFromDisplayedCategory();

        for (int i = 0; i < countEmployeeCardsDisplayed(); i++) {
            nameDpt.put(names.get(i), dpts.get(i));
        }
        return nameDpt;
    }

    public Map<String, String> getNamesAndDptsOfEmployeesFromOtherCategories() {

        List<WebElement> teamCats = teamPageFactory.teamDepartments;
        int qtyCategories = teamCats.size();
        Map<String, String> otherEmployeesNamesDpts = new HashMap<String, String>();

        for (int i = 1; i < qtyCategories; i++) {
            teamCats.get(i).click();
            otherEmployeesNamesDpts.putAll(getNamesAndDepartmentsOfEmployees());
        }
        return otherEmployeesNamesDpts;
    }

    public boolean dptMatchAllOtherTabs(Map<String, String> map1, Map<String, String> map2) {
        if(map1.size() != map2.size())
            Reporter.log("DEBUG: number of employees don't match between all tab and other tabs", true);
        List<String> nonMatchingDptEmployees = new ArrayList<String>();
        for (String name: map1.keySet()) {
            if (!map1.get(name).equalsIgnoreCase(map2.get(name))) {
                nonMatchingDptEmployees.add(name);
            }
        }
        if (nonMatchingDptEmployees.size() > 0) {
            Reporter.log("DEBUG: names of employees who's department value don't match" +
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

    public boolean displayedCategoryEmployeeImageMatch(List<String> names, List<String> images) {

        List<String> nonMatchingRecords = new ArrayList<>();

        for (int i = 0; i < names.size(); i++) {

            String imageSource = images.get(i).toLowerCase();
            String imageFileName = createImageName(names.get(i).toLowerCase());

            System.out.println(imageFileName);
            System.out.println(imageSource);

            if (!imageSource.contains(imageFileName)) {
                nonMatchingRecords.add(names.get(i));
            }
        }
        if (nonMatchingRecords.size() > 0) {
            Reporter.log("DEBUG: Names of employees with non matching images" +
                    " or inconsistently named imageFiles: " +
                    Arrays.toString(nonMatchingRecords.toArray()), true);
            return false;
        }
        return true;
    }

    public String createImageName(String empName) {
        StringBuilder iN = new StringBuilder();

        String[] fnLn = empName.split(" ");
        iN.append(fnLn[0]);
        iN.append(fnLn[fnLn.length-1].substring(0, 1));
        return iN.toString().trim();
    }



}
