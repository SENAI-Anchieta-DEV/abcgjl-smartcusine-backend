package com.senai.abcgjl_smartcusine_backend.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "relatorios")
@Getter
@Setter
@NoArgsConstructor
public class RelatorioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idRelatorio;

    private String tipo;
    private String data;

}