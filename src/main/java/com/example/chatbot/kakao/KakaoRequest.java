package com.example.chatbot.kakao;

import java.util.List;

public class KakaoRequest {

    private Intent intent;
    private UserRequest userRequest;
    private List<Object> contexts;
    private Bot bot;
    private Action action;

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

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    public UserRequest getUserRequest() {
        return userRequest;
    }

    public void setUserRequest(UserRequest userRequest) {
        this.userRequest = userRequest;
    }

    public List<Object> getContexts() {
        return contexts;
    }

    public void setContexts(List<Object> contexts) {
        this.contexts = contexts;
    }

    public Bot getBot() {
        return bot;
    }

    public void setBot(Bot bot) {
        this.bot = bot;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

}

