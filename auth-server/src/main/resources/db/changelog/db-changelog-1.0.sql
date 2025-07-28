-- --liquibase formatted sql

--changeset artem:1
create table users
(
    id bigserial primary key,
    email text unique not null ,
    username text unique not null ,
    password text ,
    role text not null default 'USER'
)