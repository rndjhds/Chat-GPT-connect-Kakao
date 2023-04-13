package com.example.translation;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

public class TranslatorEnToKo {

    private static final String PAPAGO_API_URL = "https://openapi.naver.com/v1/papago/n2mt";

    private String clientId;

    private String clientSecret;

    public TranslatorEnToKo(String clientId, String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public String translateToKorean(String text) {
        byte[] encodedTextBytes = text.getBytes(StandardCharsets.UTF_8);
        String encodedText = new String(encodedTextBytes, StandardCharsets.UTF_8);
        String requestUrl = PAPAGO_API_URL + "?source=en&target=ko&text=" + encodedText;

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/x-www-form-urlencoded");
        headers.set("X-Naver-Client-Id", clientId);
        headers.set("X-Naver-Client-Secret", clientSecret);

        HttpEntity<String> request = new HttpEntity<>("", headers);
        ResponseEntity<String> response = restTemplate.exchange(requestUrl, HttpMethod.POST, request, String.class);

        String responseBody = response.getBody();
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(responseBody).getAsJsonObject();
        String translatedText = jsonObject.getAsJsonObject("message").getAsJsonObject("result").get("translatedText").getAsString();

        String decodedText = new String(translatedText.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);

        return decodedText;
    }
}
