package com.example.chatbot.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

@Service
public class TranslationService {

    @Value("${translator_client_id}")
    private String clientId;

    @Value("${translator_client_secret}")
    private String clientSecret;

    private static final String PAPAGO_API_URL = "https://openapi.naver.com/v1/papago/n2mt";

    public String translateToEnglish(String text) {

        return decodingText(createTranslateLanguage(parsingBody(createResponseBody(stringResponseEntity(
                createPapagoApi(encodingText(text), "en", "ko"), createEntity(
                        createHeader(MediaType.APPLICATION_FORM_URLENCODED)))))));
    }

    /*public String translateToKorean(String text) {
        String encodedText = encodingText(text);
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
    }*/

    private String encodingText(String text) {

        byte[] encodedTextBytes = text.getBytes(StandardCharsets.UTF_8);
        String encodedText = new String(encodedTextBytes, StandardCharsets.UTF_8);

        return encodedText;
    }

    private String createPapagoApi(String text, String source, String target) {

        String requestUrl = PAPAGO_API_URL + "?source=" + source + "&target=" + target + "&text=" + text;

        return requestUrl;
    }

    private HttpHeaders createHeader(MediaType mediaType) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);
        headers.set("X-Naver-Client-Id", clientId);
        headers.set("X-Naver-Client-Secret", clientSecret);

        return headers;
    }

    private HttpEntity<String> createEntity(MultiValueMap<String, String> header) {

        return new HttpEntity<>("", header);
    }

    private ResponseEntity<String> stringResponseEntity(String URL, HttpEntity<String> httpEntity) {

        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.exchange(URL, HttpMethod.POST, httpEntity, String.class);
    }

    private String createResponseBody(ResponseEntity<String> responseEntity) {

        return responseEntity.getBody();
    }

    private JsonObject parsingBody(String body) {

        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(body).getAsJsonObject();

        return jsonObject;
    }

    private String createTranslateLanguage(JsonObject jsonObject) {

        String translatedText = jsonObject.getAsJsonObject("message").getAsJsonObject("result").get("translatedText").getAsString();

        return translatedText;
    }

    private String decodingText(String text) {

        String decodedText = new String(text.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);

        return decodedText;
    }
}
