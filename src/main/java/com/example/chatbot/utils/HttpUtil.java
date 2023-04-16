package com.example.chatbot.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class HttpUtil {
    public static String forwardToPapago(String requestUrl, String clientId, String clientSecret) {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/x-www-form-urlencoded");
        headers.set("X-Naver-Client-Id", clientId);
        headers.set("X-Naver-Client-Secret", clientSecret);
        HttpEntity<String> request = new HttpEntity("", headers);
        ResponseEntity<String> response = restTemplate.exchange(requestUrl, HttpMethod.POST,request, String.class);
        String responseBody = response.getBody();

        return responseBody;
    }
}