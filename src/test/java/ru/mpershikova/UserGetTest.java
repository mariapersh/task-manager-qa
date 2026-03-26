package ru.mpershikova;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserGetTest {

    private static final long TEST_USER_ID = 10L;

    @Test
    void getUserByIdTest() throws Exception {
        long userId = TEST_USER_ID;

        // Проверяем, что пользователь существует в БД
        assertTrue(DbHelper.userExists(userId), "Пользователь с ID " + userId + " не найден в БД");
        System.out.println("✅ Пользователь с ID " + userId + " существует в БД");

        // Получаем данные пользователя из БД для сравнения и сохраняет их в переменные
        String dbEmail = null;
        String dbFullName = null;

        try (ResultSet rs = DbHelper.getUserByIdTest(userId)) {
            assertTrue(rs.next(), "Пользователь должен быть в базе данных");
            dbEmail = rs.getString("email");
            dbFullName = rs.getString("full_name");

            System.out.println("📦 Данные из БД:");
            System.out.println(" id: " + userId);
            System.out.println(" email: " + dbEmail);
            System.out.println(" full_name: " + dbFullName);
        }

        // Получаем данные из API
        JsonNode jsonNode = HttpHelper.doGet("http://localhost:8080/api/v1/users/" + userId);


        // Сравниваем с данными из API с БД
        assertEquals(userId, jsonNode.get("id").asLong(), "ID должен совпадать с БД");
        assertEquals(dbEmail, jsonNode.get("email").asText(), "Email должен совпадать с БД");
        assertEquals(dbFullName, jsonNode.get("fullName").asText(),
                "Имя должно совпадать с БД");
        assertNotNull(jsonNode.get("createdAt").asText(), "Должна быть дата создания");
        assertNotNull(jsonNode.get("updatedAt").asText(), "Должна быть дата обновления");

        System.out.println("✅ Все проверки пройдены: данные из API совпадают с БД");
    }
}
