package com.senai.abcgjl_smartcusine_backend.domain.repository;

import com.senai.abcgjl_smartcusine_backend.domain.entity.FichaTecnicaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FichaTecnicaRepository extends JpaRepository<FichaTecnicaEntity, UUID> {
    boolean existsByNomePreparo(String nomePreparo);
}
