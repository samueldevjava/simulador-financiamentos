package com.financiamento.repository;

import com.financiamento.entity.Simulacao;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

// Quarkus gerencia uma única instância desta classe
@ApplicationScoped
// PanacheRepository fornece métodos prontos: persist, findById, listAll...
// O <Simulacao> diz que esse repository trabalha com a Entity Simulacao
public class SimulacaoRepository implements PanacheRepository<Simulacao> {
    // Sem código adicional — os métodos do Panache já resolvem tudo
    // persist()    → salva no banco
    // findById()   → busca pelo ID
    // listAll()    → lista tudo
    // delete()     → deleta
}