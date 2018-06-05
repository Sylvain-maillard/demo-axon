package com.prez.axon.bank.domain;

import lombok.Value;

@Value
public class OuvrirCompteCommand {
    final String compteId;
    final String proprietaire;
    final Double soldeInitial;
}
