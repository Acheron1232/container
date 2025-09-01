--liquibase formatted sql

--changeset artem:1
CREATE TABLE pizza
(
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name        VARCHAR(255) NOT NULL UNIQUE,
    description TEXT,
    images      TEXT[],
    price       FLOAT        NOT NULL
);


--changeset artem:2
CREATE TABLE facility
(
    id       UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name     VARCHAR(200) UNIQUE NOT NULL,
    address  VARCHAR(200) UNIQUE NOT NULL,
    city     VARCHAR(200)        NOT NULL,
    capacity INT                 NOT NULL
)