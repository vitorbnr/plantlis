package com.vitorbnr.plantlis.repository;

import com.vitorbnr.plantlis.domain.entity.Transporte;
import com.vitorbnr.plantlis.domain.enums.StatusTransporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransporteRepository extends JpaRepository<Transporte, UUID> {

    List<Transporte> findByLoteColheitaId(UUID loteColheitaId);

    List<Transporte> findByStatus(StatusTransporte status);
}
