package com.example.scheduleservice.controller;

import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;


@Controller
public class ChatController {

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public String sendMessage(String message) {
        return message;
    }

    @MessageMapping("/chat.privateMessage")
    @SendTo("/queue/{userId}")
    public String sendPrivateMessage(String message, String userId) {
        return message;
    }
}