package com.prez.axon.bank.infrastructure;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LoggingEventHandler {

    @EventHandler
    public void on(Object event) {
        log.info("event -> {}", event);
    }
}
