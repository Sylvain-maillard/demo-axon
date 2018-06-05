package com.prez.axon.bank.domain;

import lombok.Value;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

@Value
public class CrediteCompteCommand {
    @TargetAggregateIdentifier
    final String id;
    final double montant;
}
