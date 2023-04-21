package com.example.chatbot.chatgpt.controller;

import com.example.chatbot.kakao.model.KakaoRequest;
import com.example.chatbot.kakao.model.KakaoResponse;
import com.example.chatbot.kakao.service.KakaoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ChatController {

    private final KakaoService kakaoService;

    public ChatController(KakaoService kakaoService) {
        this.kakaoService = kakaoService;
    }

    @PostMapping("/chatbot")
    public KakaoResponse createKakaoResponse(@RequestBody KakaoRequest kakaoRequest) {

        log.info("kakaoRequest data = {}", kakaoRequest.toString());

        KakaoResponse kakaoResponse = kakaoService.createKakaoResponse(kakaoRequest);

        log.info("kakaoResponse data = {}", kakaoResponse.toString());

        return kakaoResponse;
    }
}