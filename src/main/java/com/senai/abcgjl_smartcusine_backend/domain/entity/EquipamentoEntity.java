package com.senai.abcgjl_smartcusine_backend.domain.entity;


import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
    @Table(name = "equipamentos")
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

    protected EquipamentoEntity() {}

    public EquipamentoEntity(String tipo, Double temperaturaIdeal) {
        this.tipo = tipo;
        this.temperaturaIdeal = temperaturaIdeal;
    }

    public void consultarTemperatura() {}

    public boolean temperaturaForaDoPadrao() {
        return !temperaturaAtual.equals(temperaturaIdeal);
    }
}