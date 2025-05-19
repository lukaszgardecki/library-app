--liquibase formatted sql
--changeset lukas:0004
insert into
    genres(name, loans)
values
    ('Komedia', 5),
    ('Thriller', 6),
    ('Horror', 7),
    ('Dramat', 8),
    ('Kryminał', 9),
    ('Romans', 10);