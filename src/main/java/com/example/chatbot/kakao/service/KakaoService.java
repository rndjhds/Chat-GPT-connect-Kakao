package com.example.chatbot.kakao.service;

import com.example.chatbot.chatgpt.service.ChatService;
import com.example.chatbot.kakao.model.KakaoRequest;
import com.example.chatbot.kakao.model.KakaoResponse;
import com.example.chatbot.kakao.model.KakaoTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class KakaoService {

    private final ChatService chatService;

    public KakaoService(ChatService chatService) {
        this.chatService = chatService;
    }

    public KakaoResponse createKakaoResponse(KakaoRequest kakaoRequest) {

        List<KakaoTemplate> contents = new ArrayList<>();
        KakaoTemplate template = new KakaoTemplate();
        String simpleText = loadSimpleTextByUtterance(createUtterance(kakaoRequest));
        template.addSimpleTextOutput(simpleText);
        contents.add(template);

        return new KakaoResponse(template);
    }

    public String createUtterance(KakaoRequest kakaoRequest) {

        return kakaoRequest.getUserRequest().getUtterance();
    }

    public String loadSimpleTextByUtterance(String utterance) {

        return chatService.createSimpleTextBytext(utterance);
    }

}
