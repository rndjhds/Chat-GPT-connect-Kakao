package com.example.chatbot.kakao;

public class KakaoBot {

    private String id;
    private String name;

    @Override
    public String toString() {
        return "Bot{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}