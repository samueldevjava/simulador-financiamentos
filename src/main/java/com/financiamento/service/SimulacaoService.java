package com.financiamento.service;

import com.financiamento.dto.ParcelaSimulacaoResponse;
import com.financiamento.dto.SimulacaoRequest;
import com.financiamento.dto.SimulacaoResponse;
import com.financiamento.entity.ParcelaSimulacao;
import com.financiamento.entity.Simulacao;
import com.financiamento.repository.SimulacaoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

// Quarkus gerencia uma única instância desta classe
@ApplicationScoped
public class SimulacaoService {

    // Precisão decimal usada nos cálculos
    private static final int ESCALA = 8;
    // Define como arredondar — HALF_UP = arredonda para cima no meio (ex: 1.5 → 2)
    private static final RoundingMode ARREDONDAMENTO = RoundingMode.HALF_UP;

    // Quarkus injeta o Repository automaticamente — sem precisar de new
    @Inject
    SimulacaoRepository repository;

    // @Transactional — garante que tudo é salvo junto ou nada é salvo
    @Transactional
    public SimulacaoResponse calcular(SimulacaoRequest request) {

        // Pega os dados que vieram do usuário via SimulacaoRequest
        BigDecimal valorInicial = request.getValorInicial();
        BigDecimal taxaPercentual = request.getTaxaJurosMensal();
        Integer prazoMeses = request.getPrazoMeses();

        // Converte taxa de percentual para decimal — ex: 1.5% → 0.015
        BigDecimal taxa = taxaPercentual
                .divide(new BigDecimal("100"), ESCALA, ARREDONDAMENTO);

        // Listas que vão guardar os dados mês a mês
        List<ParcelaSimulacao> parcelas = new ArrayList<>();
        List<ParcelaSimulacaoResponse> parcelasResponse = new ArrayList<>();

        // Saldo começa com o valor inicial
        BigDecimal saldo = valorInicial;

        // Loop — calcula os juros de cada mês
        for (int mes = 1; mes <= prazoMeses; mes++) {

            // Saldo no início desse mês
            BigDecimal saldoInicial = saldo;

            // Juros desse mês = saldo × taxa
            BigDecimal jurosMes = saldo.multiply(taxa)
                    .setScale(ESCALA, ARREDONDAMENTO);

            // Saldo no final desse mês = saldo inicial + juros
            BigDecimal saldoFinal = saldoInicial.add(jurosMes);

            // Monta a Entity ParcelaSimulacao para salvar no banco
            ParcelaSimulacao parcela = new ParcelaSimulacao();
            parcela.setMes(mes);
            parcela.setSaldoInicial(saldoInicial.setScale(2, ARREDONDAMENTO));
            parcela.setJurosMes(jurosMes.setScale(2, ARREDONDAMENTO));
            parcela.setSaldoFinal(saldoFinal.setScale(2, ARREDONDAMENTO));
            parcelas.add(parcela);

            // Monta o DTO ParcelaSimulacaoResponse para retornar ao usuário
            parcelasResponse.add(new ParcelaSimulacaoResponse(
                    mes,
                    saldoInicial.setScale(2, ARREDONDAMENTO),
                    jurosMes.setScale(2, ARREDONDAMENTO),
                    saldoFinal.setScale(2, ARREDONDAMENTO)
            ));

            // Atualiza o saldo para o próximo mês
            saldo = saldoFinal;
        }

        // Valor final = saldo após o último mês
        BigDecimal valorFinal = saldo.setScale(2, ARREDONDAMENTO);

        // Total de juros = diferença entre valor final e valor inicial
        BigDecimal totalJuros = valorFinal
                .subtract(valorInicial)
                .setScale(2, ARREDONDAMENTO);

        // Monta a Entity principal Simulacao para salvar no banco
        Simulacao simulacao = new Simulacao();
        simulacao.setValorInicial(valorInicial.setScale(2, ARREDONDAMENTO));
        simulacao.setTaxaJurosMensal(taxaPercentual.setScale(2, ARREDONDAMENTO));
        simulacao.setPrazoMeses(prazoMeses);
        simulacao.setValorFinal(valorFinal);
        simulacao.setTotalJuros(totalJuros);

        // Liga cada parcela à simulacao — necessário para o banco saber o relacionamento
        for (ParcelaSimulacao p : parcelas) {
            p.setSimulacao(simulacao);
        }

        // Adiciona as parcelas na simulacao — CascadeType.ALL salva tudo junto
        simulacao.setParcelas(parcelas);

        // Salva a simulacao e todas as parcelas no banco H2
        repository.persist(simulacao);

        // Monta o DTO SimulacaoResponse para retornar ao usuário
        SimulacaoResponse response = new SimulacaoResponse();
        // ID gerado pelo banco após o persist
        response.setId(simulacao.getId());
        response.setValorInicial(simulacao.getValorInicial());
        response.setTaxaJurosMensal(simulacao.getTaxaJurosMensal());
        response.setPrazoMeses(prazoMeses);
        response.setValorFinal(valorFinal);
        response.setTotalJuros(totalJuros);
        // Lista de parcelas calculadas mês a mês
        response.setParcelas(parcelasResponse);

        return response;
    }

    // Busca uma simulacao existente pelo ID
    public SimulacaoResponse buscarPorId(Long id) {

        // Busca no banco pelo ID — retorna null se não encontrar
        Simulacao simulacao = repository.findById(id);

        // Se não encontrou, retorna null — Resource vai retornar 404
        if (simulacao == null) {
            return null;
        }

        // Monta a lista de parcelas para o response
        List<ParcelaSimulacaoResponse> parcelasResponse = new ArrayList<>();
        for (ParcelaSimulacao p : simulacao.getParcelas()) {
            parcelasResponse.add(new ParcelaSimulacaoResponse(
                    p.getMes(),
                    p.getSaldoInicial(),
                    p.getJurosMes(),
                    p.getSaldoFinal()
            ));
        }

        // Monta e retorna o SimulacaoResponse com os dados do banco
        SimulacaoResponse response = new SimulacaoResponse();
        response.setId(simulacao.getId());
        response.setValorInicial(simulacao.getValorInicial());
        response.setTaxaJurosMensal(simulacao.getTaxaJurosMensal());
        response.setPrazoMeses(simulacao.getPrazoMeses());
        response.setValorFinal(simulacao.getValorFinal());
        response.setTotalJuros(simulacao.getTotalJuros());
        response.setParcelas(parcelasResponse);

        return response;
    }
}