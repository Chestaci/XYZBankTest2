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
import java.util.*;
import java.util.stream.Collectors;

@DisplayName("Тесты сортировки клиентов")
@Feature("Тесты проверки сортировки клиентов")
public class SortTest{

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
        //инициализация начальной страницы
        manager = new Manager(driver);
        //получение ссылки на страницу входа из файла настроек
        driver.get(ConfProperties.getProperty("manager_page"));
        //Нажатие на кнопку списка клиентов
        listCust = manager.clickCustomersButton();
        return driver;
    }


    /**
     * тестовый метод для осуществления проверки успешной сортировки клиентов по имени
     * в обратном порядке
     */
    @Test
    @DisplayName("Проверка успешной сортировки клиентов по имени в обратном порядке")
    @Description("Тест для осуществления проверки успешной сортировки клиентов")
    @Story("Тест успешной сортировки клиентов")
    public void successfulReverseSortTest() {
        WebDriver driver = setup();
        //Получение списка клиентов и его сортировка в обратном порядке для сравнения с
        //конечным списком, который должен быть отсорирован нажатием на вкладку first name
        List<String> customersList = listCust.checkSearchList();
        Collections.sort(customersList);
        Collections.reverse(customersList);

        //Нажатие вкладку first name для сортировки клиентов по имени в обратном порядке
        listCust.clickFirstNameTab();

        //Получение списка клиентов, которые уже должны быть отсортированы
        //нажатием на вкладку first name
        List<String> customersList2 = listCust.checkSearchList();

        Assertions.assertEquals(customersList,customersList2);

        driver.quit();
    }


    /**
     * тестовый метод для осуществления проверки успешной сортировки клиентов по имени в алфавитном порядке
     */
    @Test
    @DisplayName("Проверка успешной сортировки клиентов по имени в алфавитном порядке")
    @Description("Тест для осуществления проверки успешной сортировки клиентов")
    @Story("Тест успешной сортировки клиентов")
    public void successfulSortTest() {
        WebDriver driver = setup();
        //Получение списка клиентов и его сортировка для сравнения с
        //конечным списком, который должен быть отсорирован нажатием на вкладку first name
        List<String> customersList = listCust.checkSearchList()
                .stream()
//                .map(s -> s.split(" ")[0])
                .sorted(String::compareToIgnoreCase)
                .collect(Collectors.toList());

        //Нажатие вкладку first name для сортировки клиентов по имени в обратном порядке
        listCust.clickFirstNameTab();

        //Повторное нажатие вкладку first name для сортировки клиентов по имени в алфавитном порядке
        listCust.clickFirstNameTab();

        //Получение списка клиентов, которые уже должны быть отсортированы
        //нажатием на вкладку first name
        List<String> customersList2 = listCust.checkSearchList();

       Assertions.assertEquals(customersList,customersList2);

        driver.quit();
    }
}
