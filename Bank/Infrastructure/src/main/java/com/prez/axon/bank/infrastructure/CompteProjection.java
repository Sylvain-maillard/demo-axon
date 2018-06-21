package com.prez.axon.bank.infrastructure;

import com.prez.axon.bank.domain.Compte;
import com.prez.axon.bank.domain.Compte.ListerComptesQuery;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class CompteProjection {

    private Map<String, Integer> comptes = new HashMap<>();

    @EventHandler
    public void on(Compte.CompteOuvertEvent compteOuvertEvent) {
        comptes.put(compteOuvertEvent.getNumero(), 0);
    }

    @EventHandler
    public void on(Compte.CompteDébitéEvent compteDébitéEvent) {
        comptes.computeIfPresent(compteDébitéEvent.getNumero(),
                (s, integer) -> compteDébitéEvent.getNouveauSolde());
    }

    @QueryHandler
    public List<String> query(ListerComptesQuery q) {
        return comptes.entrySet()
                .stream()
                .map(e -> e.getKey() + " -> " + e.getValue())
                .collect(Collectors.toList());
    }
}
