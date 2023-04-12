package com.example.chatbot.kakao;

import java.util.ArrayList;
import java.util.List;

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
}
