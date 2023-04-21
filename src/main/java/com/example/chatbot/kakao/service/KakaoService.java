package com.example.chatbot.kakao.service;

import com.example.chatbot.chatgpt.service.ChatService;
import com.example.chatbot.kakao.model.CallBackRequest;
import com.example.chatbot.kakao.model.KakaoRequest;
import com.example.chatbot.kakao.model.KakaoResult;
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

    public CallBackRequest createKakaoResponse(KakaoRequest kakaoRequest) {

        List<KakaoTemplate> contents = new ArrayList<>();
        KakaoTemplate template = new KakaoTemplate();
        String simpleText = createSimpleText(createUtterance(kakaoRequest));
        template.addSimpleTextOutput(simpleText);
        contents.add(template);

        KakaoResult kakaoResult = new KakaoResult(template);
        return new CallBackRequest(kakaoResult.getVersion(), kakaoResult.getTemplate());

    }

    public String createUtterance(KakaoRequest kakaoRequest) {

        return kakaoRequest.getUserRequest().getUtterance();
    }

    public String createSimpleText(String utterance) {

        return chatService.createSimpleTextBytext(utterance);
    }

}
