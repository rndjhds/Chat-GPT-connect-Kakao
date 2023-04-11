package com.example.translation;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.StandardCharsets;

@Component
public class TranslatorEnToKo {

    private static final String PAPAGO_API_URL = "https://openapi.naver.com/v1/papago/n2mt";

    private final String clientId;

    private final String clientSecret;

    public TranslatorEnToKo(String clientId, String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public String translateToKorean(String text) {
        String requestUrl = UriComponentsBuilder.fromHttpUrl(PAPAGO_API_URL)
                .queryParam("source", "en")
                .queryParam("target", "ko")
                .queryParam("text", text)
                .build()
                .toUriString();

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/x-www-form-urlencoded");
        headers.set("X-Naver-Client-Id", clientId);
        headers.set("X-Naver-Client-Secret", clientSecret);

        HttpEntity<?> request = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(requestUrl, HttpMethod.POST, request, String.class);

        String responseBody = response.getBody();
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(responseBody).getAsJsonObject();
        String translatedText = jsonObject.getAsJsonObject("message").getAsJsonObject("result").get("translatedText").getAsString();

        return new String(translatedText.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
    }
}
