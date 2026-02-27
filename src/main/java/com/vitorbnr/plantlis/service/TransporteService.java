package com.vitorbnr.plantlis.service;

import com.vitorbnr.plantlis.domain.entity.LoteColheita;
import com.vitorbnr.plantlis.domain.entity.Transporte;
import com.vitorbnr.plantlis.domain.enums.StatusTransporte;
import com.vitorbnr.plantlis.dto.request.TransporteRequestDTO;
import com.vitorbnr.plantlis.dto.response.TransporteResponseDTO;
import com.vitorbnr.plantlis.exception.RecursoNaoEncontradoException;
import com.vitorbnr.plantlis.repository.LoteColheitaRepository;
import com.vitorbnr.plantlis.repository.TransporteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransporteService {

    private final TransporteRepository transporteRepository;
    private final LoteColheitaRepository loteColheitaRepository;

    @Transactional
    public TransporteResponseDTO criar(TransporteRequestDTO dto) {
        LoteColheita lote = loteColheitaRepository.findById(dto.loteColheitaId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Lote de Colheita", dto.loteColheitaId()));

        Transporte transporte = Transporte.builder()
                .transportadora(dto.transportadora())
                .placaVeiculo(dto.placaVeiculo())
                .motorista(dto.motorista())
                .destino(dto.destino())
                .dataDespacho(dto.dataDespacho())
                .observacoes(dto.observacoes())
                .loteColheita(lote)
                .build();

        Transporte salvo = transporteRepository.save(transporte);
        return TransporteResponseDTO.fromEntity(salvo);
    }

    @Transactional(readOnly = true)
    public TransporteResponseDTO buscarPorId(UUID id) {
        Transporte transporte = transporteRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Transporte", id));
        return TransporteResponseDTO.fromEntity(transporte);
    }

    @Transactional(readOnly = true)
    public List<TransporteResponseDTO> listarPorLote(UUID loteColheitaId) {
        return transporteRepository.findByLoteColheitaId(loteColheitaId).stream()
                .map(TransporteResponseDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<TransporteResponseDTO> listarPorStatus(StatusTransporte status) {
        return transporteRepository.findByStatus(status).stream()
                .map(TransporteResponseDTO::fromEntity)
                .toList();
    }

    @Transactional
    public TransporteResponseDTO atualizarStatus(UUID id, StatusTransporte novoStatus) {
        Transporte transporte = transporteRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Transporte", id));

        transporte.setStatus(novoStatus);
        Transporte atualizado = transporteRepository.save(transporte);
        return TransporteResponseDTO.fromEntity(atualizado);
    }

    @Transactional
    public void deletar(UUID id) {
        if (!transporteRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Transporte", id);
        }
        transporteRepository.deleteById(id);
    }
}
