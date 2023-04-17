package com.example.chatbot.service;

import com.example.chatbot.chatgpt.ChatCompletionRequest;
import com.example.chatbot.chatgpt.ChatMessage;
import com.example.chatbot.repository.ChatMessageRepository;
import com.example.chatbot.translation.Translation;
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
public class ChatService {

    @Value("${open_ai_key}")
    private String openAiKey;
    @Value("${translator_client_id}")
    private String clientId;
    @Value("${translator_client_secret}")
    private String clientSecret;

    private final ChatMessageRepository chatMessageRepository;

    public ChatService(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    private static final String CHATGPT_API_URL = "https://api.openai.com/v1/chat/completions";

    public String createSimpleTextBytext(String text) {

        String translatedToEnglish = translateTargetBySource(text, "ko", "en");

        ResponseEntity<String> response = forwardToGPT(translatedToEnglish);
        String receiveFromGpt = receiveFromGpt(response);

        log.info("GPT 응답 데이터 : " + receiveFromGpt);

        String translatedToKorean = translateTargetBySource(receiveFromGpt, "en", "ko");

        return translatedToKorean;
    }

    private String translateTargetBySource(String text, String source, String target) {

        Translation translation = new Translation(clientId, clientSecret);
        String translatedText = translation.translateTargetBySource(text, source, target);

        return translatedText;
    }

    private ChatMessage add(String role, String content) {

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setRole(role);
        chatMessage.setContent(content);
        chatMessageRepository.add(chatMessage);

        return chatMessage;
    }

    private String receiveFromGpt(ResponseEntity<String> response) {

        String responseBody = response.getBody();
        JsonArray jsonArray = responseBodyToJsonArray(responseBody);
        StringBuilder sb = findContent(jsonArray);
        ChatMessage chatMessage = add("assistant", sb.toString());

        return chatMessage.getContent();
    }

    private StringBuilder findContent(JsonArray jsonArray) {

        StringBuilder sb = new StringBuilder();
        for (JsonElement element : jsonArray) {
            JsonElement gptResponseMessage = element.getAsJsonObject().get("message");
            String content = gptResponseMessage.getAsJsonObject().get("content").getAsString();
            sb.append(content.replaceAll("\\n", ""));
        }

        return sb;
    }

    private JsonArray responseBodyToJsonArray(String responseBody) {

        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(responseBody).getAsJsonObject();
        JsonArray choicesArray = jsonObject.getAsJsonArray("choices");

        return choicesArray;
    }

    private ResponseEntity<String> forwardToGPT(String content) {

        ChatMessage chatMessage = add("user", content);

        HttpEntity<String> request = createHttpEntity();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(CHATGPT_API_URL, HttpMethod.POST, request, String.class);

        return responseEntity;
    }

    private HttpEntity<String> createHttpEntity() {

        HttpHeaders headers = createHttpHeaders();
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.create("gpt-3.5-turbo", chatMessageRepository);
        String body = chatCompletionRequest.toJsonString();
        log.info("GPT body {}", body);

        HttpEntity<String> httpEntity = new HttpEntity(body, headers);

        return httpEntity;
    }

    private HttpHeaders createHttpHeaders() {

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(openAiKey);
        headers.set("Content-Type", "application/json");

        return headers;
    }
}

