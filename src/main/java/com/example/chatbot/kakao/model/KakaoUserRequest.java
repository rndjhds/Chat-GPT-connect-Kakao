package com.example.chatbot.kakao.model;

import java.util.Map;

public class KakaoUserRequest {
    private String callbackUrl;
    private String timezone;
    private KakaoBlock block;
    private String utterance;
    private String lang;
    private KakaoUser user;

    public KakaoUserRequest() {
        this.callbackUrl = "http://54.180.81.232:8080/chatbot";
    }

    @Override
    public String toString() {
        return "KakaoUserRequest{" +
                "callbackUrl='" + callbackUrl + '\'' +
                ", timezone='" + timezone + '\'' +
                ", block=" + block +
                ", utterance='" + utterance + '\'' +
                ", lang='" + lang + '\'' +
                ", user=" + user +
                '}';
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public KakaoBlock getBlock() {
        return block;
    }

    public void setBlock(KakaoBlock kakaoBlock) {
        this.block = kakaoBlock;
    }

    public String getUtterance() {
        return utterance;
    }

    public void setUtterance(String utterance) {
        this.utterance = utterance;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public KakaoUser getUser() {
        return user;
    }

    public void setUser(KakaoUser kakaoUser) {
        this.user = kakaoUser;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }
}