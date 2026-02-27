package com.vitorbnr.plantlis.dto.request;

import com.vitorbnr.plantlis.domain.enums.TipoInsumo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.util.UUID;

public record InsumoRequestDTO(

        @NotBlank(message = "O nome do insumo é obrigatório") String nome,

        @NotNull(message = "O tipo do insumo é obrigatório") TipoInsumo tipo,

        String descricao,

        @NotNull(message = "A quantidade em stock é obrigatória") @PositiveOrZero(message = "A quantidade em stock não pode ser negativa") BigDecimal quantidadeStock,

        @NotBlank(message = "A unidade de medida é obrigatória") String unidadeMedida,

        @NotNull(message = "O limiar de alerta de stock é obrigatório") @PositiveOrZero(message = "O limiar de alerta não pode ser negativo") BigDecimal stockMinimoAlerta,

        @NotNull(message = "O ID da fazenda é obrigatório") UUID fazendaId) {
}
