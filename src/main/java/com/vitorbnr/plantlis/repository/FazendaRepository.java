package com.vitorbnr.plantlis.repository;

import com.vitorbnr.plantlis.domain.entity.Fazenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FazendaRepository extends JpaRepository<Fazenda, UUID> {

    List<Fazenda> findByUsuarioId(UUID usuarioId);
}
