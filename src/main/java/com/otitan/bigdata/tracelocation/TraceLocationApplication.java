package com.otitan.bigdata.tracelocation;

import com.otitan.bigdata.tracelocation.service.ws.BroadcastHandler;
import com.otitan.bigdata.tracelocation.service.ws.GpsTraceWebSocketHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.PerConnectionWebSocketHandler;

@SpringBootApplication
@EnableWebSocket
public class TraceLocationApplication extends SpringBootServletInitializer implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(gpsTraceWebSocketHandler(), "/GPS_Trace").setAllowedOrigins("*").withSockJS();
    }

    @Bean
    public WebSocketHandler gpsTraceWebSocketHandler() {
        return new PerConnectionWebSocketHandler(GpsTraceWebSocketHandler.class);
    }

    @Bean
    public BroadcastHandler createBroadcastHandler() {
        return new BroadcastHandler();
    }

    public static void main(String[] args) {
        SpringApplication.run(TraceLocationApplication.class, args);
    }

}
