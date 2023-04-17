package com.example.chatbot.service;

import com.example.translation.ConvertLanguage;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class ChatBotService {

    @Value("${open_ai_key}")
    private String openAiKey;

    @Value("${translator_client_id}")
    private String clientId;

    @Value("${translator_client_secret}")
    private String clientSecret;

    private static final String CHATGPT_API_URL = "https://api.openai.com/v1/chat/completions";

    public String sendRequestToChatGPT(String message) {

        //String translateToEnglish = new ConvertLanguage(clientId, clientSecret).convertToLanguage(message,"ko", "en");
        ResponseEntity<String> response = forwardToGPT(message);

        StringBuilder sb = receiveFromGpt(response);

        //String translatedText = new ConvertLanguage(clientId, clientSecret).convertToLanguage(sb.toString(),"en", "ko");
        log.info("GPT 응답 데이터 : " + sb.toString());
        //log.info("한국어로 번역된 응답 데이터 : " + translatedText);

        return sb.toString();
    }

    private static StringBuilder receiveFromGpt(ResponseEntity<String> response) {

        String responseBody = response.getBody();

        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(responseBody).getAsJsonObject();
        JsonArray choicesArray = jsonObject.getAsJsonArray("choices");

        StringBuilder sb = new StringBuilder();
        for (JsonElement element : choicesArray) {
            JsonElement gptResponseMessage = element.getAsJsonObject().get("message");
            String text = gptResponseMessage.getAsJsonObject().get("content").getAsString();
            sb.append(text.replaceAll("\\n", ""));
        }

        return sb;
    }

    private ResponseEntity<String> forwardToGPT(String message) {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(openAiKey);
        headers.set("Content-Type", "application/json");
        String body = "{\"model\": \"gpt-3.5-turbo\", \"messages\": [{\"role\": \"user\", \"content\": \"" + message + "\"}]}";
        HttpEntity<String> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.exchange(CHATGPT_API_URL, HttpMethod.POST, request, String.class);

        return response;
    }
}
