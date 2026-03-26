package ru.mpershikova;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.*;

public class UserCreationTest {

    @Test
    void createUserTest() throws Exception {
        // Создаём пользователя через HelperUser
        JsonNode jsonNode = HelperUser.createUser();

        // Получаем данные для проверки
        String email = jsonNode.get("email").asText();
        String fullName = jsonNode.get("fullName").asText();

        // Проверяем поля в ответе
        assertTrue(jsonNode.has("id"), "В ответе должен быть id");
        assertEquals(email, jsonNode.get("email").asText(), "Email должен совпадать");
        assertEquals(fullName, jsonNode.get("fullName").asText(), "Имя должно совпадать");
        assertTrue(jsonNode.has("createdAt"), "Должна быть дата создания");
        assertTrue(jsonNode.has("updatedAt"), "Должна быть дата обновления");

        System.out.println("✅ Все HTTP проверки пройдены!");

        // Проверяем данные в PostgreSQL через DbHelper
        try (ResultSet rs = DbHelper.getUserByEmail(email)) {
            assertTrue(rs.next(), "Пользователь должен быть в базе данных");

            System.out.println("Данные в БД:");
            System.out.println(" id: " + rs.getLong("id"));
            System.out.println(" email: " + rs.getString("email"));
            System.out.println(" full_name: " + rs.getString("full_name"));
            System.out.println(" created_at: " + rs.getTimestamp("created_at"));
            System.out.println(" updated_at: " + rs.getTimestamp("updated_at"));

            // Проверяем, что данные совпадают
            assertEquals(email, rs.getString("email"), "email в БД должен совпадать");
            assertEquals(fullName, rs.getString("full_name"), "Имя в БД должно совпадать");

            System.out.println("✅ Данные в БД совпадают с отправленными!");

        }
        System.out.println("🎉 Тест успешно завершён!");
    }
}



