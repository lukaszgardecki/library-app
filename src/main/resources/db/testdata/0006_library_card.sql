--liquibase formatted sql
--changeset lukas:0005
insert into
    library_card (barcode, issued_at, active)
values
    ('00000001', '2023-05-21 21:17:57', true),
    ('00000002', '2023-05-22 21:17:57', true),
    ('00000003', '2023-05-23 21:17:57', true),
    ('00000004', '2023-05-24 21:17:57', true),
    ('00000005', '2023-05-25 21:17:57', true),
    ('00000006', '2023-05-26 21:17:57', true),
    ('00000007', '2023-05-27 21:17:57', true),
    ('00000008', '2023-05-28 21:17:57', true);
