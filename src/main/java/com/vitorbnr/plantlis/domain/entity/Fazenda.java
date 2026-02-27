package com.vitorbnr.plantlis.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "fazenda")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Fazenda {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(nullable = false, length = 200)
    private String nome;

    @Column(nullable = false, length = 300)
    private String localizacao;

    @Column(name = "area_total_hectares", nullable = false, precision = 12, scale = 2)
    private BigDecimal areaTotalHectares;

    @CreationTimestamp
    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @UpdateTimestamp
    @Column(name = "atualizado_em", nullable = false)
    private LocalDateTime atualizadoEm;

    // ─── Relacionamentos ────────────────────────────────────
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @OneToMany(mappedBy = "fazenda", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Insumo> insumos = new ArrayList<>();

    @OneToMany(mappedBy = "fazenda", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<LoteColheita> lotesColheita = new ArrayList<>();
}
