package com.prez.axon.bank.domain;

import com.prez.axon.bank.domain.Compte.CompteDébitéEvent;
import com.prez.axon.bank.domain.Compte.CompteOuvertEvent;
import com.prez.axon.bank.domain.Compte.DébiterCompteCommand;
import com.prez.axon.bank.domain.Compte.OuvrirCompteCommand;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.junit.Test;

import static org.junit.Assert.*;

public class CompteTest {

    AggregateTestFixture<Compte> testFixture = new AggregateTestFixture<>(Compte.class);

    @Test
    public void ouvrir_compte() {
        testFixture.givenNoPriorActivity()
                .when(new OuvrirCompteCommand("123", 100))
                .expectSuccessfulHandlerExecution()
                .expectEvents(new CompteOuvertEvent("123", 100));
    }

    @Test
    public void debit_compte() {
        testFixture.given(new CompteOuvertEvent("123",100))
                .when(new DébiterCompteCommand("123", 10))
                .expectEvents(new CompteDébitéEvent("123", -10));
    }

    @Test
    public void debit_dépassant_la_limite_doit_provoquer_une_erreur() {
        testFixture.given(new CompteOuvertEvent("123", 100))
                .when(new DébiterCompteCommand("123", 1000))
                .expectNoEvents()
                .expectException(IllegalArgumentException.class);
    }

}