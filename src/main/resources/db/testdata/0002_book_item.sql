--liquibase formatted sql
--changeset lukas:0007
insert into book_item (barcode, is_reference_only, borrowed, due_date, price, format, status, date_of_purchase, publication_date, book_id)
values
    ('540200000001', true, '2023-05-21', '2023-06-10', 12.34, 'MAGAZINE', 'AVAILABLE', '2023-01-28', '2023-02-28', 1),
    ('540200000002', false, '2023-05-22', '2023-06-11', 23.45, 'EBOOK', 'AVAILABLE', '2023-01-28', '2023-02-28', 2),
    ('540200000003', false, '2023-05-23', '2023-06-22', 34.24, 'MAGAZINE', 'LOANED', '2023-01-20', '2023-02-23', 24),
    ('540200000004', false, '2023-05-24', '2023-06-23', 234.44, 'MAGAZINE', 'LOANED', '2023-01-20', '2023-02-23', 11),
    ('540200000005', false, '2023-05-25', '2023-06-24', 77.44, 'MAGAZINE', 'LOANED', '2023-01-20', '2023-02-23', 5),
    ('540200000006', false, '2023-05-26', '2023-06-25', 1.44, 'MAGAZINE', 'AVAILABLE', '2023-01-20', '2023-02-23', 7),
    ('540200000007', false, '2023-05-26', '2023-06-25', 1.5, 'MAGAZINE', 'RESERVED', '2023-01-20', '2023-02-23', 8);
