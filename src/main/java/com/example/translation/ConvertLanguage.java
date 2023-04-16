package com.example.translation;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.example.chatbot.utils.HttpUtil;
import com.example.chatbot.utils.CharacterSetUtil;

public class ConvertLanguage
{
    private static final String PAPAGO_API_URL = "https://openapi.naver.com/v1/papago/n2mt";
    private String clientId;
    private String clientSecret;

    public ConvertLanguage(String clientId, String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public String convertToLanguage(String text, String source, String target) {

        String encodedText = CharacterSetUtil.encodingSetUTF8(text);
        String requestUrl = "https://openapi.naver.com/v1/papago/n2mt?source=" + source + "&target=" + target + "&text=" + encodedText;
        String translatedText = getTranslatedText(requestUrl);
        String decodedText = CharacterSetUtil.decodingSetUTF8(translatedText);

        return decodedText;
    }

    private String getTranslatedText (String requestUrl) {

        String responseBody = HttpUtil.forwardToPapago(requestUrl, clientId, clientSecret);
        JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
        String translatedText = jsonObject.getAsJsonObject("message").getAsJsonObject("result").get("translatedText").getAsString();

        return translatedText;
    }
}