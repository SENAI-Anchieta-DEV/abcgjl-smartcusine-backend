package com.senai.abcgjl_smartcusine_backend.domain.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
    @Table(name = "equipamentos")
@Getter
@Setter
@NoArgsConstructor
    public class EquipamentoEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        private UUID idEquipamento;

        private String tipo;
        private Double temperaturaAtual;
        private Double temperaturaIdeal;

        @OneToOne(mappedBy = "equipamento", cascade = CascadeType.ALL)
        private TemporizadorEntity temporizadorEntity;

        @OneToMany(mappedBy = "equipamento", cascade = CascadeType.ALL)
        private List<AlertaEntity> alertas;

        @OneToOne
        private FichaTecnicaEntity fichaTecnica;

    public void consultarTemperatura() {}

    public boolean temperaturaForaDoPadrao() {
        return !temperaturaAtual.equals(temperaturaIdeal);
    }
}