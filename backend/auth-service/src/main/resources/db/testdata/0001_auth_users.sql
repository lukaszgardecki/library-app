--liquibase formatted sql
--changeset lukas:0001
insert into
    user_auth(password, email, status, role, user_id)
values
--     password: adminpass
    ('{bcrypt}$2a$10$mbU0nK/DUpHgy1ncyAEUN.m8QE9gIXLP6DwYb9/WaFUVZy86/zIJ6','admin@example.com', 'ACTIVE', 'ADMIN', 1),
--     password: userpass
    ('{bcrypt}$2a$10$rOZ4x9b/F.Pu7L9awPKaB.c.11kk7.1IVvsjn9BsITGiXZCHIouC6', 'user@example.com', 'ACTIVE', 'USER', 2),
--     password: userpass1
    ('{bcrypt}$2a$10$ueasyNGh50WvWh11FiO5x.MATQKR/qcg.K3PqFclm7GPlh7UmWAoS', 'a.mickiewicz@gmail.com', 'CLOSED', 'USER', 3),
--     password: userpass2
    ('{bcrypt}$2a$10$B12U9HtWsrEEIijV295i6u0UNDrUprO6ueg6IHC7pYXi/5tRCFPf2', 'a.kleks@gmail.com', 'SUSPENDED', 'USER', 4),
--     password: userpass3
    ('{bcrypt}$2a$10$VMVSw5F7rVN/wo7C8YWEyutSr52TZ4lq2AFd7xvmOHrIa0B6tp.am', 'p.smerf@gmail.com', 'ACTIVE', 'USER', 5),
--     password: userpass4
    ('{bcrypt}$2a$10$dBf92dsiKShz8/b2sZGtJuy.YThoWU1q1.TSKOgA8TIsBLF0GsBNG', 'l.gaga@gmail.com', 'ACTIVE', 'CASHIER', 6),
--     password: userpass5
    ('{bcrypt}$2a$10$qmsW4vMJ1lmaX5YlAfU/2ug0DQaxC1h9hzqXBzMwqLw9VzbqeLjjq', 'm.zul@gmail.com', 'ACTIVE', 'WAREHOUSE', 7),
--     password: userpass6
    ('{bcrypt}$2a$10$FX8zhJB3Cky9.4iDP1M9R.Ip6ziJLq2GuP17.af9SzVchngLsbu1m', 'o.mateusz@gmail.com', 'INACTIVE',	 'USER', 8);