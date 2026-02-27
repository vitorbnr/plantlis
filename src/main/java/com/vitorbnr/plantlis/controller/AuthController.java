package com.vitorbnr.plantlis.controller;

import com.vitorbnr.plantlis.dto.request.UsuarioRegistroDTO;
import com.vitorbnr.plantlis.dto.response.ApiResponseDTO;
import com.vitorbnr.plantlis.dto.response.UsuarioResponseDTO;
import com.vitorbnr.plantlis.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticação", description = "Endpoints de registo e autenticação")
public class AuthController {

    private final UsuarioService usuarioService;

    @PostMapping("/registrar")
    @Operation(summary = "Registar novo utilizador", description = "Cria um novo utilizador com perfil PRODUTOR")
    public ResponseEntity<ApiResponseDTO> registrar(@Valid @RequestBody UsuarioRegistroDTO dto) {
        UsuarioResponseDTO usuario = usuarioService.registrarNovoUsuario(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDTO.of(
                        "Utilizador registado com sucesso! ID: " + usuario.id(),
                        HttpStatus.CREATED.value()));
    }
}
