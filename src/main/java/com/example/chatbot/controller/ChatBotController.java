package com.example.chatbot.controller;

import com.example.chatbot.kakao.KakaoRequest;
import com.example.chatbot.kakao.KakaoResponse;
import com.example.chatbot.service.KakaoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ChatBotController {

    private final KakaoService kakaoService;

    public ChatBotController(final KakaoService kakaoService) {
        this.kakaoService = kakaoService;
    }

    @PostMapping(value = { "/chatbot" }, produces = { "application/json" })
    public KakaoResponse handleRequestFromKakao(@RequestBody final KakaoRequest kakaoRequest) {

        ChatBotController.log.info("kakaoRequest data = {}", kakaoRequest.toString());
        final KakaoResponse kakaoResponse = this.kakaoService.send(kakaoRequest);

        return kakaoResponse;
    }
}