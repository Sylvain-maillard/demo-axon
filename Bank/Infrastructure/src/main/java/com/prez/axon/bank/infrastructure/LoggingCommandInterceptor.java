package com.prez.axon.bank.infrastructure;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.messaging.InterceptorChain;
import org.axonframework.messaging.MessageHandlerInterceptor;
import org.axonframework.messaging.unitofwork.UnitOfWork;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class LoggingCommandInterceptor implements MessageHandlerInterceptor {

    private final SimpleCommandBus commandBus;

    public LoggingCommandInterceptor(SimpleCommandBus commandBus) {
        this.commandBus = commandBus;
    }

    @PostConstruct
    public void registerInterceptor() {
        commandBus.registerHandlerInterceptor(this);
    }

    @Override
    public Object handle(UnitOfWork unitOfWork, InterceptorChain interceptorChain) throws Exception {
        log.info("Processing command {}", unitOfWork.getMessage());
        try {
            Object proceed = interceptorChain.proceed();
            log.info("Processed command: {}", proceed);
            return proceed;
        } catch (Exception e) {
            log.error("Error processing command: " + e.getMessage());
            throw e;
        }
    }
}
