package com.financiamento.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

// Diz ao Hibernate que essa classe é uma tabela no banco
@Entity
// Define o nome da tabela no banco como "simulacao"
@Table(name = "simulacao")
public class Simulacao {

    // Chave primária da tabela
    @Id
    // H2 gera o ID automaticamente — auto incremento
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Colunas da tabela — cada atributo vira uma coluna
    private BigDecimal valorInicial;
    private BigDecimal taxaJurosMensal;
    private Integer prazoMeses;
    private BigDecimal valorFinal;
    private BigDecimal totalJuros;

    // Relacionamento — uma simulacao tem muitas parcelas
    // mappedBy = o atributo na ParcelaSimulacao que aponta para cá
    // CascadeType.ALL = quando salvar a simulacao, salva as parcelas junto
    @OneToMany(mappedBy = "simulacao", cascade = CascadeType.ALL)
    private List<ParcelaSimulacao> parcelas = new ArrayList<>();

    // Getters e Setters — permitem outras classes ler e escrever os atributos
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public BigDecimal getValorInicial() { return valorInicial; }
    public void setValorInicial(BigDecimal valorInicial) { this.valorInicial = valorInicial; }

    public BigDecimal getTaxaJurosMensal() { return taxaJurosMensal; }
    public void setTaxaJurosMensal(BigDecimal taxaJurosMensal) { this.taxaJurosMensal = taxaJurosMensal; }

    public Integer getPrazoMeses() { return prazoMeses; }
    public void setPrazoMeses(Integer prazoMeses) { this.prazoMeses = prazoMeses; }

    public BigDecimal getValorFinal() { return valorFinal; }
    public void setValorFinal(BigDecimal valorFinal) { this.valorFinal = valorFinal; }

    public BigDecimal getTotalJuros() { return totalJuros; }
    public void setTotalJuros(BigDecimal totalJuros) { this.totalJuros = totalJuros; }

    public List<ParcelaSimulacao> getParcelas() { return parcelas; }
    public void setParcelas(List<ParcelaSimulacao> parcelas) { this.parcelas = parcelas; }
}