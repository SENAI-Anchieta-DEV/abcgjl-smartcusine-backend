package com.senai.abcgjl_smartcusine_backend.domain.repository;

import com.senai.abcgjl_smartcusine_backend.domain.entity.FichaTecnicaInsumoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FichaTecnicaInsumoRepository extends JpaRepository<FichaTecnicaInsumoEntity, UUID> {
    boolean existsByFichaTecnicaIdAndInsumoId(UUID fichaTecnicaId, UUID insumoId);
}
