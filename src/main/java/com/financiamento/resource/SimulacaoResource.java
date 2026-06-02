package com.financiamento.resource;

import com.financiamento.dto.SimulacaoRequest;
import com.financiamento.dto.SimulacaoResponse;
import com.financiamento.service.SimulacaoService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

// Define a URL base de todos os endpoints desta classe
@Path("/simulacoes")
// Todos os métodos retornam JSON
@Produces(MediaType.APPLICATION_JSON)
// Todos os métodos recebem JSON
@Consumes(MediaType.APPLICATION_JSON)
// Nome do grupo no Swagger
@Tag(name = "Simulações", description = "Operações de simulação de financiamentos")
public class SimulacaoResource {

    // Quarkus injeta o Service automaticamente
    @Inject
    SimulacaoService service;

    // Documenta o endpoint no Swagger
    @Operation(summary = "Criar simulação", description = "Simula juros compostos e persiste no banco")
    @APIResponse(responseCode = "201", description = "Simulação criada com sucesso")
    @APIResponse(responseCode = "400", description = "Dados inválidos")
    // Responde requisições POST em /simulacoes
    @POST
    public Response criar(@Valid SimulacaoRequest request) {
        SimulacaoResponse response = service.calcular(request);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    // Documenta o endpoint no Swagger
    @Operation(summary = "Buscar simulação", description = "Busca uma simulação existente pelo ID")
    @APIResponse(responseCode = "200", description = "Simulação encontrada")
    @APIResponse(responseCode = "404", description = "Simulação não encontrada")
    // Responde requisições GET em /simulacoes/{id}
    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        SimulacaoResponse response = service.buscarPorId(id);

        if (response == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"erro\": \"Simulação não encontrada\"}")
                    .build();
        }

        return Response.ok(response).build();
    }
}