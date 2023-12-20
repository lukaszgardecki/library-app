--liquibase formatted sql
--changeset lukas:0003
insert into
    lending(creation_date, due_date, return_date, status, member_id, book_item_id)
values
    ('2023-05-21', '2023-06-20', '2023-06-10', 'COMPLETED', 1, 1),
    ('2023-05-22', '2123-06-21', null, 'CURRENT', 5, 2),
    ('2023-05-23', '2023-06-22', null, 'CURRENT', 1, 3),
    ('2023-05-24', '2123-06-23', null, 'CURRENT', 4, 4),
    ('2023-05-25', '2123-06-24', null, 'CURRENT',1, 7),
    ('2023-05-26', '2023-06-25', '2023-06-12', 'COMPLETED', 6, 6);
