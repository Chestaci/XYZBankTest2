package com.github.Chestaci.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Объект страницы добавления клиента
 */

public class AddCust {

    private final WebDriver driver;
    private final WebDriverWait wait;

    /**
     * конструктор класса, занимающийся инициализацией полей класса
     */
    public AddCust(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * определение локатора поля ввода first name
     */
    @FindBy(xpath = "/html/body/div/div/div[2]/div/div[2]/div/div/form/div[1]/input")
    private WebElement firstNameField;

    /**
     * определение локатора поля ввода last name
     */
    @FindBy(xpath = "/html/body/div/div/div[2]/div/div[2]/div/div/form/div[2]/input")
    private WebElement lastNameField;

    /**
     * определение локатора поля ввода post code
     */
    @FindBy(xpath = "/html/body/div/div/div[2]/div/div[2]/div/div/form/div[3]/input")
    private WebElement postCodeField;

    /**
     * определение локатора поля кнопки добавления нового клиента
     */
    @FindBy(xpath = "/html/body/div/div/div[2]/div/div[2]/div/div/form/button")
    private WebElement addNewCustomerButton;


    /**
     * метод для ввода
     * @param firstName Имя
     */
    @Step("Заполнение поля firstName: {firstName} на странице добавления клиента")
    private void inputFirstName(String firstName) {
        wait.until(ExpectedConditions.visibilityOfAllElements(firstNameField));
        firstNameField.sendKeys(firstName);
    }

    /**
     * метод для ввода
     * @param lastName Фамилия
     */
    @Step("Заполнение поля lastName: {lastName} на странице добавления клиента")
    private void inputLastName(String lastName) {
        wait.until(ExpectedConditions.visibilityOfAllElements(lastNameField));
        lastNameField.sendKeys(lastName);
    }

    /**
     * метод для ввода
     * @param postCode Почтовый индекс
     */
    @Step("Заполнение поля postCode: {postCode} на странице добавления клиента")
    private void inputPostCode(String postCode) {
        wait.until(ExpectedConditions.visibilityOfAllElements(postCodeField));
        postCodeField.sendKeys(postCode);
    }


    /**
     * метод для осуществления проверки сообщения всплывающего окна
     * @return возвращает сообщения с предупреждением
     */
    @Step("Проверка сообщения с предупреждением")
    public String checkAlert() {
       Alert alert = driver.switchTo().alert();
       return alert.getText();
      }

    /**
     * метод для осуществления проверки сообщения подсказки для поля First Name
     * @return возвращает текст подсказки
     */
    @Step("Проверка текста подсказки")
    public String checkTooltipForFirstName(){
         return firstNameField.getAttribute("validationMessage");
    }

    /**
     * метод для осуществления проверки сообщения подсказки для поля First Name
     * @return возвращает текст подсказки
     */
    @Step("Проверка текста подсказки")
    public String checkTooltipForLastName(){
        return lastNameField.getAttribute("validationMessage");
    }

    /**
     * метод для осуществления проверки сообщения подсказки для поля Post Code
     * @return возвращает текст подсказки
     */
    @Step("Проверка текста подсказки")
    public String checkTooltipForPostCode(){
        return postCodeField.getAttribute("validationMessage");
    }


    /**
     * метод для подтверждения добавления нового клиента
     */
    @Step("Нажание на кнопку OK сообщения во всплывающем окне")
    public void clickAcceptButton() {
        driver.switchTo().alert().accept();
    }


    /**
     * метод для осуществления нажатия на кнопку добавления нового клиента
     */
    @Step("Нажатие на кнопку добавления нового клиента")
    public void clickAddNewCustomerButton() {
        addNewCustomerButton.click();
    }


    /**
     * метод для осуществляющий действия по заполнению полей формы параметрами
     * @param firstName Имя
     * @param lastName Фамилия
     * @param postCode Почтовый индекс
     */
    @Step("Заполнение полей firstName: {firstName}, lastName: {lastName}, postCode: {postCode}")
    public void fillFieldsForCustomer(String firstName, String lastName, String postCode){
        inputFirstName(firstName);
        inputLastName(lastName);
        inputPostCode(postCode);
    }

}
