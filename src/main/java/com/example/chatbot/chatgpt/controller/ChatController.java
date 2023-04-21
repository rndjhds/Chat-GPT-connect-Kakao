package com.example.chatbot.chatgpt.controller;

import com.example.chatbot.kakao.model.CallBackRequest;
import com.example.chatbot.kakao.model.CallBackResult;
import com.example.chatbot.kakao.model.KakaoRequest;
import com.example.chatbot.kakao.model.KakaoResult;
import com.example.chatbot.kakao.service.KakaoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
public class ChatController {

    private final KakaoService kakaoService;

    public ChatController(KakaoService kakaoService) {
        this.kakaoService = kakaoService;
    }

    @PostMapping("/chatbot")
    public CallBackResult createKakaoResponse(@RequestBody KakaoRequest kakaoRequest) {

        log.info("kakaoRequest data = {}", kakaoRequest.toString());

        CallBackRequest callBackRequest = kakaoService.createKakaoResponse(kakaoRequest);

        log.info("callBackRequest data = {}", callBackRequest.toString());

        RestTemplate restTemplate = new RestTemplate();
        CallBackResult callBackResult = (CallBackResult) restTemplate.postForObject(kakaoRequest.getUserRequest().getCallbackUrl(), callBackRequest, Object.class);

        return callBackResult;
    }
}