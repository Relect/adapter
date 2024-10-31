package com.hawkingbros.adapter.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.hawkingbros.adapter.exception.MsgException;
import com.hawkingbros.adapter.meteoSource.Meteo;
import com.hawkingbros.adapter.model.Coordinates;
import com.hawkingbros.adapter.model.MessageA;
import com.hawkingbros.adapter.model.MessageB;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Date;

@RestController
@RequiredArgsConstructor
public class AppController {

    private final Meteo meteo;
    private final String EXAMPLE_URL_B = "http://localhost:8185/to_b";

    @PostMapping(value = "/from_a", consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<MessageB> fromAToB(@RequestBody MessageA messageA) throws MsgException {

        String msg = messageA.getMsg();
        if (msg.isEmpty()) {
            throw new MsgException("Пустое значение msg");
        }
        String lng = messageA.getLng();
        if (!lng.equals("ru")) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        String longitude = messageA.getCoordinates().getLongitude();
        String latitude = messageA.getCoordinates().getLatitude();
        float lon = Float.parseFloat(longitude);
        float lat = Float.parseFloat(latitude);

        MessageB messageB = new MessageB(msg, new Date(), meteo.getTemp(lat, lon));
        String json;
        try {
            json = new ObjectMapper().writeValueAsString(messageB);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        sendToB(json);
        return new ResponseEntity<>(messageB, HttpStatus.OK);
    }


    private void sendToB(String s) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(s))
                .uri(URI.create(EXAMPLE_URL_B))
                .header("Accept", "application/json")
                .build();


        //    client.send(request, HttpResponse.BodyHandlers.ofString());

    }
}
