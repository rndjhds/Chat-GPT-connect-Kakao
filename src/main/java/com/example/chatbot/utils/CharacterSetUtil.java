package com.example.chatbot.utils;

import java.nio.charset.StandardCharsets;

public class CharacterSetUtil {

    public static String encodingSetUTF8(final String text) {
        final byte[] encodedTextBytes = text.getBytes(StandardCharsets.UTF_8);
        final String encodedText = new String(encodedTextBytes, StandardCharsets.UTF_8);
        return encodedText;
    }

    public static String decodingSetUTF8(final String translatedText) {
        final String decodedText = new String(translatedText.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
        return decodedText;
    }
}