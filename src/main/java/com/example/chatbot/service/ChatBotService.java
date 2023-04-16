package com.example.chatbot.service;

import com.example.chatbot.gpt.ChatGptRequestBody;
import com.example.chatbot.gpt.Message;
import com.example.chatbot.repository.MessageRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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

    private final MessageRepository messageRepository;

    public ChatBotService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    private static final String CHATGPT_API_URL = "https://api.openai.com/v1/chat/completions";

    public String sendRequestToChatGPT(String text) {

        ResponseEntity<String> response = forwardToGPT(text);
        String sb = receiveFromGpt(response);

        log.info("GPT 응답 데이터 : " + sb);

        return sb;
    }

    private Message addMessage(String text) {

        Message message = new Message();
        message.setRole("user");
        message.setContent(text);
        messageRepository.addMessage(message);

        return message;
    }

    private String receiveFromGpt(ResponseEntity<String> response) {

        String responseBody = response.getBody();
        JsonArray choicesArray = createJsonArray(responseBody);
        StringBuilder sb = createText(choicesArray);
        Message message = addMessage(sb.toString());

        return message.getContent();
    }

    private StringBuilder createText(JsonArray choicesArray) {

        StringBuilder sb = new StringBuilder();
        for (JsonElement element : choicesArray) {
            JsonElement gptResponseMessage = element.getAsJsonObject().get("message");
            String text = gptResponseMessage.getAsJsonObject().get("content").getAsString();
            sb.append(text.replaceAll("\\n", ""));
        }

        return sb;
    }

    private JsonArray createJsonArray(String responseBody) {

        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(responseBody).getAsJsonObject();
        JsonArray choicesArray = jsonObject.getAsJsonArray("choices");

        return choicesArray;
    }

    private ResponseEntity<String> forwardToGPT(String text) {

        Message message = addMessage(text);

        HttpEntity<String> request = createHttpEntity(message.getContent());
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(CHATGPT_API_URL, HttpMethod.POST, request, String.class);

        return response;
    }

    private HttpEntity<String> createHttpEntity(String message) {
        HttpHeaders headers = createHttpHeaders();
        ChatGptRequestBody requestBody = ChatGptRequestBody.create("gpt-3.5-turbo", messageRepository);
        String body = requestBody.toJsonString();
        log.info("GPT body {}", body);
        HttpEntity<String> request = new HttpEntity(body, headers);
        return request;
    }

    private HttpHeaders createHttpHeaders() {

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(this.openAiKey);
        headers.set("Content-Type", "application/json");

        return headers;
    }
}

