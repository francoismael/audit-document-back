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
    reference VARCHAR(100) NOT NULL UNIQUE,
    intitule VARCHAR(500) NOT NULL,
    objet TEXT,
    date_debut DATE,
    date_fin DATE,
    date_signature DATE,
    lieu VARCHAR(255),

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