package com.example.chatbot.kakao.model;

public class KakaoSimpleText {
    private final String text;

    public KakaoSimpleText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "KakaoSimpleText{" +
                "text='" + text + '\'' +
                '}';
    }
}
