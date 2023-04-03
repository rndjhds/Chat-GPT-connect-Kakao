package com.example.chatbot.kakao;

import java.util.ArrayList;
import java.util.List;

public class KakaoResponse {

    private List<KakaoResponseContent> contents;

    public KakaoResponse() {
        this.contents = new ArrayList<>();
    }

    public void setContents(List<KakaoResponseContent> contents) {
        this.contents = contents;
    }

    public List<KakaoResponseContent> getContents() {
        return contents;
    }
}
