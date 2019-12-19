package com.example.websocket.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

@Component
public class WebSocketHandler extends AbstractWebSocketHandler {

	List<WebSocketSession> sessionList = new ArrayList<>();

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("Se establecio una nueva conexion id " + session.getId());
		addSessionListener(session);

		System.out.println("Raw user info::  " + session.getUri().getRawUserInfo());

	}

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
		System.out.println("He recibido un nuevo mensaje");
		sendMessageAllSessions(message, session);

	}

	private boolean existSession(WebSocketSession session) {

		for (WebSocketSession tmp : sessionList) {
			tmp.getId();
			if (tmp.getId().equals(session.getId())) {
				return true;
			}
		}

		return false;
	}

	private void addSessionListener(WebSocketSession session) {
		boolean exist = existSession(session);

		if (!exist) {
			sessionList.add(session);
		}
	}

	private void sendMessageAllSessions(TextMessage message, WebSocketSession senderSession) throws IOException {
		for (WebSocketSession session : sessionList) {
			if (!senderSession.getId().equals(session.getId())) {
				session.sendMessage(message);
			}
		}
	}

	@Override
	public void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws IOException {
		System.out.println("Recibi un mensaje binario");
		session.sendMessage(message);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

		System.out.println("Cerrando conexion [Razon]" + status.getReason());
		System.out.println("Cerrando conexion [Código]" + status.getCode());
		WebSocketSession tmp = null;
	
		for (WebSocketSession sessionTmp : sessionList) {
			if (sessionTmp.getId().equals(session.getId())) {
				tmp = sessionTmp;
				break;
			}
		}

		sessionList.remove(tmp);

	}

}
