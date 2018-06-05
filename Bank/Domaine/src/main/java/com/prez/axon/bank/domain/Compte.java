package com.prez.axon.bank.domain;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Slf4j
@Aggregate
public class Compte {

    @AggregateIdentifier
    private String compteId;

    private double solde;

    @CommandHandler
    public Compte(OuvrirCompteCommand ouvrirCompteCommand) {
        compteId = ouvrirCompteCommand.getCompteId();
        log.debug("ouverture de compte en cours...");
        apply(new CompteOuvertEvent(compteId));
    }

    @CommandHandler
    public void credite(CrediteCompteCommand crediteCompteCommand) {
        apply(new CompteCredité(crediteCompteCommand.getMontant()));
    }

    private Compte() {}

    @EventSourcingHandler
    private void onCompteOuvertEvent(CompteOuvertEvent compteOuvertEvent) {
        this.compteId = compteOuvertEvent.getId();
        this.solde = 0.0d;
        log.debug("compte ouvert. {}", compteId);
    }

    @EventSourcingHandler
    private void onCredit(CompteCredité compteCredité) {
        this.solde += compteCredité.getMontant();
        log.debug("compte crédité ! nouveau solde = {}", this.solde);
    }
}
