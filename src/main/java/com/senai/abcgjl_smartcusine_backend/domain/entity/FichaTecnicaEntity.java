package com.senai.abcgjl_smartcusine_backend.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "fichas_tecnicas")
@Getter
@Setter
@NoArgsConstructor
public class FichaTecnicaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nomePreparo;
    private String tempoIdeal;
    private Double temperaturaIdeal;

    @OneToMany(mappedBy = "fichaTecnica", cascade = CascadeType.ALL)
    private List<FichaTecnicaInsumoEntity> insumos;
}
