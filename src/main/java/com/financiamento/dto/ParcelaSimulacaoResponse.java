package com.financiamento.dto;

import java.math.BigDecimal;

//ParcelaSimulacaoResponse — Cada mês da memória de cálculo

//Quatro campos que representam exatamente o que o desafio pede para cada mês 
//sem anotações de validação porque é só saída, não entrada.
public class ParcelaSimulacaoResponse {

    private Integer mes;
    private BigDecimal saldoInicial;
    private BigDecimal jurosMes;
    private BigDecimal saldoFinal;

    //Construtor vazio — o Jackson — biblioteca que converte objetos 
    //Java em JSON — precisa dele para funcionar.
    public ParcelaSimulacaoResponse() {}

    
    //Construtor com parâmetros — permite criar o objeto já com todos os valores de uma vez.
    //Em vez de criar vazio e chamar 4 setters separados.
    public ParcelaSimulacaoResponse(Integer mes, BigDecimal saldoInicial,
                                     BigDecimal jurosMes, BigDecimal saldoFinal) {
        this.mes = mes;
        this.saldoInicial = saldoInicial;
        this.jurosMes = jurosMes;
        this.saldoFinal = saldoFinal;
    }
    
    //Os Getters e Setters da classe:
    public Integer getMes() { return mes; }
    public void setMes(Integer mes) { this.mes = mes; }

    public BigDecimal getSaldoInicial() { return saldoInicial; }
    public void setSaldoInicial(BigDecimal saldoInicial) { this.saldoInicial = saldoInicial; }

    public BigDecimal getJurosMes() { return jurosMes; }
    public void setJurosMes(BigDecimal jurosMes) { this.jurosMes = jurosMes; }

    public BigDecimal getSaldoFinal() { return saldoFinal; }
    public void setSaldoFinal(BigDecimal saldoFinal) { this.saldoFinal = saldoFinal; }
}