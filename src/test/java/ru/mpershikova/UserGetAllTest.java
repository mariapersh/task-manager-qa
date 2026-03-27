package ru.mpershikova;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserGetAllTest {

    @Test
    void getAllUsersTest() throws Exception {
        // Убедиться, что БД не пустая
        long count = DbHelper.getUsersCount();
        assertTrue(count > 0, "В БД есть пользователи");

        String url = "http://localhost:8080/api/v1/users?page=0&size=1&sort=desc";
        JsonNode response = HttpHelper.doGet(url);

        // Проверяем, что есть все поля
        assertTrue(response.has("totalElements"), "Должно быть поле totalElements");
        assertTrue(response.has("totalPages"), "Должно быть поле totalPage");
        assertTrue(response.has("first"), "Должно быть поле first");
        assertTrue(response.has("last"), "Должно быть поле last");
        assertTrue(response.has("size"), "Должно быть поле size");
        assertTrue(response.has("content"), "Должен быть массив content");
        assertTrue(response.has("number"), "Должно быть поле number");
        assertTrue(response.has("sort"), "Должно быть поле sort");
        assertTrue(response.has("numberOfElements"), "Должно быть поле numberOfElements");
        assertTrue(response.has("pageable"), "Должно быть поле pageable");
        assertTrue(response.has("empty"), "Должно быть поле empty");

    }
}
