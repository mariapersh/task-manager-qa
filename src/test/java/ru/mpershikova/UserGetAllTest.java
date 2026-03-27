package ru.mpershikova;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserGetAllTest {

    @Test
    void getAllUsersTest() throws Exception {
        // Убедиться, что БД не пустая
        long dbCount = DbHelper.getUsersCount();
        assertTrue(dbCount > 0, "В БД есть пользователи");

        String url = "http://localhost:8080/api/v1/users?page=0&size=1&sort=desc";
        JsonNode response = HttpHelper.doGet(url);

        System.out.println("Полный ответ API: " + response.toPrettyString());

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

        // Проверяем значения
        assertEquals(1, response.get("size").asInt(), "size должен быть 1");
        assertEquals(0, response.get("number").asInt(), "number должен быть 0");
        assertEquals(dbCount, response.get("totalElements").asLong(), "totalElements должно совпадать с БД");

        System.out.println("Все поля есть и значения совпадают с переданными");



    }
}
