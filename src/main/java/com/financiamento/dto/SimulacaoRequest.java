//Diz ao Java em qual pacote essa classe mora
package com.financiamento.dto;

//Importam ferramentas externas que serão usadas nesta classe
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

//SimulacaoRequest — o que entra na API

public class SimulacaoRequest {

    @NotNull(message = "Valor inicial é obrigatório")//não aceita campo vazio
    @DecimalMin(value = "0.01", message = "Valor inicial deve ser maior que zero")//não aceita valor menor que 0.01
    private BigDecimal valorInicial;//declara a variável valor inicial

    @NotNull(message = "Taxa de juros mensal é obrigatória")//não aceita campo vazio
    @DecimalMin(value = "0.01", message = "Taxa deve ser maior que zero")// taxa não pode ser menor que 0.01
    private BigDecimal taxaJurosMensal;//declara a variável taxa de juros mensal

    @NotNull(message = "Prazo em meses é obrigatório")//não aceita campo vazio
    @Min(value = 1, message = "Prazo deve ser de no mínimo 1 mês")//não aceita valor menor que 1
    private Integer prazoMeses;//declara a variável referente ao prazo em meses

    //Os gets deixa as outras classes lerem o seu valor, já os sets deixam as outras classes definirem seu valor
    public BigDecimal getValorInicial() { return valorInicial; }
    public void setValorInicial(BigDecimal valorInicial) { this.valorInicial = valorInicial; }

    public BigDecimal getTaxaJurosMensal() { return taxaJurosMensal; }
    public void setTaxaJurosMensal(BigDecimal taxaJurosMensal) { this.taxaJurosMensal = taxaJurosMensal; }

    public Integer getPrazoMeses() { return prazoMeses; }
    public void setPrazoMeses(Integer prazoMeses) { this.prazoMeses = prazoMeses; }
}
