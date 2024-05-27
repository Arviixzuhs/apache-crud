CREATE DATABASE IF NOT EXISTS parcialdos;

USE parcialdos;

CREATE TABLE
    IF NOT EXISTS materia (
        idMateria INT NOT NULL AUTO_INCREMENT,
        nombreMateria VARCHAR(25) NOT NULL,
        unidadesValorativas VARCHAR(25) NOT NULL,
        descripcionMateria VARCHAR(25) NOT NULL,
        estadoMateria VARCHAR(25) NOT NULL,
        PRIMARY KEY (idMateria)
    );

INSERT INTO
    materia (
        idMateria,
        nombreMateria,
        unidadesValorativas,
        descripcionMateria,
        estadoMateria
    )
VALUES
    (1, "Matematica", "3", "descripcion", "aprobado"),
    (2, "Ingles", "3", "descripcion", "aprobado"),
    (3, "Algoritmica", "3", "descripcion", "aprobado"),
    (4, "Redes", "3", "descripcion", "aprobado"),
    (5, "Paradigmas", "3", "descripcion", "aprobado")