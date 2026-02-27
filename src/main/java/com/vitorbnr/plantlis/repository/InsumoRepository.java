package com.vitorbnr.plantlis.repository;

import com.vitorbnr.plantlis.domain.entity.Insumo;
import com.vitorbnr.plantlis.domain.enums.TipoInsumo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface InsumoRepository extends JpaRepository<Insumo, UUID> {

    List<Insumo> findByFazendaId(UUID fazendaId);

    List<Insumo> findByFazendaIdAndTipo(UUID fazendaId, TipoInsumo tipo);

    /**
     * Retorna os insumos de uma fazenda cujo stock está abaixo do limiar de alerta.
     */
    @Query("SELECT i FROM Insumo i WHERE i.fazenda.id = :fazendaId AND i.quantidadeStock < i.stockMinimoAlerta")
    List<Insumo> findInsumosComStockBaixo(@Param("fazendaId") UUID fazendaId);
}
