package com.financiamento.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

// Diz ao Hibernate que essa classe é uma tabela no banco
@Entity
// Define o nome da tabela no banco como "parcela_simulacao"
@Table(name = "parcela_simulacao")
public class ParcelaSimulacao {

    // Chave primária da tabela
    @Id
    // H2 gera o ID automaticamente
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Colunas da tabela — dados de cada mês
    private Integer mes;
    private BigDecimal saldoInicial;
    private BigDecimal jurosMes;
    private BigDecimal saldoFinal;

    // Relacionamento — muitas parcelas pertencem a uma simulacao
    // @JoinColumn = coluna que liga parcela à simulacao no banco
    @ManyToOne
    @JoinColumn(name = "simulacao_id")
    private Simulacao simulacao;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getMes() { return mes; }
    public void setMes(Integer mes) { this.mes = mes; }

    public BigDecimal getSaldoInicial() { return saldoInicial; }
    public void setSaldoInicial(BigDecimal saldoInicial) { this.saldoInicial = saldoInicial; }

    public BigDecimal getJurosMes() { return jurosMes; }
    public void setJurosMes(BigDecimal jurosMes) { this.jurosMes = jurosMes; }

    public BigDecimal getSaldoFinal() { return saldoFinal; }
    public void setSaldoFinal(BigDecimal saldoFinal) { this.saldoFinal = saldoFinal; }

    public Simulacao getSimulacao() { return simulacao; }
    public void setSimulacao(Simulacao simulacao) { this.simulacao = simulacao; }
}