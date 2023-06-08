--liquibase formatted sql
--changeset lukas:0002
insert into
    user_role (name)
values
    ('ADMIN'),
    ('USER');