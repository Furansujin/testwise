package com.furansujin.brainiac.websocketclient;


import com.furansujin.brainiac.service.OrderBookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.socket.*;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.WebSocketConnectionManager;

public class ClientWebSocketHandlerBitfinex implements WebSocketHandler {

    private static final Logger logger = LoggerFactory.getLogger(ClientWebSocketHandlerBitfinex.class);
    public final static String MESSAGE = "  { \"event\": \"subscribe\", \"channel\": \"book\", \"symbol\": \"btcusd\", \"len\": \"25\"}";



    @Autowired
    WebSocketClient webSocketClientBitfinex;
    @Autowired
    OrderBookService orderBookService;
    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        logger.info("Bitfinex Client connection opened");
        TextMessage message = new TextMessage(MESSAGE);
        logger.info("Bitfinex Client sends: {}", message);
        webSocketSession.sendMessage(message);
    }

    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
      // logger.info("Bitfinex Client received: {}", webSocketMessage.getPayload().toString());
        if(webSocketMessage.getPayload().toString() != null) {
            orderBookService.updateBitfinex(webSocketMessage.getPayload().toString());
        }
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        logger.info("Bitfinex Client transport error: {}", throwable.getMessage());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        logger.info("Bitfinex Client connection closed: {}", closeStatus);
        WebSocketConnectionManager webSocketConnectionManager = ClientWebSocketConfig.webSocketConnectionManagerGenerique(webSocketClientBitfinex, this, ClientWebSocketConfig.BITFINEX_STREAM_URL);
        webSocketConnectionManager.start();
    }




    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}