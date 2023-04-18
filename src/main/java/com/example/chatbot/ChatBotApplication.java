package com.example.chatbot;

import com.example.chatbot.utils.http.GptClientAPI;
import com.example.chatbot.utils.http.PapagoClientAPI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({GptClientAPI.class, PapagoClientAPI.class})
public class ChatBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatBotApplication.class, args);
    }

}
