package com.vitorbnr.plantlis.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record TransporteRequestDTO(

        @NotBlank(message = "A transportadora é obrigatória") String transportadora,

        @NotBlank(message = "A placa do veículo é obrigatória") String placaVeiculo,

        @NotBlank(message = "O nome do motorista é obrigatório") String motorista,

        @NotBlank(message = "O destino é obrigatório") String destino,

        @NotNull(message = "A data de despacho é obrigatória") LocalDate dataDespacho,

        String observacoes,

        @NotNull(message = "O ID do lote de colheita é obrigatório") UUID loteColheitaId) {
}
