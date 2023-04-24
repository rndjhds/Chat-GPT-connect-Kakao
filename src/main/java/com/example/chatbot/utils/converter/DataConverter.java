package com.example.chatbot.utils.converter;

import java.nio.charset.StandardCharsets;

public interface DataConverter {

    public static String encodingText(String text) {

        String encodedText = new String(text.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);

        return encodedText;
    }

    public static String decodingText(String text) {

        String decodedText = new String(text.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);

        return decodedText;
    }
}
