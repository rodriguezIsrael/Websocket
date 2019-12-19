package com.example.websocket.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.TextMessage;

import com.example.websocket.handler.WebSocketHandler;

@RestController
public class TestController {

	@Autowired
	WebSocketHandler ws;

	@GetMapping("/example")
	public void test() throws IOException {
		TextMessage message = new TextMessage("Hola mundo");

		System.out.println("Example");
//		ws.getSession().sendMessage(message);

	}
}
