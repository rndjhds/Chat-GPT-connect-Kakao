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
public class KakaoController {

    private final KakaoService kakaoService;

    public KakaoController(KakaoService kakaoService) {
        this.kakaoService = kakaoService;
    }

    @PostMapping("/chatbot")
    public KakaoResponse createKakaoResponse(@RequestBody KakaoRequest kakaoRequest) {

        log.info("kakaoRequest data = {}", kakaoRequest.toString());

        KakaoResponse kakaoResponse = kakaoService.createKakaoResponse(kakaoRequest);

        return kakaoResponse;
    }
}