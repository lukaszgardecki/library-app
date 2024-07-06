--liquibase formatted sql
--changeset lukas:0014
insert into
    reserved_items_ids(member_id, book_item_id)
values
    (1, 4),
    (1, 5),
    (2, 7),
    (3, 13),
    (4, 1),
    (5, 21),
    (6, 3),
    (6, 8);