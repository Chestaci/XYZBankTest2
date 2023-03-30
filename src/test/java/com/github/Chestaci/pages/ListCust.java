package com.github.Chestaci.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Объект страницы со списком клиентов
 */

public class ListCust {

    private final WebDriver driver;

    /**
     * конструктор класса, занимающийся инициализацией полей класса
     */
    public ListCust(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    /**
     * определение локатора вкладки first name
     */
    @FindBy(xpath = "/html/body/div/div/div[2]/div/div[2]/div/div/table/thead/tr/td[1]/a")
    private WebElement firstNameTab;


    /**
     * определение локатора поля ввода для поиска книентов
     */
    @FindBy(xpath = "/html/body/div/div/div[2]/div/div[2]/div/form/div/div/input")
    private WebElement searchCustomerField;


    /**
     * определение локатора таблицы с клиентами
     */
    @FindBy(xpath = "/html/body/div/div/div[2]/div/div[2]/div/div/table/tbody")
    private WebElement tableCustomersList;


    /**
     * определение локатора поля кнопок удаления клиентов
     */
    @FindBy(xpath = "//*[contains(text(), 'Delete')]")
    private List<WebElement> deleteButtonList;


    /**
     * метод для осуществления проверки поиска клиентов
     * @return список строк в таблице с клиентами
     */
    @Step("Проверка поиска клиентов")
    public List<String> checkSearchList() {
        if (tableCustomersList.getText().isBlank()){
            return new ArrayList<>();
        }else {
            return new ArrayList<>(Arrays.asList(tableCustomersList.getText().split("\n")));
        }
    }


    /**
     * метод для осуществления очистки поля ввода для поиска клиентов
     */
    @Step("Очистка поля ввода для поиска клиентов")
    public void clearSearchField() {
        searchCustomerField.clear();
    }


    /**
     * метод для осуществления нажатия кнопки удаления клиента
     * @param number - порядковый номер клиента в диапазоне
     *               от 1 до deleteButtonList.size()
     * @throws ArrayIndexOutOfBoundsException бросает ошибку если
     *              не правильный number, 0 или больше чем deleteButtonList.size()
     */
    @Step("Нажатие на кнопку Delete клиента № number {number} на странице со списком клиентов")
    public void deleteCustomer(int number) throws ArrayIndexOutOfBoundsException{
        deleteButtonList.get(number-1).click();
      }


    /**
     * метод для осуществления нажатия кнопки удаления последнего добавленного клиента
     */
    @Step("Нажатие на кнопку Delete последнего добавленного клиента на странице со списком клиентов")
    public void deleteLastCustomer() {
        LinkedList<WebElement> linkedList = new LinkedList<>(deleteButtonList);
        linkedList.getLast().click();
    }


    /**
     * метод для осуществления нажатия на вкладку first name
     */
    @Step("Нажатие на вкладку first name")
    public void clickFirstNameTab() {
        firstNameTab.click();
      }


    /**
     * метод для ввода
     * @param searchCustomer Данные для поиска клиента
     */
    @Step("Заполнение поля ввода для поиска книентов: {searchCustomer}")
    public void inputSearchCustomer(String searchCustomer) {
        searchCustomerField.sendKeys(searchCustomer);
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(10));
    }

}