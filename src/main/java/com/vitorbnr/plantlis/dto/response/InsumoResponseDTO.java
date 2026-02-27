package com.vitorbnr.plantlis.dto.response;

import com.vitorbnr.plantlis.domain.entity.Insumo;
import com.vitorbnr.plantlis.domain.enums.TipoInsumo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record InsumoResponseDTO(
        UUID id,
        String nome,
        TipoInsumo tipo,
        String descricao,
        BigDecimal quantidadeStock,
        String unidadeMedida,
        BigDecimal stockMinimoAlerta,
        boolean alertaStock,
        UUID fazendaId,
        LocalDateTime criadoEm,
        LocalDateTime atualizadoEm) {

    public static InsumoResponseDTO fromEntity(Insumo insumo) {
        return new InsumoResponseDTO(
                insumo.getId(),
                insumo.getNome(),
                insumo.getTipo(),
                insumo.getDescricao(),
                insumo.getQuantidadeStock(),
                insumo.getUnidadeMedida(),
                insumo.getStockMinimoAlerta(),
                insumo.isStockBaixo(),
                insumo.getFazenda().getId(),
                insumo.getCriadoEm(),
                insumo.getAtualizadoEm());
    }
}
