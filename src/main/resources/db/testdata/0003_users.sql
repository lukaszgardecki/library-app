--liquibase formatted sql
--changeset lukas:0002
insert into
    users(first_name, last_name, email, password, card_id, role_id)
values
--     password: adminpass
    ('Kasia', 'Lubiczytać','admin@example.com', '{bcrypt}$2a$10$mbU0nK/DUpHgy1ncyAEUN.m8QE9gIXLP6DwYb9/WaFUVZy86/zIJ6', 1, 1),
--     password: userpass
    ('Kamil', 'Nielubi', 'user@example.com', '{bcrypt}$2a$10$rOZ4x9b/F.Pu7L9awPKaB.c.11kk7.1IVvsjn9BsITGiXZCHIouC6', 2, 2),
--     password: userpass1
    ('Adam', 'Mickiewicz', 'a.mickiewicz@gmail.com', '{bcrypt}$2a$10$ueasyNGh50WvWh11FiO5x.MATQKR/qcg.K3PqFclm7GPlh7UmWAoS', 3, 2),
--     password: userpass2
    ('Ambroży',	'Kleks', 'a.kleks@gmail.com', '{bcrypt}$2a$10$B12U9HtWsrEEIijV295i6u0UNDrUprO6ueg6IHC7pYXi/5tRCFPf2', 4, 2),
--     password: userpass3
    ('Papa',	'Smerf',	'p.smerf@gmail.com',	'{bcrypt}$2a$10$VMVSw5F7rVN/wo7C8YWEyutSr52TZ4lq2AFd7xvmOHrIa0B6tp.am', 5, 2),
--     password: userpass4
    ('Lady',	'Gaga',	'l.gaga@gmail.com',	'{bcrypt}$2a$10$dBf92dsiKShz8/b2sZGtJuy.YThoWU1q1.TSKOgA8TIsBLF0GsBNG', 6, 2),
--     password: userpass5
    ('Mietek',	'Żul',	'm.zul@gmail.com',	'{bcrypt}$2a$10$qmsW4vMJ1lmaX5YlAfU/2ug0DQaxC1h9hzqXBzMwqLw9VzbqeLjjq', 7, 2),
--     password: userpass6
    ('Ojciec',	'Mateusz',	'o.mateusz@gmail.com',	'{bcrypt}$2a$10$FX8zhJB3Cky9.4iDP1M9R.Ip6ziJLq2GuP17.af9SzVchngLsbu1m', 8, 2);