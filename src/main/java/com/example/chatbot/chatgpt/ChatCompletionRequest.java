package com.example.chatbot.chatgpt;

import com.example.chatbot.repository.ChatMessageRepository;
import com.google.gson.Gson;

import java.util.List;

public class ChatCompletionRequest {
    private String model;
    private List<ChatMessage> messages;

    public ChatCompletionRequest(String model, List<ChatMessage> messages) {
        this.model = model;
        this.messages = messages;
    }

    public String toJsonString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static ChatCompletionRequest create(String model, ChatMessageRepository chatMessageRepository) {
        List<ChatMessage> chatMessages = chatMessageRepository.getMessages();
        return new ChatCompletionRequest(model, chatMessages);
    }
}
