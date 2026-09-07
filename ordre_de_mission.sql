CREATE DATABASE audit_document;

CREATE TABLE structure (
    id BIGSERIAL PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    descriptions TEXT
);


CREATE TABLE personne (
    id BIGSERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(150) NOT NULL,
    matricule VARCHAR(100),
    fonction VARCHAR(255),
    services VARCHAR(255)
);


CREATE TABLE mission (
    id BIGSERIAL PRIMARY KEY,
    numero VARCHAR(100) NOT NULL UNIQUE,
    intitule VARCHAR(500) NOT NULL,
    objet TEXT,
    date_debut DATE,
    date_fin DATE,
    date_signature DATE,
    structure_id BIGINT NOT NULL,

    CONSTRAINT fk_mission_structure
        FOREIGN KEY (structure_id)
        REFERENCES structure(id)
);


CREATE TABLE mission_personne (
    id BIGSERIAL PRIMARY KEY,

    mission_id BIGINT NOT NULL,
    personne_id BIGINT NOT NULL,

    roles VARCHAR(100) NOT NULL,

    CONSTRAINT fk_mp_mission
        FOREIGN KEY (mission_id)
        REFERENCES mission(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_mp_personne
        FOREIGN KEY (personne_id)
        REFERENCES personne(id),

    CONSTRAINT uq_mission_personne_role
        UNIQUE (mission_id, personne_id, roles)
);

CREATE TABLE declaration_independance (
    id BIGSERIAL PRIMARY KEY,

    mission_id BIGINT NOT NULL,

    personne_id BIGINT NOT NULL,

    date_declaration DATE NOT NULL,

    declaration_conflit TEXT,

    observation TEXT,

    CONSTRAINT fk_declaration_mission
        FOREIGN KEY (mission_id)
        REFERENCES mission(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_declaration_personne
        FOREIGN KEY (personne_id)
        REFERENCES personne(id),

    CONSTRAINT uq_declaration
        UNIQUE (mission_id, personne_id)
);

INSERT INTO structure (nom, descriptions)
VALUES
('Direction Générale des Finances', 'Structure chargée de la gestion financière'),
('Direction des Ressources Humaines', 'Structure chargée de la gestion des ressources humaines');

INSERT INTO personne (nom, prenom, matricule, fonction, services)
VALUES
('RAKOTO', 'Jean', 'MAT001', 'Chef de mission', 'Direction de l''Audit Interne'),
('RABE', 'Marie', 'MAT002', 'Auditeur interne', 'Direction de l''Audit Interne'),
('RANDRIA', 'Paul', 'MAT003', 'Superviseur', 'Direction de l''Audit Interne'),
('RAZAFINDRAKOTO', 'Luc', 'MAT004', 'Auditeur interne', 'Direction de l''Audit Interne'),
('ANDRIAMBOLOLONA', 'Sophie', 'MAT005', 'Directeur de l''Audit Interne', 'Direction de l''Audit Interne'),
('RAKOTOARISOA', 'Nadia', 'MAT006', 'Chef de mission', 'Direction de l''Audit Interne'),
('RABEMANANA', 'Hery', 'MAT007', 'Superviseur', 'Direction de l''Audit Interne');


