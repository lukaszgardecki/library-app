--liquibase formatted sql
--changeset lukas:0003
insert into
    auth_details(password, email, status, role, user_id)
values
--     password: adminpass
('{bcrypt}$2a$10$mbU0nK/DUpHgy1ncyAEUN.m8QE9gIXLP6DwYb9/WaFUVZy86/zIJ6','admin@example.com', 'ACTIVE', 'ADMIN', 1);