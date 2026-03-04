package com.senai.abcgjl_smartcusine_backend.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@Table(name = "gerentes")
public class GerenteEntity  {


    public void visualizarPainel() {}
    public void gerarRelatorio() {}
    public void consultarAlertas() {}
}