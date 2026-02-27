package com.vitorbnr.plantlis.service;

import com.vitorbnr.plantlis.domain.entity.Fazenda;
import com.vitorbnr.plantlis.domain.entity.LoteColheita;
import com.vitorbnr.plantlis.dto.request.LoteColheitaRequestDTO;
import com.vitorbnr.plantlis.dto.response.LoteColheitaResponseDTO;
import com.vitorbnr.plantlis.exception.RecursoNaoEncontradoException;
import com.vitorbnr.plantlis.repository.FazendaRepository;
import com.vitorbnr.plantlis.repository.LoteColheitaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class LoteColheitaService {

    private final LoteColheitaRepository loteColheitaRepository;
    private final FazendaRepository fazendaRepository;
    private final InsumoService insumoService;

    private final AtomicLong sequencial = new AtomicLong(0);

    /**
     * Regista um novo lote de colheita com código rastreável gerado
     * automaticamente.
     * Se insumoId e quantidadeInsumoUtilizada forem fornecidos, efetua baixa no
     * stock.
     */
    @Transactional
    public LoteColheitaResponseDTO registrar(LoteColheitaRequestDTO dto) {
        Fazenda fazenda = fazendaRepository.findById(dto.fazendaId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Fazenda", dto.fazendaId()));

        // ─── Gerar código de lote único ─────────────────────
        String codigoLote = gerarCodigoLote();

        LoteColheita lote = LoteColheita.builder()
                .codigoLote(codigoLote)
                .talhao(dto.talhao())
                .produto(dto.produto())
                .quantidadeColhida(dto.quantidadeColhida())
                .unidadeMedida(dto.unidadeMedida())
                .dataColheita(dto.dataColheita())
                .observacoes(dto.observacoes())
                .fazenda(fazenda)
                .build();

        LoteColheita salvo = loteColheitaRepository.save(lote);

        // ─── Baixa automática de stock do insumo utilizado ──
        if (dto.insumoId() != null && dto.quantidadeInsumoUtilizada() != null) {
            insumoService.deduzirStock(dto.insumoId(), dto.quantidadeInsumoUtilizada());
        }

        return LoteColheitaResponseDTO.fromEntity(salvo);
    }

    @Transactional(readOnly = true)
    public LoteColheitaResponseDTO buscarPorId(UUID id) {
        LoteColheita lote = loteColheitaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Lote de Colheita", id));
        return LoteColheitaResponseDTO.fromEntity(lote);
    }

    @Transactional(readOnly = true)
    public LoteColheitaResponseDTO buscarPorCodigo(String codigoLote) {
        LoteColheita lote = loteColheitaRepository.findByCodigoLote(codigoLote)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Lote com código " + codigoLote));
        return LoteColheitaResponseDTO.fromEntity(lote);
    }

    @Transactional(readOnly = true)
    public List<LoteColheitaResponseDTO> listarPorFazenda(UUID fazendaId) {
        return loteColheitaRepository.findByFazendaId(fazendaId).stream()
                .map(LoteColheitaResponseDTO::fromEntity)
                .toList();
    }

    @Transactional
    public void deletar(UUID id) {
        if (!loteColheitaRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Lote de Colheita", id);
        }
        loteColheitaRepository.deleteById(id);
    }

    // ─── Geração de código de lote ──────────────────────────
    private String gerarCodigoLote() {
        int ano = LocalDate.now().getYear();
        long seq = sequencial.incrementAndGet();
        String codigo;
        do {
            codigo = String.format("LOTE-%d-%05d", ano, seq);
            seq = sequencial.incrementAndGet();
        } while (loteColheitaRepository.findByCodigoLote(codigo).isPresent());

        return codigo;
    }
}
