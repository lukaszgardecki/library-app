--liquibase formatted sql
--changeset lukas:0004
insert into
    reservation (creation_date, status, member_id, book_item_id)
values
    ('2023-02-20', 'COMPLETED', 1, 2),
    ('2023-02-20', 'COMPLETED', 1, 3),
    ('2023-02-20', 'READY', 1, 4),
    ('2023-02-20', 'READY', 1, 5),

    ('2023-02-20', 'COMPLETED', 2, 6),
    ('2023-02-20', 'READY', 2, 7),

    ('2023-02-20', 'COMPLETED', 3, 8),
    ('2023-02-20', 'COMPLETED', 3, 9),
    ('2023-02-20', 'COMPLETED', 3, 10),
    ('2023-02-20', 'COMPLETED', 3, 11),
    ('2023-02-20', 'COMPLETED', 3, 12),
    ('2023-02-20', 'READY', 3, 13),

    ('2023-02-20', 'PENDING', 4, 1),

    ('2023-02-20', 'PENDING', 5, 21),

    ('2023-02-20', 'PENDING', 6, 3),
    ('2023-02-20', 'PENDING', 6, 8);

