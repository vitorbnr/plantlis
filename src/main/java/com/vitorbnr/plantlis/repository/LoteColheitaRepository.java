package com.vitorbnr.plantlis.repository;

import com.vitorbnr.plantlis.domain.entity.LoteColheita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LoteColheitaRepository extends JpaRepository<LoteColheita, UUID> {

    List<LoteColheita> findByFazendaId(UUID fazendaId);

    Optional<LoteColheita> findByCodigoLote(String codigoLote);
}
