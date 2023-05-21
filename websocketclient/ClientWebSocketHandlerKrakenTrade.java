package com.furansujin.brainiac.websocketclient;


import com.furansujin.brainiac.service.OrderBookService;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.socket.*;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.WebSocketConnectionManager;

public class ClientWebSocketHandlerKrakenTrade implements WebSocketHandler {

    private static final Logger logger = LoggerFactory.getLogger(ClientWebSocketHandlerKrakenTrade.class);
    public final static String MESSAGE = "{ \"event\": \"subscribe\",  \"pair\": [\"XBT/USD\"],  \"subscription\": {\"name\": \"trade\"} }";


    @Autowired
    WebSocketClient webSocketClientKrakenTrade;
    @Autowired
    MongoTemplate mongoTemplate;

    MongoCollection<Document> collectionTrade;

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        collectionTrade = mongoTemplate.getCollection("collectionTrade");

        logger.info("Kraken trade Client connection opened");

        TextMessage message = new TextMessage(MESSAGE);
        logger.info("Kraken trade Client sends: {}", message);
        webSocketSession.sendMessage(message);
    }

    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
       //logger.info("Kraken trade Client received: {}", webSocketMessage.getPayload().toString());
       // mongoTemplate.insert(webSocketMessage.getPayload().toString(), "collectionKraken");
        if(webSocketMessage.getPayload().toString() != null && !webSocketMessage.getPayload().toString().contains("heartbeat")) {
            Document documentAggregate = new Document();
            documentAggregate.append("date", System.currentTimeMillis());
            documentAggregate.append("trade", webSocketMessage.getPayload().toString());
             collectionTrade.insertOne(documentAggregate);
        }
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        logger.info("Kraken Client transport error: {}", throwable.getMessage());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        logger.info("Kraken trade Client connection closed: {}", closeStatus);
        WebSocketConnectionManager webSocketConnectionManager = ClientWebSocketConfig.webSocketConnectionManagerGenerique(webSocketClientKrakenTrade, this, ClientWebSocketConfig.KRAKEN_STREAM_URL);
        webSocketConnectionManager.start();
    }



    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}