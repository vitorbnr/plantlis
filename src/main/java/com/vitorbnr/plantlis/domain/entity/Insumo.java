package com.vitorbnr.plantlis.domain.entity;

import com.vitorbnr.plantlis.domain.enums.TipoInsumo;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "insumo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Insumo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(nullable = false, length = 200)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "tipo_insumo")
    private TipoInsumo tipo;

    @Column(length = 500)
    private String descricao;

    @Column(name = "quantidade_stock", nullable = false, precision = 12, scale = 2)
    @Builder.Default
    private BigDecimal quantidadeStock = BigDecimal.ZERO;

    @Column(name = "unidade_medida", nullable = false, length = 20)
    private String unidadeMedida;

    @Column(name = "stock_minimo_alerta", nullable = false, precision = 12, scale = 2)
    @Builder.Default
    private BigDecimal stockMinimoAlerta = BigDecimal.ZERO;

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

    // ─── Lógica de domínio ──────────────────────────────────
    /**
     * Verifica se o stock atual está abaixo do limiar de alerta.
     */
    public boolean isStockBaixo() {
        return quantidadeStock.compareTo(stockMinimoAlerta) < 0;
    }
}
