package com.example.chatbot.kakao.model;

public class CallBackRequest {

    private final String version;
    private final KakaoTemplate template;

    public CallBackRequest(String version, KakaoTemplate template) {
        this.version = version;
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
