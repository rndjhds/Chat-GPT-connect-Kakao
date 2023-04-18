package com.example.chatbot.kakao.model;

public class KakaoResponse {

    private final String version;
    private final KakaoTemplate template;

    public KakaoResponse(KakaoTemplate template) {
        this.version = "2.0";
        this.template = template;
    }

    public KakaoTemplate getTemplate() {
        return template;
    }

    public String getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return "KakaoResponse{" +
                "version='" + version + '\'' +
                ", template=" + template +
                '}';
    }
}