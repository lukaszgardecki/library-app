--liquibase formatted sql
--changeset lukas:0004
insert into
    reservation (creation_date, status, member_id, book_item_id)
values
    ('2023-05-20', 'COMPLETED', 1, 5),
    ('2023-05-21', 'COMPLETED', 2, 23),
    ('2023-05-22', 'COMPLETED', 3, 12),
    ('2023-05-23', 'COMPLETED', 4, 7),
    ('2023-05-24', 'COMPLETED', 5, 25),
    ('2023-05-25', 'COMPLETED', 6, 16);
