package com.prez.axon.bank.domain;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Test;


public class CompteTest {

    private FixtureConfiguration<Compte> fixture = new AggregateTestFixture<>(Compte.class);

    @Test
    public void test_compte_ouvert() {
        fixture.givenNoPriorActivity()
                .when(new OuvrirCompteCommand("Jean Bon", 0.0))
                .expectSuccessfulHandlerExecution()
                .expectEvents(new CompteOuvertEvent());
        /*
        These four lines define the actual scenario and its expected
        result. The first line defines the events that happened in the
        past. These events define the state of the aggregate under test.
        In practical terms, these are the events that the event store
        returns when an aggregate is loaded. The second line defines the
        command that we wish to execute against our system. Finally, we
        have two more methods that define expected behavior. In the
        example, we use the recommended void return type. The last method
        defines that we expect a single event as result of the command
        execution.
        */
    }

    @Test
    public void test_compte_credité() {
        fixture.given(new CompteOuvertEvent())
                .when(new CrediteCompteCommand())
                .expectSuccessfulHandlerExecution()
                .expectEvents(new CompteCredité(1));
    }

    // autres commandes:
    // débit

    // règle métier a tester:
    // si trop gros debit alors emmetre un event "piratage suspecté" ou quelque chose de ce genre.
}