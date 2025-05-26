--liquibase formatted sql
--changeset lukas:0003
insert into
    cities(name, users)
values
    ('Jeruzalem', 1),
    ('Sosnowiec', 2),
    ('Bytom', 3),
    ('Warszawa', 4),
    ('Katowice', 5),
    ('Wałbrzych', 6);