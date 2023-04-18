package com.example.chatbot.chatgpt.service;

import com.example.chatbot.chatgpt.model.ChatCompletionRequest;
import com.example.chatbot.chatgpt.model.ChatMessage;
import com.example.chatbot.chatgpt.repository.ChatMessageRepository;
import com.example.chatbot.utils.http.GptClientAPI;
import com.example.chatbot.utils.http.PapagoClientAPI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ChatService {

    private final ChatMessageRepository chatMessageRepository;

    private final GptClientAPI gptClientAPI;

    private final PapagoClientAPI papagoClientAPI;

    public ChatService(ChatMessageRepository chatMessageRepository,
                       GptClientAPI gptClientAPI, PapagoClientAPI papagoClientAPI) {
        this.chatMessageRepository = chatMessageRepository;
        this.gptClientAPI = gptClientAPI;
        this.papagoClientAPI = papagoClientAPI;

    }

    public String createSimpleTextBytext(String text) {

        log.info("요청 데이터 : {}", text);

        String translatedToEnglish = papagoClientAPI.translateTargetBySource(text, "ko", "en");
        log.info("TranslationEnglish {}", translatedToEnglish);
        add("user", translatedToEnglish);

        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.create("gpt-3.5-turbo", chatMessageRepository);
        String body = chatCompletionRequest.toJsonString();
        log.info("GPT 요청 데이터 : {}", body);

        String response = gptClientAPI.forwardToAPI(body);
        String receiveFromGpt = gptClientAPI.receiveFromAPI(response);

        log.info("GPT 응답 데이터 : {}", receiveFromGpt);

        String translatedToKorean = papagoClientAPI.translateTargetBySource(receiveFromGpt, "en", "ko");
        add("assistant", translatedToKorean);
        log.info("GPT TranslationToKorean : {}", translatedToKorean);

        return translatedToKorean;
    }

    private void add(String role, String content) {

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setRole(role);
        chatMessage.setContent(content);
        chatMessageRepository.add(chatMessage);

    }
}

