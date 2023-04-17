package com.example.chatbot.controller;

import com.example.chatbot.kakao.KakaoRequest;
import com.example.chatbot.kakao.KakaoResponse;
import com.example.chatbot.service.KakaoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ChatBotController {

    private final KakaoService kakaoService;

    public ChatBotController(KakaoService kakaoService) {
        this.kakaoService = kakaoService;
    }


    @PostMapping(value = "/chatbot", produces = MediaType.APPLICATION_JSON_VALUE)
    public KakaoResponse handleRequestFromKakao(@RequestBody KakaoRequest kakaoRequest) {

        log.info("kakaoRequest data = {}", kakaoRequest.toString());

        KakaoResponse kakaoResponse = kakaoService.send(kakaoRequest);
        // KakaoResponse 클래스를 리턴한 값.
        return kakaoResponse;
    }

}
