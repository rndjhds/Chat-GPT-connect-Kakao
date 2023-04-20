package com.example.chatbot.utils.http;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@ConfigurationProperties(prefix = "open")
@Slf4j
public class GptClientAPI implements ClientAPI {

    @Value("${open.ai_key}")
    private String openAiKey;

    @Value("${open.chat_url}")
    private String chatGptApiUrl;

    @Override
    public HttpHeaders createHttpHeaders() {

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(openAiKey);
        headers.set("Content-Type", "application/json");

        return headers;
    }

    @Override
    public HttpEntity<String> createHttpEntity(String body) {

        HttpHeaders headers = createHttpHeaders();
        HttpEntity<String> httpEntity = new HttpEntity(body, headers);

        return httpEntity;
    }

    @Override
    public String forwardToAPI(String body) throws HttpClientErrorException {
        HttpEntity<String> request = createHttpEntity(body);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(chatGptApiUrl, HttpMethod.POST, request, String.class);
        return responseEntity.getBody();
    }


    @Override
    public String receiveFromAPI(String response) {

        String responseBody = response;
        JsonArray jsonArray = responseBodyToJsonArray(responseBody);
        StringBuilder sb = findContent(jsonArray);

        return sb.toString();
    }

    public StringBuilder findContent(JsonArray jsonArray) {

        StringBuilder sb = new StringBuilder();
        for (JsonElement element : jsonArray) {
            JsonElement gptResponseMessage = element.getAsJsonObject().get("message");
            String content = gptResponseMessage.getAsJsonObject().get("content").getAsString();
            sb.append(content.replaceAll("\\n", ""));
        }

        return sb;
    }

    public JsonArray responseBodyToJsonArray(String responseBody) {

        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(responseBody).getAsJsonObject();
        JsonArray choicesArray = jsonObject.getAsJsonArray("choices");

        return choicesArray;
    }
}
