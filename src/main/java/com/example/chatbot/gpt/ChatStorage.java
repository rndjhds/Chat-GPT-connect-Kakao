package com.example.chatbot.gpt;

import java.util.ArrayList;
import java.util.List;

public class ChatStorage {
    private String model = "gpt-3.5-turbo";
    private List<Message> messages;

    public ChatStorage() {
        messages = new ArrayList<>();
    }

    public String getModel() {
        return model;
    }

    public void saveMessage(Message message) {
        messages.add(message);
    }

    public List<Message> getMessages() {
        return messages;
    }
}