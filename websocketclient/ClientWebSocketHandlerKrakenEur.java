package com.furansujin.brainiac.websocketclient;


import com.furansujin.brainiac.service.OrderBookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.*;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.WebSocketConnectionManager;

public class ClientWebSocketHandlerKrakenEur implements WebSocketHandler {

    private static final Logger logger = LoggerFactory.getLogger(ClientWebSocketHandlerKrakenEur.class);
    public final static String MESSAGE = "{ \"event\": \"subscribe\",  \"pair\": [\"XBT/EUR\"],  \"subscription\": {\"depth\": 25, \"name\": \"book\"} }";


    @Autowired
    WebSocketClient webSocketClientKrakenEur;
    @Autowired
    OrderBookService orderBookService;

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
       // logger.info("Kraken Client connection opened");

        TextMessage message = new TextMessage(MESSAGE);
        logger.info("Kraken Client sends Eur: {}", message);
        webSocketSession.sendMessage(message);
    }

    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
     //  logger.info("Kraken Client received: {}", webSocketMessage.getPayload().toString());
       // mongoTemplate.insert(webSocketMessage.getPayload().toString(), "collectionKraken");
        if(webSocketMessage.getPayload().toString() != null) {
            orderBookService.updateKrakenEur(webSocketMessage.getPayload().toString());

        }
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        logger.info("Kraken Eur Client transport error Eur: {}", throwable.getMessage());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        logger.info("Kraken Eur Client connection closed Eur: {}", closeStatus);
        WebSocketConnectionManager webSocketConnectionManager = ClientWebSocketConfig.webSocketConnectionManagerGenerique(webSocketClientKrakenEur, this, ClientWebSocketConfig.KRAKEN_STREAM_URL);
        webSocketConnectionManager.start();
    }



    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}