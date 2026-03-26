package ru.mpershikova;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserDeleteTest {


    @Test
    void deleteUserByIdTest() throws Exception {
        // Создаём пользователя
        long userId = HelperUser.createUserAndGetId();
        System.out.println("Создан пользователь с ID: " + userId);

        // Проверяем, что он есть в БД
        assertTrue(DbHelper.userExists(userId), "Пользователь с ID " + userId + " существует до удаления");

        // Удаляем пользователя
        HttpHelper.doDelete("http://localhost:8080/api/v1/users/" + userId);

        // Проверяем, что его нет в БД
        assertFalse(DbHelper.userExists(userId), "Пользователь удалён");

        System.out.println("✅ Тест удаления пройден!");
    }
}
