package com.prez.axon.bank.domain;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Test;


public class CompteTest {

    private FixtureConfiguration<Compte> fixture = new AggregateTestFixture<>(Compte.class);

    @Test
    public void test_compte_ouvert() {
        fixture.givenNoPriorActivity()
                .when(new OuvrirCompteCommand("123","Jean Bon", 0.0))
                .expectSuccessfulHandlerExecution()
                .expectEvents(new CompteOuvertEvent("123"));
    }

    @Test
    public void test_compte_credité() {
        fixture.given(new CompteOuvertEvent("123"))
                .when(new CrediteCompteCommand("123",1))
                .expectSuccessfulHandlerExecution()
                .expectEvents(new CompteCredité(1));
    }

    // autres commandes:
    // débit

    // règle métier a tester:
    // si trop gros debit alors emmetre un event "piratage suspecté" ou quelque chose de ce genre.
}