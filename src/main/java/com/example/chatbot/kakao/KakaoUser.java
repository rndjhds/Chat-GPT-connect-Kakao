package com.example.chatbot.kakao;

import java.util.Map;

public class KakaoUser {
    private String id;
    private String type;
    private Map<String, String> properties;

    @Override
    public String toString() {
        return "KakaoUser{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", properties=" + properties +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }
}