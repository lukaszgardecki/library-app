--liquibase formatted sql
--changeset lukas:0003
insert into
    lending(creation_date, due_date, return_date, status, member_id, book_item_id)
values
    ('2023-02-20', '2023-03-22', '2023-02-20', 'COMPLETED', 1, 2),
    ('2023-02-20', '2023-03-22', null, 'CURRENT', 1, 3),

    ('2023-02-20', '2025-03-22', null, 'CURRENT', 2, 6),

    ('2023-02-20', '2025-03-22', null, 'CURRENT', 3, 8),
    ('2023-02-20', '2023-03-22', null, 'CURRENT', 3, 9),
    ('2023-02-20', '2023-03-22', null, 'CURRENT', 3, 10),
    ('2023-02-20', '2023-03-22', null, 'CURRENT', 3, 11),
    ('2023-02-20', '2023-03-22', null, 'CURRENT', 3, 12);
