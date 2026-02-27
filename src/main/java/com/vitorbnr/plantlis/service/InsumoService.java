package com.vitorbnr.plantlis.service;

import com.vitorbnr.plantlis.domain.entity.Fazenda;
import com.vitorbnr.plantlis.domain.entity.Insumo;
import com.vitorbnr.plantlis.dto.request.InsumoRequestDTO;
import com.vitorbnr.plantlis.dto.response.InsumoResponseDTO;
import com.vitorbnr.plantlis.exception.EstoqueInsuficienteException;
import com.vitorbnr.plantlis.exception.RecursoNaoEncontradoException;
import com.vitorbnr.plantlis.repository.FazendaRepository;
import com.vitorbnr.plantlis.repository.InsumoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InsumoService {

    private final InsumoRepository insumoRepository;
    private final FazendaRepository fazendaRepository;

    @Transactional
    public InsumoResponseDTO criar(InsumoRequestDTO dto) {
        Fazenda fazenda = fazendaRepository.findById(dto.fazendaId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Fazenda", dto.fazendaId()));

        Insumo insumo = Insumo.builder()
                .nome(dto.nome())
                .tipo(dto.tipo())
                .descricao(dto.descricao())
                .quantidadeStock(dto.quantidadeStock())
                .unidadeMedida(dto.unidadeMedida())
                .stockMinimoAlerta(dto.stockMinimoAlerta())
                .fazenda(fazenda)
                .build();

        Insumo salvo = insumoRepository.save(insumo);
        return InsumoResponseDTO.fromEntity(salvo);
    }

    @Transactional(readOnly = true)
    public InsumoResponseDTO buscarPorId(UUID id) {
        Insumo insumo = insumoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Insumo", id));
        return InsumoResponseDTO.fromEntity(insumo);
    }

    @Transactional(readOnly = true)
    public List<InsumoResponseDTO> listarPorFazenda(UUID fazendaId) {
        return insumoRepository.findByFazendaId(fazendaId).stream()
                .map(InsumoResponseDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<InsumoResponseDTO> listarAlertasDeStock(UUID fazendaId) {
        return insumoRepository.findInsumosComStockBaixo(fazendaId).stream()
                .map(InsumoResponseDTO::fromEntity)
                .toList();
    }

    @Transactional
    public InsumoResponseDTO atualizar(UUID id, InsumoRequestDTO dto) {
        Insumo insumo = insumoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Insumo", id));

        insumo.setNome(dto.nome());
        insumo.setTipo(dto.tipo());
        insumo.setDescricao(dto.descricao());
        insumo.setQuantidadeStock(dto.quantidadeStock());
        insumo.setUnidadeMedida(dto.unidadeMedida());
        insumo.setStockMinimoAlerta(dto.stockMinimoAlerta());

        Insumo atualizado = insumoRepository.save(insumo);
        return InsumoResponseDTO.fromEntity(atualizado);
    }

    @Transactional
    public void deletar(UUID id) {
        if (!insumoRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Insumo", id);
        }
        insumoRepository.deleteById(id);
    }

    /**
     * Deduz a quantidade utilizada do stock de um insumo.
     * Lança EstoqueInsuficienteException se o stock for insuficiente.
     */
    @Transactional
    public void deduzirStock(UUID insumoId, BigDecimal quantidade) {
        Insumo insumo = insumoRepository.findById(insumoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Insumo", insumoId));

        BigDecimal novoStock = insumo.getQuantidadeStock().subtract(quantidade);

        if (novoStock.compareTo(BigDecimal.ZERO) < 0) {
            throw new EstoqueInsuficienteException(insumo.getNome());
        }

        insumo.setQuantidadeStock(novoStock);
        insumoRepository.save(insumo);
    }
}
