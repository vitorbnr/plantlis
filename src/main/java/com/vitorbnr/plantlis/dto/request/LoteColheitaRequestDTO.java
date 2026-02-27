package com.vitorbnr.plantlis.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record LoteColheitaRequestDTO(

        @NotBlank(message = "O talhão é obrigatório") String talhao,

        @NotBlank(message = "O produto é obrigatório") String produto,

        @NotNull(message = "A quantidade colhida é obrigatória") @Positive(message = "A quantidade colhida deve ser positiva") BigDecimal quantidadeColhida,

        @NotBlank(message = "A unidade de medida é obrigatória") String unidadeMedida,

        @NotNull(message = "A data de colheita é obrigatória") LocalDate dataColheita,

        String observacoes,

        @NotNull(message = "O ID da fazenda é obrigatório") UUID fazendaId,

        // ─── Campos opcionais para baixa de stock ───────────
        UUID insumoId,

        BigDecimal quantidadeInsumoUtilizada) {
}
