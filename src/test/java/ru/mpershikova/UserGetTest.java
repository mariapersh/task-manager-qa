package ru.mpershikova;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserGetTest {

    @Test
    void getUserByIdTest() throws Exception {

        try (ResultSet rs = DbHelper.getAnyUser()) {
            assertTrue(rs.next(), "В БД нет пользователей");

            long userId = rs.getLong("id");
            String dbEmail = rs.getString("email");
            String dbFullName = rs.getString("full_name");

            System.out.println("📦 Данные из БД:");
            System.out.println(" id: " + userId);
            System.out.println(" email: " + dbEmail);
            System.out.println(" full_name: " + dbFullName);

            // Получаем данные из API
            JsonNode jsonNode = HttpHelper.doGet("http://localhost:8080/api/v1/users/" + userId);


            // Сравниваем с данными из API с БД
            assertEquals(userId, jsonNode.get("id").asLong(), "ID должен совпадать с БД");
            assertEquals(dbEmail, jsonNode.get("email").asText(), "Email должен совпадать с БД");
            assertEquals(dbFullName, jsonNode.get("fullName").asText(), "Имя должно совпадать с БД");
            assertNotNull(jsonNode.get("createdAt").asText(), "Должна быть дата создания");
            assertNotNull(jsonNode.get("updatedAt").asText(), "Должна быть дата обновления");

            System.out.println("✅ Все проверки пройдены: данные из API совпадают с БД");
        }
    }
}
