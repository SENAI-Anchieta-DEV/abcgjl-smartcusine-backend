CREATE DATABASE cuisine;
USE cuisine;

CREATE TABLE Usuario (
                         idUsuario INT AUTO_INCREMENT PRIMARY KEY,
                         nome VARCHAR(100) NOT NULL,
                         cpf VARCHAR(14) NOT NULL UNIQUE,
                         email VARCHAR(150) NOT NULL UNIQUE,
                         senha VARCHAR(255) NOT NULL,
                         tipoUsuario VARCHAR(30) NOT NULL
);

CREATE TABLE FichaTecnica (
                              idFicha INT AUTO_INCREMENT PRIMARY KEY,
                              nomePreparo VARCHAR(100) NOT NULL,
                              temperaturaMin DECIMAL(5,2) NOT NULL,
                              temperaturaMax DECIMAL(5,2) NOT NULL,
                              tempoIdeal INT NOT NULL,
                              tolerancia INT NOT NULL DEFAULT 0,
                              codigoQR VARCHAR(255) UNIQUE
);

CREATE TABLE Equipamento (
                             idEquipamento INT AUTO_INCREMENT PRIMARY KEY,
                             nome VARCHAR(100) NOT NULL,
                             tipoEquipamento VARCHAR(70) NOT NULL,
                             statusEquipamento VARCHAR(30) NOT NULL,
                             temperaturaAtual DECIMAL(5,2)
);

CREATE TABLE ProcessoPreparo (
                                 idProcesso INT AUTO_INCREMENT PRIMARY KEY,
                                 idEquipamento INT NOT NULL,
                                 idFicha INT NOT NULL,
                                 statusPreparo VARCHAR(30) NOT NULL,
                                 tempoDecorrido INT NOT NULL,
                                 temperaturaAtual DECIMAL(5,2),
                                 FOREIGN KEY (idEquipamento) REFERENCES Equipamento(idEquipamento) ON DELETE CASCADE,
                                 FOREIGN KEY (idFicha) REFERENCES FichaTecnica(idFicha) ON DELETE CASCADE
);

CREATE TABLE Insumo (
                        idInsumo INT AUTO_INCREMENT PRIMARY KEY,
                        nome VARCHAR(100) NOT NULL,
                        dataFabricacao DATE NOT NULL,
                        dataValidade DATE NOT NULL,
                        statusInsumo VARCHAR(30) NOT NULL,
                        codigoQR VARCHAR(255) UNIQUE,
                        idUsuario INT NOT NULL,
                        FOREIGN KEY (idUsuario) REFERENCES Usuario(idUsuario) ON DELETE CASCADE
);

CREATE TABLE Alerta (
                        idAlerta INT AUTO_INCREMENT PRIMARY KEY,
                        tipo VARCHAR(50) NOT NULL,
                        nivelSeveridade VARCHAR(20) NOT NULL,
                        mensagem TEXT,
                        idProcesso INT NULL,
                        idInsumo INT NULL,
                        FOREIGN KEY (idProcesso) REFERENCES ProcessoPreparo(idProcesso) ON DELETE CASCADE,
                        FOREIGN KEY (idInsumo) REFERENCES Insumo(idInsumo) ON DELETE CASCADE
);