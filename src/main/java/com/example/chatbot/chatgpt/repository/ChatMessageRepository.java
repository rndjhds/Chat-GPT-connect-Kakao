package com.example.chatbot.chatgpt.repository;

import com.example.chatbot.chatgpt.model.ChatMessage;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ChatMessageRepository {
    private List<ChatMessage> chatMessages;

    public ChatMessageRepository() {

        chatMessages = new ArrayList<>();
    }

    public void add(ChatMessage chatMessage) {

        chatMessages.add(chatMessage);
    }

    public List<ChatMessage> getMessages() {

        return chatMessages;
    }

}
