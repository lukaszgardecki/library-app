--liquibase formatted sql
--changeset lukas:0016
insert into
    users(password, email, status, person_id, role, registration_date, total_books_borrowed, total_books_requested, charge, card_id)
values
--     password: adminpass
    ('{bcrypt}$2a$10$mbU0nK/DUpHgy1ncyAEUN.m8QE9gIXLP6DwYb9/WaFUVZy86/zIJ6','admin@example.com', 'ACTIVE', 1, 'ADMIN', '2023-05-21', 0, 0, 0, 1),
--     password: userpass
    ('{bcrypt}$2a$10$rOZ4x9b/F.Pu7L9awPKaB.c.11kk7.1IVvsjn9BsITGiXZCHIouC6', 'user@example.com', 'ACTIVE',  2, 'USER', '2023-05-22', 0, 0, 0, 2),
--     password: userpass1
    ('{bcrypt}$2a$10$ueasyNGh50WvWh11FiO5x.MATQKR/qcg.K3PqFclm7GPlh7UmWAoS', 'a.mickiewicz@gmail.com', 'CLOSED', 3, 'USER', '2023-05-23', 0, 0, 0, 3),
--     password: userpass2
    ('{bcrypt}$2a$10$B12U9HtWsrEEIijV295i6u0UNDrUprO6ueg6IHC7pYXi/5tRCFPf2', 'a.kleks@gmail.com', 'SUSPENDED', 4, 'USER', '2023-05-24', 0, 0, 0, 4),
--     password: userpass3
    ('{bcrypt}$2a$10$VMVSw5F7rVN/wo7C8YWEyutSr52TZ4lq2AFd7xvmOHrIa0B6tp.am', 'p.smerf@gmail.com', 'ACTIVE',	5, 'USER', '2023-05-25', 0, 0, 0, 5),
--     password: userpass4
    ('{bcrypt}$2a$10$dBf92dsiKShz8/b2sZGtJuy.YThoWU1q1.TSKOgA8TIsBLF0GsBNG', 'l.gaga@gmail.com', 'ACTIVE',	6, 'CASHIER', '2023-05-26', 0, 0, 0, 6),
--     password: userpass5
    ('{bcrypt}$2a$10$qmsW4vMJ1lmaX5YlAfU/2ug0DQaxC1h9hzqXBzMwqLw9VzbqeLjjq', 'm.zul@gmail.com', 'ACTIVE',	7, 'WAREHOUSE', '2023-05-27', 0, 0, 0, 7),
--     password: userpass6
    ('{bcrypt}$2a$10$FX8zhJB3Cky9.4iDP1M9R.Ip6ziJLq2GuP17.af9SzVchngLsbu1m', 'o.mateusz@gmail.com', 'INACTIVE',	8, 'USER', '2023-05-28', 0, 0, 0, 8);