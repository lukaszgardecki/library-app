--liquibase formatted sql
--changeset lukas:0004
insert into
    reservation (creation_date, status, member_id, book_item_id)
values
    ('2023-05-20', 'COMPLETED', 1, 2),
    ('2023-05-21', 'COMPLETED', 2, 4),
    ('2023-05-22', 'COMPLETED', 3, 2),
    ('2023-05-23', 'COMPLETED', 4, 1),
    ('2023-05-24', 'COMPLETED', 5, 4),
    ('2023-05-25', 'COMPLETED', 6, 5),
    ('2023-05-26', 'COMPLETED', 7, 6),
    ('2023-05-27', 'READY', 2, 4),
    ('2023-05-27', 'READY', 2, 5),
    ('2023-05-28', 'PENDING', 3, 4);
