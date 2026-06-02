package com.financiamento;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

// Sobe o Quarkus completo para testar a API
@QuarkusTest
public class SimulacaoResourceIT {

    @Test
    public void testCriarSimulacaoComSucesso() {
        // Monta o JSON que será enviado para a API
        String json = """
                {
                    "valorInicial": 1000.00,
                    "taxaJurosMensal": 1.5,
                    "prazoMeses": 12
                }
                """;

        given()
            // Define que envia JSON
            .contentType(ContentType.JSON)
            // Corpo da requisição
            .body(json)
        .when()
            // Chama o endpoint POST /simulacoes
            .post("/simulacoes")
        .then()
            // Verifica HTTP 201
            .statusCode(201)
            // Verifica que o ID foi gerado
            .body("id", notNullValue())
            // Verifica o valor final calculado
            .body("valorFinal", equalTo(1195.62f))
            // Verifica o total de juros
            .body("totalJuros", equalTo(195.62f))
            // Verifica que tem 12 parcelas
            .body("parcelas.size()", equalTo(12));
    }

    @Test
    public void testCriarSimulacaoComDadosInvalidos() {
        // Envia dados inválidos — deve retornar 400
        String json = """
                {
                    "valorInicial": -100,
                    "taxaJurosMensal": 0,
                    "prazoMeses": 0
                }
                """;

        given()
            .contentType(ContentType.JSON)
            .body(json)
        .when()
            .post("/simulacoes")
        .then()
            // Verifica HTTP 400
            .statusCode(400);
    }

    @Test
    public void testBuscarSimulacaoPorIdExistente() {
        // Primeiro cria uma simulação
        String json = """
                {
                    "valorInicial": 1000.00,
                    "taxaJurosMensal": 1.5,
                    "prazoMeses": 12
                }
                """;

        // Pega o ID gerado pelo POST
        Integer id = given()
            .contentType(ContentType.JSON)
            .body(json)
        .when()
            .post("/simulacoes")
        .then()
            .statusCode(201)
            .extract()
            // Extrai o ID da resposta
            .path("id");

        // Agora busca pelo ID gerado
        given()
        .when()
            .get("/simulacoes/" + id)
        .then()
            // Verifica HTTP 200
            .statusCode(200)
            // Verifica que o ID bate
            .body("id", equalTo(id))
            // Verifica o valor final
            .body("valorFinal", equalTo(1195.62f));
    }

    @Test
    public void testBuscarSimulacaoPorIdInexistente() {
        given()
        .when()
            // Busca um ID que não existe
            .get("/simulacoes/999")
        .then()
            // Verifica HTTP 404
            .statusCode(404);
    }
}