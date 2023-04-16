package com.example.chatbot.gpt;
import com.example.chatbot.repository.MessageRepository;
import com.google.gson.Gson;
import java.util.List;

public class ChatGptRequestBody {
    private String model;
    private List<Message> messages;

    public ChatGptRequestBody(String model, List<Message> messages) {
        this.model = model;
        this.messages = messages;
    }

    public String toJsonString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static ChatGptRequestBody create(String model, MessageRepository messageRepository) {
        List<Message> messages = messageRepository.getMessages();
        return new ChatGptRequestBody(model, messages);
    }
}
