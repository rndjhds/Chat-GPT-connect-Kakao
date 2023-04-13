package com.example.chatbot.service;

import com.example.translation.TranslatorEnToKo;
import com.example.translation.TranslatorKoToEn;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TranslationService {

    @Value("${translator_client_id}")
    private String clientId;

    @Value("${translator_client_secret}")
    private String clientSecret;

    public String translateToEnglish(String text) {
        return new TranslatorKoToEn(clientId, clientSecret).translateToEnglish(text);
    }

    public String translateToKorean(String text) {
        return new TranslatorEnToKo(clientId, clientSecret).translateToKorean(text);
    }
}
