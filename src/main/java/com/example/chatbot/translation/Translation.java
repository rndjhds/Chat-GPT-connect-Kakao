package com.example.chatbot.translation;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

public class Translation {
    private static final String PAPAGO_API_URL = "https://openapi.naver.com/v1/papago/n2mt";
    private String clientId;
    private String clientSecret;

    public Translation(String clientId, String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public String translateTargetBySource(String text, String originalLanguage, String translationLanguage) {

        String encodedText = encodeText(text);

        String requestUrl = PAPAGO_API_URL + "?source=" + originalLanguage + "&target=" + translationLanguage + "&text=" + encodedText;
        String translatedText = findTranslatedText(requestUrl);

        String decodedText = decodeText(translatedText);

        return decodedText;
    }

    public String findTranslatedText(String requestUrl) {

        String responseBody = forwardToPapago(requestUrl, clientId, clientSecret);
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        JsonObject messageObject = jsonObject.getAsJsonObject("message");
        String translatedText = messageObject.getAsJsonObject("result").get("translatedText").getAsString();

        return translatedText;
    }

    public String forwardToPapago(String requestUrl, String clientId, String clientSecret) {

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> httpEntity = createHttpEntity(clientId, clientSecret);
        ResponseEntity<String> responseEntity = restTemplate.exchange(requestUrl, HttpMethod.POST, httpEntity, String.class);
        String responseBody = responseEntity.getBody();

        return responseBody;
    }

    public HttpEntity<String> createHttpEntity(String clientId, String clientSecret) {

        HttpHeaders headers = createHttpHeaders(clientId, clientSecret);
        HttpEntity<String> httpEntity = new HttpEntity("", headers);

        return httpEntity;
    }

    public HttpHeaders createHttpHeaders(String clientId, String clientSecret) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/x-www-form-urlencoded");
        headers.set("X-Naver-Client-Id", clientId);
        headers.set("X-Naver-Client-Secret", clientSecret);

        return headers;
    }

    public String encodeText(String text) {

        String encodedText = new String(text.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);

        return encodedText;
    }

    public String decodeText(String text) {

        String decodedText = new String(text.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);

        return decodedText;
    }
}