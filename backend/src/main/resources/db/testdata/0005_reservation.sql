--liquibase formatted sql
--changeset lukas:0004
insert into
    reservation (creation_date, status, member_id, book_item_id)
values
    ('2023-02-20 00:23:01', 'COMPLETED', 1, 2),
    ('2023-02-20 01:24:02', 'COMPLETED', 1, 3),
    ('2023-02-20 02:25:03', 'READY', 1, 4),
    ('2023-02-20 03:26:04', 'READY', 1, 5),

    ('2023-02-20 04:27:05', 'COMPLETED', 2, 6),
    ('2023-02-20 05:28:06', 'READY', 2, 7),

    ('2023-02-20 06:29:07', 'COMPLETED', 3, 8),
    ('2023-02-20 07:30:08', 'COMPLETED', 3, 9),
    ('2023-02-20 08:31:09', 'COMPLETED', 3, 10),
    ('2023-02-20 09:32:10', 'COMPLETED', 3, 11),
    ('2023-02-20 10:33:11', 'COMPLETED', 3, 12),
    ('2023-02-20 11:34:12', 'READY', 3, 13),

    ('2023-02-20 12:35:13', 'PENDING', 4, 1),

    ('2023-02-20 13:36:14', 'PENDING', 5, 21),

    ('2023-02-20 14:37:15', 'PENDING', 6, 3),
    ('2023-02-20 15:38:16', 'PENDING', 6, 8);

