package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class DataGenerator {
    private DataGenerator() {
    }

    public static String generateDate(int shift) {
        LocalDate date = LocalDate.now().plusDays(shift);
        return date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static String generateCity(Faker faker) {
        List<String> cities = List.of(
                "Москва", "Санкт-Петербург", "Казань", "Екатеринбург", "Самара",
                "Нижний Новгород", "Новосибирск", "Краснодар", "Воронеж", "Уфа"
        );
        String city = cities.get(faker.random().nextInt(cities.size()));
        return city;
    }

    public static String generateName(Faker faker) {
        String name = faker.name().lastName() + " " + faker.name().firstName();
        return name;
    }

    public static String generatePhone(Faker faker) {
        String phone = "+79" + faker.number().digits(9);
        return phone;
    }

    public static class Registration {
        private static Faker faker;

        private Registration() {
        }

        public static UserInfo generateUser(String locale) {
            faker = new Faker(new Locale(locale));
            UserInfo user = new UserInfo(
                    generateCity(faker),
                    generateName(faker),
                    generatePhone(faker)
            );
            return user;
        }
    }

    @Value
    public static class UserInfo {
        String city;
        String name;
        String phone;
    }
}