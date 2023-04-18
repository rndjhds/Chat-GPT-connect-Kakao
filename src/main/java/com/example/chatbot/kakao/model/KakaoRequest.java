package com.example.chatbot.kakao.model;

import java.util.List;

public class KakaoRequest {

    private KakaoIntent intent;
    private KakaoUserRequest userRequest;
    private List<Object> contexts;
    private KakaoBot bot;
    private KakaoAction action;

    @Override
    public String toString() {
        return "KakaoRequest{" +
                "intent=" + intent +
                ", userRequest=" + userRequest +
                ", contexts=" + contexts +
                ", bot=" + bot +
                ", action=" + action +
                '}';
    }

    public KakaoIntent getIntent() {
        return intent;
    }

    public void setIntent(KakaoIntent kakaoIntent) {
        this.intent = kakaoIntent;
    }

    public KakaoUserRequest getUserRequest() {
        return userRequest;
    }

    public void setUserRequest(KakaoUserRequest kakaoUserRequest) {
        this.userRequest = kakaoUserRequest;
    }

    public List<Object> getContexts() {
        return contexts;
    }

    public void setContexts(List<Object> contexts) {
        this.contexts = contexts;
    }

    public KakaoBot getBot() {
        return bot;
    }

    public void setBot(KakaoBot kakaoBot) {
        this.bot = kakaoBot;
    }

    public KakaoAction getAction() {
        return action;
    }

    public void setAction(KakaoAction kakaoAction) {
        this.action = kakaoAction;
    }

}

