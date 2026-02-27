package com.vitorbnr.plantlis.domain.entity;

import com.vitorbnr.plantlis.domain.enums.StatusTransporte;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transporte")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Transporte {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(nullable = false, length = 200)
    private String transportadora;

    @Column(name = "placa_veiculo", nullable = false, length = 20)
    private String placaVeiculo;

    @Column(nullable = false, length = 150)
    private String motorista;

    @Column(nullable = false, length = 300)
    private String destino;

    @Column(name = "data_despacho", nullable = false)
    private LocalDate dataDespacho;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "status_transporte")
    @Builder.Default
    private StatusTransporte status = StatusTransporte.PENDENTE;

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
    @JoinColumn(name = "lote_colheita_id", nullable = false)
    private LoteColheita loteColheita;
}
