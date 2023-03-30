package com.github.Chestaci;

import com.github.Chestaci.pages.AddCust;
import com.github.Chestaci.pages.ListCust;
import com.github.Chestaci.pages.Manager;
import com.github.Chestaci.utils.ConfProperties;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

@DisplayName("Тесты добавления клиента")
@Feature("Тесты проверки добавления клиента")

public class AdditionOfACustomerTest{

    private Manager manager;
    private AddCust addCust;


    @Step("Настройки перед началом тестов." +
          " Создание начальной страницы, ее открытие и нажатие на кнопку добавления клиента")

    public WebDriver setup() {
        WebDriver driver;
        //Инициализация менеджера WebDrivers
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("disable-infobars"); // disabling infobars
        options.addArguments("--disable-extensions"); // disabling extensions
        options.addArguments("--disable-gpu"); // applicable to windows os only
        options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");


        //создание экземпляра драйвера
        driver = new ChromeDriver(options);
        //окно разворачивается на полный экран
        driver.manage().window().maximize();
        //задержка на выполнение теста = 10 сек.
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));
        //инициализация начальной страницы
        manager = new Manager(driver);
        //получение ссылки на страницу входа из файла настроек
        driver.get(ConfProperties.getProperty("manager_page"));
        //Нажатие на кнопку добавления клиента
        addCust = manager.clickAddCustomerButton();
        return driver;
     }


    /**
     * метод для осуществляющий действия по заполнению полей формы параметрами
     * @param firstName Имя
     * @param lastName Фамилия
     * @param postCode Почтовый индекс,
     * а также нажатие кнопки добавления нового клиента
     */
    @Step("Заполнение полей ввода firstName: {firstName}," +
            " lastName: {lastName}, postCode: {postCode} и нажатие на кнопку добавления клиента")
    private void fillFieldsAndClick(WebDriver driver, String firstName, String lastName, String postCode) {
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));

        //Заполнение полей
        addCust.fillFieldsForCustomer(firstName, lastName, postCode);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));

        //Нажатие на кнопку добавления нового клиента
        addCust.clickAddNewCustomerButton();
    }

    @Step("Нажатие на кнопку списка клиентов и удаление последнего")
    private void deleteTheLastCustomer(WebDriver driver) {
        //инициализация страницы со списком клиентов
        ListCust listCust = new ListCust(driver);
        //Нажатие на кнопку списка клиентов
        manager.clickCustomersButton();
        //удаление последнего добавленного клиента
        listCust.deleteLastCustomer();
    }

    /**
     * тестовый метод для осуществления проверки успешного добавления клиента
     */
    @Test
    @DisplayName("Проверка успешного добавления клиента")
    @Description("Тест для осуществления проверки успешного добавления клиента")
    @Story("Тест успешного добавления клиента")
    public void successfulAdditionOfACustomerTest() {
        WebDriver driver = setup();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));
        fillFieldsAndClick(driver, ConfProperties.getProperty("first_name1"),
                ConfProperties.getProperty("last_name1"),
                ConfProperties.getProperty("post_code1"));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));

        String alertMessage = addCust.checkAlert();
        addCust.clickAcceptButton();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));

        Assertions.assertTrue(
                alertMessage.toUpperCase().contains(ConfProperties.getProperty("alert1").toUpperCase())
        );
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));
        deleteTheLastCustomer(driver);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));
        driver.quit();
  }


    /**
     * тестовый метод для осуществления проверки успешного добавления клиента с заполнением полей
     * русскими и латинскими буквами в разных регистрах
     */
    @Test
    @DisplayName("Проверка успешного создания клиента с заполнением полей" +
            "русскими и латинскими буквами в разных регистрах")
    @Description("Тест для осуществления проверки успешного добавления клиента")
    @Story("Тест успешного добавления клиента")
    public void successfulAdditionOfACustomerWithRussianAndLatinLettersInCamelCaseTest() {
        WebDriver driver = setup();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));
        fillFieldsAndClick(driver, ConfProperties.getProperty("first_name2"),
                ConfProperties.getProperty("last_name2"),
                ConfProperties.getProperty("post_code2"));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));

        String alertMessage = addCust.checkAlert();
        addCust.clickAcceptButton();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));


        Assertions.assertTrue(
                alertMessage.toUpperCase().contains(ConfProperties.getProperty("alert1").toUpperCase())
        );

        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));
        deleteTheLastCustomer(driver);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));
        driver.quit();
    }


    /**
     * тестовый метод для осуществления проверки успешного создания клиента
     * с заполнением полей одной цифрой
     */
    @Test
    @DisplayName("Проверка успешного создания клиента с заполнением полей одной цифрой")
    @Description("Тест для осуществления проверки успешного добавления клиента")
    @Story("Тест успешного добавления клиента")
    public void successfulAdditionOfACustomerWithOneDigitTest() {
        WebDriver driver = setup();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));
        fillFieldsAndClick(driver, ConfProperties.getProperty("first_name3"),
                ConfProperties.getProperty("last_name3"),
                ConfProperties.getProperty("post_code3"));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));

        String alertMessage = addCust.checkAlert();
        addCust.clickAcceptButton();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));

        Assertions.assertTrue(
                alertMessage.toUpperCase().contains(ConfProperties.getProperty("alert1").toUpperCase())
        );
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));
        deleteTheLastCustomer(driver);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));
        driver.quit();
    }


    /**
     * тестовый метод для осуществления проверки успешного создания клиента с
     * заполнением полей специальными символами
     */
    @Test
    @DisplayName("Проверка успешного создания клиента с заполнением полей специальными символами")
    @Description("Тест для осуществления проверки успешного добавления клиента")
    @Story("Тест успешного добавления клиента")
    public void successfulAdditionOfACustomerWithSpecialCharactersTest() {
        WebDriver driver = setup();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));
        fillFieldsAndClick(driver, ConfProperties.getProperty("first_name4"),
                ConfProperties.getProperty("last_name4"),
                ConfProperties.getProperty("post_code4"));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));

        String alertMessage = addCust.checkAlert();
        addCust.clickAcceptButton();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));


        Assertions.assertTrue(
                alertMessage.toUpperCase().contains(ConfProperties.getProperty("alert1").toUpperCase())
        );
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));
        deleteTheLastCustomer(driver);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));
        driver.quit();
    }


    /**
     * тестовый метод для осуществления проверки успешного создания клиента с
     * заполнением полей длинными значениями
     */
    @Test
    @DisplayName("Проверка успешного создания клиента с заполнением полей длинными значениями")
    @Description("Тест для осуществления проверки успешного добавления клиента")
    @Story("Тест успешного добавления клиента")
    public void successfulAdditionOfACustomerWithLongValuesTest() {
        WebDriver driver = setup();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));
        fillFieldsAndClick(driver, ConfProperties.getProperty("first_name5"),
                ConfProperties.getProperty("last_name5"),
                ConfProperties.getProperty("post_code5"));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));

        String alertMessage = addCust.checkAlert();
        addCust.clickAcceptButton();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));

        Assertions.assertTrue(
                alertMessage.toUpperCase().contains(ConfProperties.getProperty("alert1").toUpperCase())
        );
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));
        deleteTheLastCustomer(driver);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));

        driver.quit();
    }



    /**
     * тестовый метод для осуществления проверки успешного создания клиента с
     * заполнением полей буквами и цифрами
     */
    @Test
    @DisplayName("Проверка успешного создания клиента с заполнением полей буквами и цифрами")
    @Description("Тест для осуществления проверки успешного добавления клиента")
    @Story("Тест успешного добавления клиента")
    public void successfulAdditionOfACustomerWithLettersAndNumbersTest() {
        WebDriver driver = setup();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));
        fillFieldsAndClick(driver, ConfProperties.getProperty("first_name6"),
                ConfProperties.getProperty("last_name6"),
                ConfProperties.getProperty("post_code6"));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));
        String alertMessage = addCust.checkAlert();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));
        addCust.clickAcceptButton();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));
        Assertions.assertTrue(
                alertMessage.toUpperCase().contains(ConfProperties.getProperty("alert1").toUpperCase())
        );
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));
        deleteTheLastCustomer(driver);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));
        driver.quit();
    }



    /**
     * тестовый метод для осуществления проверки сообщения об ошибке при попытке добавления дубликата клиента
     */
    @Test
    @DisplayName("Проверка сообщения об ошибке при попытке добавления дубликата клиента")
    @Description("Тест для осуществления проверки сообщения об ошибке при неправильном добавлении клиента")
    @Story("Тест проверки сообщения об ошибке")
    public void checkingTheErrorMessageWithDuplicateClientTest() {
        WebDriver driver = setup();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));
        //создание первого клиента
        fillFieldsAndClick(driver, ConfProperties.getProperty("first_name1"),
                ConfProperties.getProperty("last_name1"),
                ConfProperties.getProperty("post_code1"));
        addCust.clickAcceptButton();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));
        //создание второго клиента с такими же данными
        fillFieldsAndClick(driver, ConfProperties.getProperty("first_name1"),
                ConfProperties.getProperty("last_name1"),
                ConfProperties.getProperty("post_code1"));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));
        String alertMessage = addCust.checkAlert();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));
        addCust.clickAcceptButton();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));
        Assertions.assertTrue(
                alertMessage.toUpperCase().contains(ConfProperties.getProperty("alert2").toUpperCase())
        );

        deleteTheLastCustomer(driver);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));
        driver.quit();
    }


    /**
     * тестовый метод для осуществления проверки сообщения об ошибке при попытке
     * создания клиента с пустым полем First Name
     */
    @Test
    @DisplayName("Проверка сообщения об ошибке при попытке создания клиента с пустым полем First Name")
    @Description("Тест для осуществления проверки сообщения об ошибке при неправильном добавлении клиента")
    @Story("Тест проверки сообщения об ошибке")
    public void checkingTheErrorMessageWithEmptyFirstNameTest() {
        WebDriver driver = setup();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
            fillFieldsAndClick(driver,"",
                ConfProperties.getProperty("last_name7"),
                ConfProperties.getProperty("post_code7"));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));
        String tooltipMessage = addCust.checkTooltipForFirstName();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));
        Assertions.assertTrue(
                tooltipMessage.toUpperCase().contains(ConfProperties.getProperty("tooltip").toUpperCase())
        );
        driver.quit();
      }


    /**
     * тестовый метод для осуществления проверки сообщения об ошибке при попытке
     * создания клиента с пустым полем Last Name
     */
    @Test
    @DisplayName("Проверка сообщения об ошибке при попытке создания клиента с пустым полем Last Name")
    @Description("Тест для осуществления проверки сообщения об ошибке при неправильном добавлении клиента")
    @Story("Тест проверки сообщения об ошибке")
    public void checkingTheErrorMessageWithEmptyLastNameTest() {
        WebDriver driver = setup();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));
        fillFieldsAndClick(driver, ConfProperties.getProperty("first_name8"),
                "",
                ConfProperties.getProperty("post_code8"));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));
        String tooltipMessage = addCust.checkTooltipForLastName();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));
        Assertions.assertTrue(
                tooltipMessage.toUpperCase().contains(ConfProperties.getProperty("tooltip").toUpperCase())
        );
        driver.quit();
    }


    /**
     * тестовый метод для осуществления проверки сообщения об ошибке при попытке
     * создания клиента с пустым полем Post Code
     */
    @Test
    @DisplayName("Проверка сообщения об ошибке при попытке создания клиента с пустым полем Post Code")
    @Description("Тест для осуществления проверки сообщения об ошибке при неправильном добавлении клиента")
    @Story("Тест проверки сообщения об ошибке")
    public void checkingTheErrorMessageWithEmptyPostCodeTest() {
        WebDriver driver = setup();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));
        fillFieldsAndClick(driver, ConfProperties.getProperty("first_name9"),
                ConfProperties.getProperty("last_name9"),""
                );
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));
        String tooltipMessage = addCust.checkTooltipForPostCode();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));
        Assertions.assertTrue(
                tooltipMessage.toUpperCase().contains(ConfProperties.getProperty("tooltip").toUpperCase())
        );
        driver.quit();
    }

    /**
     * тестовый метод для осуществления проверки сообщения об ошибке при попытке
     * создания клиента с заполнением полей пробелом
     */
    @Test
    @DisplayName("Проверка сообщения об ошибке при попытке создания клиента с заполнением полей пробелом")
    @Description("Тест для осуществления проверки сообщения об ошибке при неправильном добавлении клиента")
    @Story("Тест проверки сообщения об ошибке")
    public void checkingTheErrorMessageWithSpaceFieldsTest() {
        WebDriver driver = setup();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));

        fillFieldsAndClick(driver," ",
                " "," "
        );
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));
        String alertMessage = addCust.checkAlert();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));
        addCust.clickAcceptButton();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));
        Assertions.assertTrue(
                alertMessage.toUpperCase().contains(ConfProperties.getProperty("alert2").toUpperCase())
        );
        driver.quit();
    }
}
