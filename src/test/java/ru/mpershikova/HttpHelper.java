package ru.mpershikova;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.BasicHttpClientResponseHandler;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpResponse;
import lombok.SneakyThrows;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.StringEntity;

public class HttpHelper {

    private static final CloseableHttpClient httpClient = HttpClients.createDefault();
    private static final ObjectMapper mapper = new ObjectMapper();

    @SneakyThrows
    public static JsonNode doGet(String uri) throws Exception {
        // Создаём GET запрос
        HttpGet getRequest = new HttpGet(uri);
        getRequest.setHeader("Accept", "*/*");

        // Отправляем запрос и получаем полный ответ
        ClassicHttpResponse httpResponse = httpClient.execute(getRequest);

        // Получаем тело ответа
        String response = new BasicHttpClientResponseHandler().handleResponse(httpResponse);
        System.out.println("Ответ от API: " + response);

        // Парсим JSON ответ
        return mapper.readTree(response);
    }

    @SneakyThrows
    public static JsonNode doPost(String uri, String body) throws Exception {
        // Создаём POST запрос
        HttpPost postRequest = new HttpPost(uri);
        postRequest.setHeader("Accept", "*/*");
        postRequest.setHeader("Content-Type", "application/json");

        // Добавляем тело запроса
        postRequest.setEntity(new StringEntity(body, ContentType.APPLICATION_JSON));

        // Отправляем запрос и получаем полный ответ
        ClassicHttpResponse httpResponse = httpClient.execute(postRequest);

        // Получаем тело ответа
        String response = new BasicHttpClientResponseHandler().handleResponse(httpResponse);
        System.out.println("Ответ от API: " + response);

        // Парсим JSON ответ
        return mapper.readTree(response);
    }

    @SneakyThrows
    public static void doDelete(String uri) throws Exception {
        // Создаём DELETE запрос
        HttpDelete deleteRequest = new HttpDelete(uri);
        deleteRequest.setHeader("Accept", "*/*");

        // Отправляем запрос и получаем полный ответ
        ClassicHttpResponse httpResponse = httpClient.execute(deleteRequest);

        int statusCode = httpResponse.getCode();
        System.out.println("Статус удаления: " + statusCode + " User deleted successfully");

        // Проверяем, что удаление прошло успешно (204 No Content или 200 OK)
        if (statusCode != 204) {
            String response = new BasicHttpClientResponseHandler().handleResponse(httpResponse);
            throw new RuntimeException("Ошибка удаления. Статус: " + statusCode + ", ответ: " + response);
        }
        System.out.println("✅ Пользователь успешно удалён");
    }
}
