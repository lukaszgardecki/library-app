--liquibase formatted sql
--changeset lukas:0002
insert into
    member(password, email, status, person_id, role, date_of_membership, total_books_borrowed, total_books_reserved, charge, library_card_id, fav_genre)
values
--     password: adminpass
    ('{bcrypt}$2a$10$mbU0nK/DUpHgy1ncyAEUN.m8QE9gIXLP6DwYb9/WaFUVZy86/zIJ6','admin@example.com', 'ACTIVE', 1, 'ADMIN', '2023-05-21', 5, 2, 0, 1, 'Fantasy'),
--     password: userpass
    ('{bcrypt}$2a$10$rOZ4x9b/F.Pu7L9awPKaB.c.11kk7.1IVvsjn9BsITGiXZCHIouC6', 'user@example.com', 'ACTIVE',  2, 'USER', '2023-05-22', 1, 1, 0, 2, 'Mystery'),
--     password: userpass1
    ('{bcrypt}$2a$10$ueasyNGh50WvWh11FiO5x.MATQKR/qcg.K3PqFclm7GPlh7UmWAoS', 'a.mickiewicz@gmail.com', 'CLOSED', 3, 'USER', '2023-05-23', 5, 1, 0, 3, 'Historical Fiction'),
--     password: userpass2
    ('{bcrypt}$2a$10$B12U9HtWsrEEIijV295i6u0UNDrUprO6ueg6IHC7pYXi/5tRCFPf2', 'a.kleks@gmail.com', 'SUSPENDED', 4, 'USER', '2023-05-24', 0, 1, 0, 4, 'Science Fiction'),
--     password: userpass3
    ('{bcrypt}$2a$10$VMVSw5F7rVN/wo7C8YWEyutSr52TZ4lq2AFd7xvmOHrIa0B6tp.am', 'p.smerf@gmail.com', 'ACTIVE',	5, 'USER', '2023-05-25', 0, 1, 0, 5, 'Thriller'),
--     password: userpass4
    ('{bcrypt}$2a$10$dBf92dsiKShz8/b2sZGtJuy.YThoWU1q1.TSKOgA8TIsBLF0GsBNG', 'l.gaga@gmail.com', 'ACTIVE',	6, 'CASHIER', '2023-05-26', 0, 2, 0, 6, 'Romance'),
--     password: userpass5
    ('{bcrypt}$2a$10$qmsW4vMJ1lmaX5YlAfU/2ug0DQaxC1h9hzqXBzMwqLw9VzbqeLjjq', 'm.zul@gmail.com', 'ACTIVE',	7, 'WAREHOUSE', '2023-05-27', 0, 0, 0, 7, 'Horror'),
--     password: userpass6
    ('{bcrypt}$2a$10$FX8zhJB3Cky9.4iDP1M9R.Ip6ziJLq2GuP17.af9SzVchngLsbu1m', 'o.mateusz@gmail.com', 'INACTIVE',	8, 'USER', '2023-05-28', 0, 0, 1.23, 8, 'Biography');