package com.example.chatbot.kakao;

import java.util.ArrayList;
import java.util.List;

public class KakaoTemplate {
    private final List<KakaoOutput> outputs;

    public KakaoTemplate() {
        this.outputs = new ArrayList<>();
    }

    public List<KakaoOutput> getOutputs() {
        return outputs;
    }

    public void addSimpleTextOutput(String text) {
        this.outputs.add(new KakaoOutput(new KakaoSimpleText(text)));
    }

}
