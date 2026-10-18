package dataProvider;

import org.testng.annotations.DataProvider;
import util.ExcelFileManager;

public class DataProviderTest {
    private final ExcelFileManager excelFileManager = new ExcelFileManager("src/main/resources/data.xlsx");
    @DataProvider(name = "firstFlow")
    public Object[][]getFirstFlowData(){
        return new Object[][] {excelFileManager.getRowValues(1,"firstScenario").toArray()};
    }
    @DataProvider(name = "shoppingListWithShipping")
    public Object[][]getShoppingListWithShipping(){
        return new Object[][] {{excelFileManager.getColumnValues(0,"shoppingList"),excelFileManager.getCellValue(1,2,"shoppingList")}};
    }
    @DataProvider(name = "shoppingListWithoutShipping")
    public Object[][]getShoppingListWithoutShipping(){
        return new Object[][] {{excelFileManager.getColumnValues(1,"shoppingList"),excelFileManager.getCellValue(1,2,"shoppingList")}};
    }
    @DataProvider(name="invalidSearch")
    public Object[][]getInvalidSearchQuery(){
        return new Object[][] {{excelFileManager.getCellValue(1,0,"invalidSearch")}};
    }
}
