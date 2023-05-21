package com.furansujin.brainiac.websocketclient;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import javax.websocket.ContainerProvider;
import javax.websocket.WebSocketContainer;

@Configuration
public class ClientWebSocketConfig {


    public final static String KRAKEN_STREAM_URL = "wss://ws.kraken.com";
    public final static String BITFINEX_STREAM_URL = "wss://api-pub.bitfinex.com/ws/2";

    public static WebSocketConnectionManager webSocketConnectionManagerGenerique(WebSocketClient client, WebSocketHandler webSocketHandler, String uriTemplate) {
        WebSocketConnectionManager manager = new WebSocketConnectionManager(
                client,
                webSocketHandler,
                uriTemplate
        );
        return manager;
    }

    //Kraken Eur-------------------------------------------------------------------
    @Bean
    public WebSocketConnectionManager webSocketConnectionManagerKrakenEur() {
        WebSocketConnectionManager manager = new WebSocketConnectionManager(
                webSocketClientKrakenEur(),
                webSocketHandlerKrakenEur(),
                KRAKEN_STREAM_URL
        );
        manager.setAutoStartup(true);
        return manager;
    }

    @Bean
    public WebSocketClient webSocketClientKrakenEur() {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        container.setDefaultMaxTextMessageBufferSize(4096);
        return new StandardWebSocketClient(container);
    }

    @Bean
    public WebSocketHandler webSocketHandlerKrakenEur() {
        return new ClientWebSocketHandlerKrakenEur();


    }


    //Kraken -------------------------------------------------------------------
    @Bean
    public WebSocketConnectionManager webSocketConnectionManagerKraken() {
        WebSocketConnectionManager manager = new WebSocketConnectionManager(
                webSocketClientKraken(),
                webSocketHandlerKraken(),
                KRAKEN_STREAM_URL
        );
            manager.setAutoStartup(true);
        return manager;
    }

    @Bean
    public WebSocketClient webSocketClientKraken() {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        container.setDefaultMaxTextMessageBufferSize(4096);
        return new StandardWebSocketClient(container);
    }

    @Bean
    public WebSocketHandler webSocketHandlerKraken() {
        return new ClientWebSocketHandlerKraken();


    }
    //Kraken trade-------------------------------------------------------------------
    @Bean
    public WebSocketConnectionManager webSocketConnectionManagerKrakenTrade() {
        WebSocketConnectionManager manager = new WebSocketConnectionManager(
                webSocketClientKrakenTrade(),
                webSocketHandlerKrakenTrade(),
                KRAKEN_STREAM_URL
        );
            manager.setAutoStartup(true);
        return manager;
    }

    @Bean
    public WebSocketClient webSocketClientKrakenTrade() {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        container.setDefaultMaxTextMessageBufferSize(4096);
        return new StandardWebSocketClient(container);
    }

    @Bean
    public WebSocketHandler webSocketHandlerKrakenTrade() {
        return new ClientWebSocketHandlerKrakenTrade();


    }

    //Bitfinex -------------------------------------------------------------------
    @Bean
    public WebSocketConnectionManager webSocketConnectionManagerBitfinex () {
        WebSocketConnectionManager manager = new WebSocketConnectionManager(
                webSocketClientBitfinex (),
                webSocketHandlerBitfinex(),
                BITFINEX_STREAM_URL
        );
            manager.setAutoStartup(true);

        return manager;
    }

    @Bean
    public WebSocketClient webSocketClientBitfinex () {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        container.setDefaultMaxTextMessageBufferSize(4096);
        return new StandardWebSocketClient(container);
    }

    @Bean
    public WebSocketHandler webSocketHandlerBitfinex () {
        return new ClientWebSocketHandlerBitfinex();
    }
}