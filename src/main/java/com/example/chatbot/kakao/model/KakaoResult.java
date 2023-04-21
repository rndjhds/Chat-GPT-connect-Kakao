package com.example.chatbot.kakao.model;

public class KakaoResult {

    private final String version;
    private final boolean useCallback;
    private final KakaoTemplate template;

    public KakaoResult(KakaoTemplate template) {
        this.version = "2.0";
        this.useCallback=true;
        this.template = template;
    }

    public KakaoTemplate getTemplate() {
        return template;
    }

    public String getVersion() {
        return version;
    }

    public boolean isUseCallback() {
        return useCallback;
    }

    @Override
    public String toString() {
        return "KakaoResponse{" +
                "version='" + version + '\'' +
                ", useCallback=" + useCallback +
                ", template=" + template +
                '}';
    }
}
