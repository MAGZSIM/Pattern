import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;


//Утилитный класс т.к. имеет приватный конструктор и статические методы, содержит логику подготовки и предоставления тестовых данных.
public class DataGenerator {
        private DataGenerator() {
        }

        public static String generateDate(int shift) {
            return LocalDate.now().plusDays(shift).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        }

        //public static String generateCity() {
            // var cities = new String [] { "Москва", "Санкт-Петербург", "Нижний-Новгород", "Уфа", "Екатеринбург", "Казань", "Ставрополь", "Ростов", "Саратов", "Самара"}; // массив валидных городов и класс Random
               // return cities [new Random().nextInt(cities.length)];
        //}

        public static String generateCityTwo(String locale) {
            var faker = new Faker (new Locale(locale));
            return faker.address().cityName();
            //не походит, т.к. генерирует города кот. не являются административными центрами
    }

        public static String generateName(String locale) {
            var faker = new Faker (new Locale(locale));
            return faker.name().lastName() + " " + faker.name().firstName();
        } // генерируется Имя и Фамилия и сшивается в строку

        public static String generatePhone(String locale) {
            var faker = new Faker(new Locale(locale));
            return faker.phoneNumber().phoneNumber();
        }

    // Вложенный класс в кот. реализован 1 метод собирающий город, имя и телефон:
        public static class Registration {
            private Registration() {
            }
            public static UserInfo generateUser(String locale) {
                return new UserInfo(generateCityTwo(locale), generateName(locale), generatePhone(locale));
            }
        }

    //Дата класс определяющий структуру объекта с данными, аннотация Value означает иммутабельный объект, в кот. нельзя изменить значение полей:
        @Value
        public static class UserInfo {
            String city;
            String name;
            String phone;
        }
    }

