package com.example.chatbot.kakao.model;

public class KakaoOutput {
    private final KakaoSimpleText simpleText;

    public KakaoOutput(KakaoSimpleText simpleText) {
        this.simpleText = simpleText;
    }

    public KakaoSimpleText getSimpleText() {
        return simpleText;
    }

    @Override
    public String toString() {
        return "KakaoOutput{" +
                "simpleText=" + simpleText +
                '}';
    }
}
