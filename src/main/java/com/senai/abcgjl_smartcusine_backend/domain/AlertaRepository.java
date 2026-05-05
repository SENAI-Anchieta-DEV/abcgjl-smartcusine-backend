package com.senai.abcgjl_smartcusine_backend.domain;

import com.senai.abcgjl_smartcusine_backend.domain.entity.AlertaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AlertaRepository extends JpaRepository <AlertaEntity, UUID> {
}
