package com.github.Chestaci.utils;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Утилитный класс для работы с файлом настройки
 */
public class ConfProperties {

    protected static FileInputStream fileInputStream;
    protected static Properties PROPERTIES;

     /*
      Инициализация утилитного класса
     */
    static {
        try {
            FileReader fileReader = new FileReader("src/test/resources/conf.properties");
            //указание пути до файла с настройками
//            fileInputStream = new FileInputStream("src/test/resources/conf.properties");

            PROPERTIES = new Properties();
//            PROPERTIES.load(fileInputStream);
            PROPERTIES.load(fileReader);
        } catch (IOException e) {
            e.printStackTrace();
            //обработка возможного исключения (нет файла и т.п.)
        } finally {
            if (fileInputStream != null)
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    /**
     * метод для возврата строки со значением из файла с настройками
     * @param key ключ параметра из файла с настройками
     * @return возвращает значение ключа
     */
    public static String getProperty(String key) {
        return PROPERTIES.getProperty(key);
    }
}
