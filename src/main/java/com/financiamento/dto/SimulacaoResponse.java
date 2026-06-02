package com.financiamento.dto;

import java.math.BigDecimal;
import java.util.List;

//SimulacaoResponse — o que sai da API

//Todos os campos que a API retorna. O List<ParcelaSimulacaoResponse> é a lista de meses 
//é isso que vira o array de parcelas no JSON no Swagger.

public class SimulacaoResponse {

    private Long id;
    private BigDecimal valorInicial;
    private BigDecimal taxaJurosMensal;
    private Integer prazoMeses;
    private BigDecimal valorFinal;
    private BigDecimal totalJuros;
    private List<ParcelaSimulacaoResponse> parcelas;

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

    public List<ParcelaSimulacaoResponse> getParcelas() { return parcelas; }
    public void setParcelas(List<ParcelaSimulacaoResponse> parcelas) { this.parcelas = parcelas; }
}
