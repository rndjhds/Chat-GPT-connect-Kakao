package com.example.chatbot.service;

import com.example.chatbot.kakao.KakaoRequest;
import com.example.chatbot.kakao.KakaoResponse;
import com.example.chatbot.kakao.KakaoTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class KakaoService {

    private final ChatBotService chatBotService;

    public KakaoService(final ChatBotService chatBotService) {
        this.chatBotService = chatBotService;
    }

    public KakaoResponse send(final KakaoRequest kakaoRequest) {

        List<KakaoTemplate> contents = new ArrayList<>();
        KakaoTemplate template = new KakaoTemplate();
        String text = this.sendRequestToChatGPT(createUtterance(kakaoRequest));
        template.addSimpleTextOutput(text);
        contents.add(template);

        return new KakaoResponse(template);
    }

    public String createUtterance(KakaoRequest kakaoRequest) {

        return kakaoRequest.getUserRequest().getUtterance();
    }

    public String sendRequestToChatGPT(String utterance) {

        return chatBotService.sendRequestToChatGPT(utterance);
    }

}
