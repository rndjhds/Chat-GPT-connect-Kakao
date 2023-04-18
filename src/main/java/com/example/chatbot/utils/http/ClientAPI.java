package com.example.chatbot.utils.http;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

public interface ClientAPI {

    public HttpHeaders createHttpHeaders();

    public HttpEntity<String> createHttpEntity(String body);

    public String forwardToAPI(String body);

    public String receiveFromAPI(String response);
}
