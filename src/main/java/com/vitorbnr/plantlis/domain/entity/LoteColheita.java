package com.vitorbnr.plantlis.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "lote_colheita")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class LoteColheita {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(name = "codigo_lote", nullable = false, unique = true, length = 50)
    private String codigoLote;

    @Column(nullable = false, length = 100)
    private String talhao;

    @Column(nullable = false, length = 150)
    private String produto;

    @Column(name = "quantidade_colhida", nullable = false, precision = 12, scale = 2)
    private BigDecimal quantidadeColhida;

    @Column(name = "unidade_medida", nullable = false, length = 20)
    private String unidadeMedida;

    @Column(name = "data_colheita", nullable = false)
    private LocalDate dataColheita;

    @Column(columnDefinition = "TEXT")
    private String observacoes;

    @CreationTimestamp
    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @UpdateTimestamp
    @Column(name = "atualizado_em", nullable = false)
    private LocalDateTime atualizadoEm;

    // ─── Relacionamentos ────────────────────────────────────
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fazenda_id", nullable = false)
    private Fazenda fazenda;

    @OneToMany(mappedBy = "loteColheita", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Transporte> transportes = new ArrayList<>();
}
