package ru.mpershikova;

import com.github.javafaker.Faker;

public class UserTestDataGenerator {
    private static final Faker faker = new Faker();

    public static String generateEmail() {
        return faker.internet().emailAddress();
    }

    public static String generateFullName() {
        return faker.name().fullName();
    }
}
