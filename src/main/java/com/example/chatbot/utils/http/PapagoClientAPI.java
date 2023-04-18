package com.example.chatbot.utils.http;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

@ConfigurationProperties(prefix = "papago")
@Slf4j
public class PapagoClientAPI implements ClientAPI {

    @Value("${papago.client_id}")
    private String clientId;

    @Value("${papago.client_secret}")
    private String clientSecret;

    @Value("${papago.url}")
    private String papagoApiUrl;

    public String translateTargetBySource(String text, String originalLanguage, String translationLanguage) {

        String encodedText = encodeText(text);

        String requestUrl = papagoApiUrl + "?source=" + originalLanguage + "&target=" + translationLanguage + "&text=" + encodedText;
        String translatedText = receiveFromAPI(requestUrl);

        String decodedText = decodeText(translatedText);

        return decodedText;
    }

    public String encodeText(String text) {

        String encodedText = new String(text.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);

        return encodedText;
    }

    public String decodeText(String text) {

        String decodedText = new String(text.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);

        return decodedText;
    }

    @Override
    public HttpHeaders createHttpHeaders() {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/x-www-form-urlencoded");
        headers.set("X-Naver-Client-Id", clientId);
        headers.set("X-Naver-Client-Secret", clientSecret);

        return headers;
    }

    @Override
    public HttpEntity<String> createHttpEntity(String body) {

        HttpHeaders headers = createHttpHeaders();
        HttpEntity<String> httpEntity = new HttpEntity(body, headers);

        return httpEntity;
    }

    @Override
    public String forwardToAPI(String body) {

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> httpEntity = createHttpEntity("");
        ResponseEntity<String> responseEntity = restTemplate.exchange(body, HttpMethod.POST, httpEntity, String.class);

        return responseEntity.getBody();
    }

    @Override
    public String receiveFromAPI(String response) {

        String responseBody = forwardToAPI(response);
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        JsonObject messageObject = jsonObject.getAsJsonObject("message");
        String translatedText = messageObject.getAsJsonObject("result").get("translatedText").getAsString();

        return translatedText;
    }
}
