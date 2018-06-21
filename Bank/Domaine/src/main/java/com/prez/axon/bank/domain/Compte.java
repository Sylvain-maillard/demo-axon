package com.prez.axon.bank.domain;

import lombok.NoArgsConstructor;
import lombok.Value;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.TargetAggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Aggregate
@NoArgsConstructor
public class Compte {

    @AggregateIdentifier
    private String numero;
    private int limiteDébit;
    private int solde;

    @CommandHandler
    public Compte(OuvrirCompteCommand c) {
        apply(new CompteOuvertEvent(c.getNumero(), c.getLimiteDébit()));
    }

    @EventSourcingHandler
    public void on(CompteOuvertEvent event) {
        numero = event.getNumero();
        limiteDébit = event.limiteDébit;
        this.solde = 0;
    }

    @CommandHandler
    public void handle(DébiterCompteCommand c) {

        if (c.montant > limiteDébit) {
            throw new IllegalArgumentException("Limite de Débit dépassée.");
        }

        apply(new CompteDébitéEvent(c.numero, this.solde - c.montant));
    }

    @EventSourcingHandler
    public void on(CompteDébitéEvent event) {
        this.solde = event.nouveauSolde;
    }

    @Value
    public static class DébiterCompteCommand {
        @TargetAggregateIdentifier
        String numero;
        int montant;
    }

    @Value
    public static class CompteDébitéEvent {
        String numero;
        int nouveauSolde;
    }

    @Value
    public static class OuvrirCompteCommand {
        String numero;
        int limiteDébit;
    }

    @Value
    public static class CompteOuvertEvent {
        String numero;
        int limiteDébit;
    }

    @Value
    public static class ListerComptesQuery {
    }
}
