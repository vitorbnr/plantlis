package com.vitorbnr.plantlis.service;

import com.vitorbnr.plantlis.domain.entity.Fazenda;
import com.vitorbnr.plantlis.domain.entity.Usuario;
import com.vitorbnr.plantlis.dto.request.FazendaRequestDTO;
import com.vitorbnr.plantlis.dto.response.FazendaResponseDTO;
import com.vitorbnr.plantlis.exception.RecursoNaoEncontradoException;
import com.vitorbnr.plantlis.repository.FazendaRepository;
import com.vitorbnr.plantlis.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FazendaService {

    private final FazendaRepository fazendaRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public FazendaResponseDTO criar(FazendaRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.usuarioId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário", dto.usuarioId()));

        Fazenda fazenda = Fazenda.builder()
                .nome(dto.nome())
                .localizacao(dto.localizacao())
                .areaTotalHectares(dto.areaTotalHectares())
                .usuario(usuario)
                .build();

        Fazenda salva = fazendaRepository.save(fazenda);
        return FazendaResponseDTO.fromEntity(salva);
    }

    @Transactional(readOnly = true)
    public FazendaResponseDTO buscarPorId(UUID id) {
        Fazenda fazenda = fazendaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Fazenda", id));
        return FazendaResponseDTO.fromEntity(fazenda);
    }

    @Transactional(readOnly = true)
    public List<FazendaResponseDTO> listarPorUsuario(UUID usuarioId) {
        return fazendaRepository.findByUsuarioId(usuarioId).stream()
                .map(FazendaResponseDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<FazendaResponseDTO> listarTodas() {
        return fazendaRepository.findAll().stream()
                .map(FazendaResponseDTO::fromEntity)
                .toList();
    }

    @Transactional
    public FazendaResponseDTO atualizar(UUID id, FazendaRequestDTO dto) {
        Fazenda fazenda = fazendaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Fazenda", id));

        fazenda.setNome(dto.nome());
        fazenda.setLocalizacao(dto.localizacao());
        fazenda.setAreaTotalHectares(dto.areaTotalHectares());

        Fazenda atualizada = fazendaRepository.save(fazenda);
        return FazendaResponseDTO.fromEntity(atualizada);
    }

    @Transactional
    public void deletar(UUID id) {
        if (!fazendaRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Fazenda", id);
        }
        fazendaRepository.deleteById(id);
    }
}
