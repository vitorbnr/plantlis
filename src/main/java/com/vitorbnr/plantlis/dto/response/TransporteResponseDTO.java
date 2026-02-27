package com.vitorbnr.plantlis.dto.response;

import com.vitorbnr.plantlis.domain.entity.Transporte;
import com.vitorbnr.plantlis.domain.enums.StatusTransporte;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record TransporteResponseDTO(
        UUID id,
        String transportadora,
        String placaVeiculo,
        String motorista,
        String destino,
        LocalDate dataDespacho,
        StatusTransporte status,
        String observacoes,
        UUID loteColheitaId,
        String codigoLote,
        LocalDateTime criadoEm,
        LocalDateTime atualizadoEm) {

    public static TransporteResponseDTO fromEntity(Transporte transporte) {
        return new TransporteResponseDTO(
                transporte.getId(),
                transporte.getTransportadora(),
                transporte.getPlacaVeiculo(),
                transporte.getMotorista(),
                transporte.getDestino(),
                transporte.getDataDespacho(),
                transporte.getStatus(),
                transporte.getObservacoes(),
                transporte.getLoteColheita().getId(),
                transporte.getLoteColheita().getCodigoLote(),
                transporte.getCriadoEm(),
                transporte.getAtualizadoEm());
    }
}
