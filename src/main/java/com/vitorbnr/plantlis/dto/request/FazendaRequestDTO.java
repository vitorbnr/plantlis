package com.vitorbnr.plantlis.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public record FazendaRequestDTO(

        @NotBlank(message = "O nome da fazenda é obrigatório") String nome,

        @NotBlank(message = "A localização é obrigatória") String localizacao,

        @NotNull(message = "A área total é obrigatória") @Positive(message = "A área total deve ser positiva") BigDecimal areaTotalHectares,

        @NotNull(message = "O ID do usuário é obrigatório") UUID usuarioId) {
}
