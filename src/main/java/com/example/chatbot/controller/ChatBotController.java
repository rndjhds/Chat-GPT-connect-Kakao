package com.example.chatbot.controller;

import com.example.chatbot.kakao.KakaoRequest;
import com.example.chatbot.kakao.KakaoResponse;
import com.example.chatbot.kakao.KakaoTemplate;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class ChatBotController {

    @Value("${open_ai_key}")
    private String openAiKey;

//    @Value("${translator_client_id}")
//    private String clientId;
//
//    @Value("${translator_client_secret}")
//    private String clientSecret;

    private static final String CHATGPT_API_URL = "https://api.openai.com/v1/completions";

    @PostMapping(value = "/chatbot", produces = MediaType.APPLICATION_JSON_VALUE)
    public KakaoResponse handleRequestFromKakao(@RequestBody KakaoRequest request) {

        log.info("요청 데이터 : " + request.getUserRequest().getUtterance());
        String chatGptResponse = sendRequestToChatGPT(request.getUserRequest().getUtterance());

        List<KakaoTemplate> contents = new ArrayList<>();

        KakaoTemplate template = new KakaoTemplate();
        template.addSimpleTextOutput(chatGptResponse);
        contents.add(template);

        return new KakaoResponse(template);
    }



    private String sendRequestToChatGPT(String message) {
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
        //String translatedText = new TranslatorEnToKo(clientId, clientSecret).translateToKorean(sb.toString());
        //log.info("한국어로 번역된 응답 데이터 : " + translatedText);

        return sb.toString();
    }

}
