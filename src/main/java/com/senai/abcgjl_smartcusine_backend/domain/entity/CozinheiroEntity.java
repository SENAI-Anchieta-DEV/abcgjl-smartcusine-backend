package com.senai.abcgjl_smartcusine_backend.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "cozinheiros")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder

public class CozinheiroEntity extends Usuario {

    public void configurarTemporizador() {}
    public void registrarInsumo() {}
    public void consultarValidade() {}
    public void visualizarFichaTecnica() {}
}