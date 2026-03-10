package com.senai.abcgjl_smartcusine_backend.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "ficha_tecnica_insumos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class FichaTecnicaInsumoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "ficha_tecnica_id", nullable = false)
    private FichaTecnicaEntity fichaTecnica;

    @ManyToOne
    @JoinColumn(name = "insumo_id", nullable = false)
    private InsumoEntity insumo;

    private Double quantidade;

    private String unidade;
}
