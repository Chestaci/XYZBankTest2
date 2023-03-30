package com.github.Chestaci;


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
import java.util.List;

@DisplayName("Тесты поиска клиентов")
@Feature("Тесты проверки поиска клиентов")
public class CustomerSearchTest{

    private Manager manager;
    private ListCust listCust;

    @Step("Настройки перед началом тестов." +
            " Создание начальной страницы, ее открытие и нажатие на кнопку списка клиентов")
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
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        //инициализация начальной страницы
        manager = new Manager(driver);
        //получение ссылки на страницу входа из файла настроек
        driver.get(ConfProperties.getProperty("manager_page"));
        //Нажатие на кнопку списка клиентов
        listCust = manager.clickCustomersButton();
        return driver;
    }



    /**
     * тестовый метод для осуществления проверки успешного поиска клиентов по совпадению букв
     */
    @Test
    @DisplayName("Проверка успешного поиска клиентов по совпадению букв")
    @Description("Тест для осуществления проверки поиска клиентов")
    @Story("Тест поиска клиентов")
    public void searchByMatchingLettersTest() {
        WebDriver driver = setup();
        String search = ConfProperties.getProperty("search_customer1");
        //Ввод данных для поиска клиентов
        listCust.inputSearchCustomer(search);
        //Получение списока строк в таблице с клиентами по заданным параметрам поиска
        List<String> customersList = listCust.checkSearchList();

        boolean b = false;
        for (String s:customersList) {
            if(s.contains(search)){
                b = true;
            }else{
                b = false;
                return;
            }
        }

        Assertions.assertTrue(b);

        listCust.clearSearchField();

        driver.quit();
    }



    /**
     * тестовый метод для осуществления проверки успешного поиска клиентов по фамилии
     */
    @Test
    @DisplayName("Проверка успешного поиска клиента по фамилии")
    @Description("Тест для осуществления проверки поиска клиентов")
    @Story("Тест поиска клиентов")
    public void searchByLastNameTest() {
        WebDriver driver = setup();
        String search = ConfProperties.getProperty("search_customer2");
        //Ввод данных для поиска клиентов
        listCust.inputSearchCustomer(search);
        //Получение списока строк в таблице с клиентами по заданным параметрам поиска
        List<String> customersList = listCust.checkSearchList();

        boolean b = false;
        for (String s:customersList) {
            if(s.contains(search)){
                b = true;
            }else{
                b = false;
                return;
            }
        }

        Assertions.assertTrue(b);

        listCust.clearSearchField();

        driver.quit();
    }



    /**
     * тестовый метод для осуществления проверки успешного поиска клиентов по совпадению циф
     */
    @Test
    @DisplayName("Проверка успешного поиска клиента по совпадению цифр")
    @Description("Тест для осуществления проверки поиска клиентов")
    @Story("Тест поиска клиентов")
    public void searchByNumberTest() {
        WebDriver driver = setup();
        String search = ConfProperties.getProperty("search_customer3");
        //Ввод данных для поиска клиентов
        listCust.inputSearchCustomer(search);
        //Получение списока строк в таблице с клиентами по заданным параметрам поиска
        List<String> customersList = listCust.checkSearchList();

        boolean b = false;
        for (String s:customersList) {
            if(s.contains(search)){
                b = true;
            }else{
                b = false;
                return;
            }
        }

        Assertions.assertTrue(b);

        listCust.clearSearchField();

        driver.quit();
    }


    /**
     * тестовый метод для осуществления проверки поиска несуществующего клиента
     */
    @Test
    @DisplayName("Проверка поиска несуществующего клиента")
    @Description("Тест для осуществления проверки поиска клиентов")
    @Story("Тест поиска клиентов")
    public void searchNonexistentCustomerTest() {
        WebDriver driver = setup();
        String search = ConfProperties.getProperty("search_customer4");
        //Ввод данных для поиска клиентов
        listCust.inputSearchCustomer(search);
        //Получение списока строк в таблице с клиентами по заданным параметрам поиска
        List<String> customersList = listCust.checkSearchList();

        Assertions.assertTrue(customersList.isEmpty());

        listCust.clearSearchField();

        driver.quit();
    }
}
