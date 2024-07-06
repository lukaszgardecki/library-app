--liquibase formatted sql
--changeset lukas:0013
insert into
    loaned_items_ids(member_id, book_item_id)
values
    (1, 3),
    (2, 6),
    (3, 8),
    (3, 9),
    (3, 10),
    (3, 11),
    (3, 12),
    (1, 32),
    (1, 33),
    (1, 34),
    (1, 35);



