package com.vitorbnr.plantlis.dto.response;

import com.vitorbnr.plantlis.domain.entity.LoteColheita;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record LoteColheitaResponseDTO(
        UUID id,
        String codigoLote,
        String talhao,
        String produto,
        BigDecimal quantidadeColhida,
        String unidadeMedida,
        LocalDate dataColheita,
        String observacoes,
        UUID fazendaId,
        LocalDateTime criadoEm,
        LocalDateTime atualizadoEm) {

    public static LoteColheitaResponseDTO fromEntity(LoteColheita lote) {
        return new LoteColheitaResponseDTO(
                lote.getId(),
                lote.getCodigoLote(),
                lote.getTalhao(),
                lote.getProduto(),
                lote.getQuantidadeColhida(),
                lote.getUnidadeMedida(),
                lote.getDataColheita(),
                lote.getObservacoes(),
                lote.getFazenda().getId(),
                lote.getCriadoEm(),
                lote.getAtualizadoEm());
    }
}
