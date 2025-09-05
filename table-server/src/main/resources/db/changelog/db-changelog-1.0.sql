--liquibase formatted sql

--changeset artem:1
CREATE TABLE restaurant_table
(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    capacity INT NOT NULL ,
    facility_id UUID NOT NULL ,
    status VARCHAR(50) NOT NULL
)