--liquibase formatted sql
--changeset lukas:0016
insert into
    users(person_id, registration_date, total_books_borrowed, total_books_requested, charge, card_id)
values
    (1, '2023-05-21', 0, 0, 0, 1),
    (2, '2023-05-22', 0, 0, 0, 2),
    (3, '2023-05-23', 0, 0, 0, 3),
    (4, '2023-05-24', 0, 0, 0, 4),
    (5, '2023-05-25', 0, 0, 0, 5),
    (6, '2023-05-26', 0, 0, 0, 6),
    (7, '2023-05-27', 0, 0, 0, 7),
    (8, '2023-05-28', 0, 0, 0, 8);