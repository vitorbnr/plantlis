package com.vitorbnr.plantlis.dto.response;

import com.vitorbnr.plantlis.domain.entity.Fazenda;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record FazendaResponseDTO(
        UUID id,
        String nome,
        String localizacao,
        BigDecimal areaTotalHectares,
        UUID usuarioId,
        String nomeUsuario,
        LocalDateTime criadoEm,
        LocalDateTime atualizadoEm) {

    public static FazendaResponseDTO fromEntity(Fazenda fazenda) {
        return new FazendaResponseDTO(
                fazenda.getId(),
                fazenda.getNome(),
                fazenda.getLocalizacao(),
                fazenda.getAreaTotalHectares(),
                fazenda.getUsuario().getId(),
                fazenda.getUsuario().getNome(),
                fazenda.getCriadoEm(),
                fazenda.getAtualizadoEm());
    }
}
