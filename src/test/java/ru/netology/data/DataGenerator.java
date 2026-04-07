package ru.netology.data;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class DataGenerator {
    private static final Faker FAKER = new Faker(new Locale("ru"));
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final List<String> CITIES = List.of(
            "Москва", "Санкт-Петербург", "Казань", "Екатеринбург", "Самара",
            "Нижний Новгород", "Новосибирск", "Краснодар", "Воронеж", "Уфа"
    );

    private DataGenerator() {
    }

    public static RegistrationInfo generateRegistrationInfo() {
        String city = CITIES.get(FAKER.random().nextInt(CITIES.size()));
        String name = FAKER.name().lastName() + " " + FAKER.name().firstName();
        String phone = "+79" + FAKER.number().digits(9);
        return new RegistrationInfo(city, name, phone);
    }

    public static String generateDate(int daysToAdd) {
        return LocalDate.now().plusDays(daysToAdd).format(DATE_FORMATTER);
    }
}