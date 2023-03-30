package com.github.Chestaci.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Объект страницы Manager
 */

public class Manager {

    private final WebDriver driver;

    /**
     * конструктор класса, занимающийся инициализацией полей класса
     */
    public Manager(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    /**
     * определение локатора поля кнопки добавления клиента
     */
    @FindBy(xpath = "/html/body/div/div/div[2]/div/div[1]/button[1]")
     private WebElement addCustomerButton;


    /**
     * определение локатора поля кнопки списка клиентов
     */
    @FindBy(xpath = "/html/body/div/div/div[2]/div/div[1]/button[3]")
    private WebElement customersButton;


    /**
     * метод для осуществления нажатия кнопки добавления клиента
     * @return возвращает страницу AddCust
     * @see AddCust
     */
    @Step("Нажатие на кнопку добавления клиента")
    public AddCust clickAddCustomerButton() {
        addCustomerButton.click();
        return new AddCust(this.driver);
    }

    /**
     * метод для осуществления нажатия кнопки списка клиентов
     * @return возвращает страницу ListCust
     * @see ListCust
     */
    @Step("Нажатие на кнопку списка клиентов")
    public ListCust clickCustomersButton() {
        customersButton.click();
        return new ListCust(this.driver);
    }
}
