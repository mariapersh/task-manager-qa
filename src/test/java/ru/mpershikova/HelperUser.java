package ru.mpershikova;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.SneakyThrows;

public class HelperUser {

    @SneakyThrows
    public static JsonNode createUser() {
        // Генерируем случайные email и имя
        String email = UserTestDataGenerator.generateEmail();
        String fullName = UserTestDataGenerator.generateFullName();

        // Выводим в консоль для отладки
        System.out.println("Создаём пользователя: ");
        System.out.println("Email: " + email);
        System.out.println("Name: " + fullName);

        // Собираем JSON для отправки
        String jsonBody = String.format("""
                {
                "email": "%s",
                "fullName": "%s"
                }
                """, email, fullName);

        // Отправляем POST запрос и возвращаем ответ
        return HttpHelper.doPost("http://localhost:8080/api/v1/users", jsonBody);
    }

    @SneakyThrows
    public static long createUserAndGetId() {
        return createUser().get("id").asLong();
    }
}
