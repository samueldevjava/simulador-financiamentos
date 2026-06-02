package com.financiamento;

import com.financiamento.dto.SimulacaoRequest;
import com.financiamento.dto.SimulacaoResponse;
import com.financiamento.service.SimulacaoService;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class SimulacaoServiceTest {

    @Inject
    SimulacaoService service;

    @Test
    public void testCalcularJurosCompostos() {
        // Monta o request
        SimulacaoRequest request = new SimulacaoRequest();
        request.setValorInicial(new BigDecimal("1000.00"));
        request.setTaxaJurosMensal(new BigDecimal("1.5"));
        request.setPrazoMeses(12);

        // Chama o Service
        SimulacaoResponse response = service.calcular(request);

        // Verifica resultados
        assertNotNull(response);
        assertNotNull(response.getId());
        assertEquals(12, response.getParcelas().size());
        assertEquals(new BigDecimal("1195.62"), response.getValorFinal());
        assertEquals(new BigDecimal("195.62"), response.getTotalJuros());
    }

    @Test
    public void testCalcularPrazoUmMes() {
        SimulacaoRequest request = new SimulacaoRequest();
        request.setValorInicial(new BigDecimal("1000.00"));
        request.setTaxaJurosMensal(new BigDecimal("1.5"));
        request.setPrazoMeses(1);

        SimulacaoResponse response = service.calcular(request);

        assertNotNull(response);
        assertEquals(1, response.getParcelas().size());
        assertEquals(new BigDecimal("1015.00"), response.getValorFinal());
        assertEquals(new BigDecimal("15.00"), response.getTotalJuros());
    }

    @Test
    public void testBuscarPorIdNaoEncontrado() {
        SimulacaoResponse response = service.buscarPorId(999L);
        assertNull(response);
    }

    @Test
    public void testPrimeiraParcela() {
        SimulacaoRequest request = new SimulacaoRequest();
        request.setValorInicial(new BigDecimal("1000.00"));
        request.setTaxaJurosMensal(new BigDecimal("1.5"));
        request.setPrazoMeses(12);

        SimulacaoResponse response = service.calcular(request);

        // Verifica a primeira parcela
        assertEquals(1, response.getParcelas().get(0).getMes());
        assertEquals(new BigDecimal("1000.00"), response.getParcelas().get(0).getSaldoInicial());
        assertEquals(new BigDecimal("15.00"), response.getParcelas().get(0).getJurosMes());
        assertEquals(new BigDecimal("1015.00"), response.getParcelas().get(0).getSaldoFinal());
    }
}