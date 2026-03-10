package com.senai.abcgjl_smartcusine_backend.domain.repository;

import com.senai.abcgjl_smartcusine_backend.domain.entity.TemporizadorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TemporizadorRepository extends JpaRepository<TemporizadorEntity, UUID> {
}
