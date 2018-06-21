package com.prez.axon.bank.api;

import com.prez.axon.bank.domain.Compte;
import com.prez.axon.bank.domain.Compte.ListerComptesQuery;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.responsetypes.ResponseTypes;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class Bank implements CommandLineRunner {

    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    public Bank(CommandGateway commandGateway, QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Hello Axon World");

        commandGateway.send(new Compte.OuvrirCompteCommand("123", 100));
        commandGateway.send(new Compte.OuvrirCompteCommand("1232", 100));
        commandGateway.send(new Compte.OuvrirCompteCommand("1233", 100));
        commandGateway.send(new Compte.OuvrirCompteCommand("1234", 100));
        commandGateway.send(new Compte.OuvrirCompteCommand("1235", 100));

        commandGateway.send(new Compte.DébiterCompteCommand("123", 100));
        commandGateway.send(new Compte.DébiterCompteCommand("1235", 100));
        commandGateway.send(new Compte.DébiterCompteCommand("1235", 100));
        commandGateway.send(new Compte.DébiterCompteCommand("1235", 100));

        CompletableFuture<List<String>> query = queryGateway.query(new ListerComptesQuery(), ResponseTypes.multipleInstancesOf(String.class));
        query.get().forEach(System.out::println);
    }
}
