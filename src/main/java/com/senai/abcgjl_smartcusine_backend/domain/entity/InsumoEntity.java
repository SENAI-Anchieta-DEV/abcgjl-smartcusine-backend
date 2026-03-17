package com.senai.abcgjl_smartcusine_backend.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "insumos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InsumoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nome;
    private String unidadeMedida;
    private Double quantidadeEstoque;
    private LocalDate dataValidade;
    private String qrCode;


    @OneToMany(mappedBy = "insumo")
    private List<FichaTecnicaInsumoEntity> fichasTecnicas;

}
