package com.vitorbnr.plantlis.service;

import com.vitorbnr.plantlis.domain.entity.Usuario;
import com.vitorbnr.plantlis.domain.enums.PerfilUsuario;
import com.vitorbnr.plantlis.dto.request.UsuarioRegistroDTO;
import com.vitorbnr.plantlis.dto.response.UsuarioResponseDTO;
import com.vitorbnr.plantlis.exception.EmailJaCadastradoException;
import com.vitorbnr.plantlis.exception.RecursoNaoEncontradoException;
import com.vitorbnr.plantlis.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Regista um novo utilizador com perfil PRODUTOR e senha encriptada.
     */
    @Transactional
    public UsuarioResponseDTO registrarNovoUsuario(UsuarioRegistroDTO dto) {
        if (usuarioRepository.existsByEmail(dto.email())) {
            throw new EmailJaCadastradoException(dto.email());
        }

        Usuario usuario = Usuario.builder()
                .nome(dto.nome())
                .email(dto.email())
                .senha(passwordEncoder.encode(dto.senha()))
                .perfil(PerfilUsuario.PRODUTOR)
                .ativo(true)
                .build();

        Usuario salvo = usuarioRepository.save(usuario);
        return UsuarioResponseDTO.fromEntity(salvo);
    }

    @Transactional(readOnly = true)
    public UsuarioResponseDTO buscarPorId(UUID id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário", id));
        return UsuarioResponseDTO.fromEntity(usuario);
    }

    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> listarTodos() {
        return usuarioRepository.findAll().stream()
                .map(UsuarioResponseDTO::fromEntity)
                .toList();
    }
}
