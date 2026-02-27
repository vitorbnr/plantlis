package com.vitorbnr.plantlis.dto.response;

import java.time.LocalDateTime;

public record ApiResponseDTO(
        String mensagem,
        int status,
        LocalDateTime timestamp) {

    public static ApiResponseDTO of(String mensagem, int status) {
        return new ApiResponseDTO(mensagem, status, LocalDateTime.now());
    }
}
