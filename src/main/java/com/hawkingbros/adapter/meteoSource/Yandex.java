package com.hawkingbros.adapter.meteoSource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class Yandex implements Meteo {

    @Value("${key_yandex}")
    private String key;


    @Override
    public int getTemp(float lat, float lon) {

        int result;
        String url = "https://api.weather.yandex.ru/v2/forecast?lat=%.2f&lon=%.2f";
        url = String.format(url, lat, lon);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Accept", "application/json")
                .header("X-Yandex-Weather-Key", key)
                .GET()
                .build();

        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String jsonStr = response.body();

            ObjectMapper mapper = new ObjectMapper();

            JsonNode parent = mapper.readTree(jsonStr);
            JsonNode factNode = parent.path("fact");
            result = factNode.path("temp").asInt();

        } catch (JsonProcessingException e) {
            System.out.println("ошибка парсинга Yandex response");
            throw new RuntimeException(e);
        } catch (IOException | InterruptedException e) {
            System.out.println("ошибка отправки запроса");
            throw new RuntimeException(e); //todo
        }
        return result;
    }
}
