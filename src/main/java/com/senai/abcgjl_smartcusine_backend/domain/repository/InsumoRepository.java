package com.senai.abcgjl_smartcusine_backend.domain.repository;

import com.senai.abcgjl_smartcusine_backend.domain.entity.InsumoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InsumoRepository extends JpaRepository<InsumoEntity, UUID> {
}
