package com.example.chatbot.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
@Slf4j
public class ChatBotService {

    @Value("${open_ai_key}")
    private String openAiKey;

    private static final String CHATGPT_API_URL = "https://api.openai.com/v1/chat/completions";

    // 응답에서 필요한 데이터만 리터
    public String giveBackFromGpt(String message) {

        return createContent(parsingBody(createResponseBody(stringResponseEntity(CHATGPT_API_URL,createEntity(
                createBody(message), createHeader(openAiKey, APPLICATION_JSON))))));

    }

    public String createContent(JsonArray jsonArray) {

        StringBuilder sb = new StringBuilder();
        for (JsonElement element : jsonArray) {
            JsonElement gptResponseMessage = element.getAsJsonObject().get("message");
            String text = gptResponseMessage.getAsJsonObject().get("content").getAsString();
            sb.append(text.replaceAll("\\n", ""));
        }

        return sb.toString();
    }

    public JsonArray parsingBody(String body) {

        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(body).getAsJsonObject();

        return jsonObject.getAsJsonArray("choices");
    }

    public String createResponseBody(ResponseEntity<String> responseEntity) {

        return responseEntity.getBody();
    }

    public ResponseEntity<String> stringResponseEntity(String URL, HttpEntity<String> httpEntity) {

        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.exchange(URL, HttpMethod.POST, httpEntity, String.class);
    }

    public HttpEntity<String> createEntity(String body, MultiValueMap<String, String> header) {

        return new HttpEntity<>(body, header);
    }

    public String createBody(String text) {

        return "{\"model\": \"gpt-3.5-turbo\", \"messages\": [{\"role\": \"user\", \"content\": \"" + text + "\"}]}";
    }

    public HttpHeaders createHeader(String key, MediaType mediaType) {

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(key);
        headers.setContentType(mediaType);

        return headers;
    }

    /*private String sendRequestToChatGPT(String message) {
        String model = "text-davinci-002";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(openAiKey);
        headers.set("Content-Type", "application/json");
        String body = "{\"model\": \"" + model + "\",\"prompt\": \"" + message + "\", \"max_tokens\":128}";
        HttpEntity<String> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.exchange(CHATGPT_API_URL, HttpMethod.POST, request, String.class);

        String responseBody = response.getBody();

        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(responseBody).getAsJsonObject();
        JsonArray choicesArray = jsonObject.getAsJsonArray("choices");

        StringBuilder sb = new StringBuilder();
        for (JsonElement element : choicesArray) {
            String text = element.getAsJsonObject().get("text").getAsString();
            sb.append(text.replaceAll("\\n", ""));
        }
        log.info("GPT 응답 데이터 : " + sb.toString());
        String translatedText = new TranslatorEnToKo(clientId, clientSecret).translateToKorean(sb.toString());
        log.info("한국어로 번역된 응답 데이터 : " + translatedText);

        return translatedText;
    }*/
}
