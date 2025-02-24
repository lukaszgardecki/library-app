--liquibase formatted sql
--changeset lukas:0007

insert into rack (location_identifier)
values
    ('123-I-12'),
    ('123-II-23'),
    ('123-III-34'),
    ('123-IV-45'),
    ('123-V-56'),
    ('123-VI-67'),
    ('123-VII-78');


insert into book_item (barcode, is_reference_only, borrowed, due_date, price, format, status, date_of_purchase, publication_date, book_id, rack_id)
values
    ('540200000001', true, null, null, 12.34, 'MAGAZINE', 'REQUESTED', '2023-01-28', '2023-01-29', 1, 1),
    ('540200000002', true, '2023-02-20', '2023-02-22', 10.56, 'MAGAZINE', 'AVAILABLE', '2023-01-28', '2023-01-29', 1, 1),
    ('540200000003', true, null, null, 12.34, 'MAGAZINE', 'LOANED', '2023-01-28', '2023-01-29', 1, 1),
    ('540200000004', true, null, null, 10.56, 'MAGAZINE', 'REQUESTED', '2023-01-28', '2023-01-29', 2, 1),
    ('540200000005', true, null, null, 12.34, 'MAGAZINE', 'REQUESTED', '2023-01-28', '2023-01-29', 2, 1),
    ('540200000006', true, '2023-02-20', '2023-03-22', 10.56, 'MAGAZINE', 'LOANED', '2023-01-28', '2023-01-29', 2, 1),
    ('540200000007', true, null, null, 12.34, 'MAGAZINE', 'REQUESTED', '2023-01-28', '2023-01-29', 3, 1),
    ('540200000008', true, '2023-02-20', '2023-03-22', 10.56, 'MAGAZINE', 'LOANED', '2023-01-28', '2023-01-29', 3, 1),
    ('540200000009', true, '2023-02-20', '2023-03-22', 12.34, 'MAGAZINE', 'LOANED', '2023-01-28', '2023-01-29', 3, 1),
    ('540200000010', true, '2023-02-20', '2023-03-22', 10.56, 'MAGAZINE', 'LOANED', '2023-01-28', '2023-01-29', 4, 1),

    ('540200000011', true, '2023-02-20', '2023-03-22', 12.34, 'JOURNAL', 'LOANED', '2023-01-28', '2023-01-29', 7, 1),
    ('540200000012', true, '2023-02-20', '2023-03-22', 12.34, 'JOURNAL', 'LOANED', '2023-01-28', '2023-01-29', 7, 1),
    ('540200000013', true, null, null, 12.34, 'JOURNAL', 'AVAILABLE', '2023-01-28', '2023-01-29', 8, 1),
    ('540200000014', true, null, null, 12.34, 'JOURNAL', 'AVAILABLE', '2023-01-28', '2023-01-29', 8, 1),
    ('540200000015', false, null, null, 12.34, 'JOURNAL', 'AVAILABLE', '2023-01-28', '2023-01-29', 9, 1),
    ('540200000016', true, null, null, 12.34, 'JOURNAL', 'AVAILABLE', '2023-01-28', '2023-01-29', 9, 1),
    ('540200000017', false, null, null, 12.34, 'JOURNAL', 'AVAILABLE', '2023-01-28', '2023-01-29', 9, 1),
    ('540200000018', true, null, null, 12.34, 'JOURNAL', 'AVAILABLE', '2023-01-28', '2023-01-29', 9, 1),
    ('540200000019', false, null, null, 12.34, 'JOURNAL', 'AVAILABLE', '2023-01-28', '2023-01-29', 9, 1),
    ('540200000020', true, null, null, 12.34, 'JOURNAL', 'AVAILABLE', '2023-01-28', '2023-01-29', 9, 1),

    ('540200000021', false, null, null, 40.00, 'NEWSPAPER', 'REQUESTED', '2023-01-28', '2023-01-29', 11, 2),
    ('540200000022', false, null, null, 40.00, 'NEWSPAPER', 'REQUESTED', '2023-01-28', '2023-01-29', 12, 2),
    ('540200000023', false, null, null, 40.00, 'NEWSPAPER', 'AVAILABLE', '2023-01-28', '2023-01-29', 12, 2),
    ('540200000024', false, null, null, 40.00, 'NEWSPAPER', 'AVAILABLE', '2023-01-28', '2023-01-29', 13, 2),
    ('540200000025', false, null, null, 40.00, 'NEWSPAPER', 'AVAILABLE', '2023-01-28', '2023-01-29', 13, 2),
    ('540200000026', false, null, null, 40.00, 'NEWSPAPER', 'AVAILABLE', '2023-01-28', '2023-01-29', 14, 2),
    ('540200000027', false, null, null, 40.00, 'NEWSPAPER', 'AVAILABLE', '2023-01-28', '2023-01-29', 14, 2),
    ('540200000028', false, null, null, 40.00, 'NEWSPAPER', 'AVAILABLE', '2023-01-28', '2023-01-29', 15, 2),
    ('540200000029', false, null, null, 40.00, 'NEWSPAPER', 'AVAILABLE', '2023-01-28', '2023-01-29', 15, 2),
    ('540200000030', false, null, null, 40.00, 'NEWSPAPER', 'AVAILABLE', '2023-01-28', '2023-01-29', 16, 2),

    ('540200000031', false, null, null, 10.00, 'EBOOK', 'AVAILABLE', '2023-01-28', '2023-01-29', 17, 3),
    ('540200000032', false, '2023-02-20', null, 10.00, 'EBOOK', 'LOANED', '2023-01-28', '2023-01-29', 17, 3),
    ('540200000033', false, '2023-02-20', null, 10.00, 'EBOOK', 'LOANED', '2023-01-28', '2023-01-29', 18, 3),
    ('540200000034', false, '2023-02-20', null, 10.00, 'EBOOK', 'LOANED', '2023-01-28', '2023-01-29', 19, 3),
    ('540200000035', false, '2023-02-20', null, 10.00, 'EBOOK', 'LOANED', '2023-01-28', '2023-01-29', 20, 3),
    ('540200000036', false, null, null, 10.00, 'EBOOK', 'AVAILABLE', '2023-01-28', '2023-01-29', 21, 3),
    ('540200000037', false, null, null, 10.00, 'EBOOK', 'AVAILABLE', '2023-01-28', '2023-01-29', 22, 3),
    ('540200000038', false, null, null, 10.00, 'EBOOK', 'AVAILABLE', '2023-01-28', '2023-01-29', 23, 3),
    ('540200000039', false, null, null, 10.00, 'EBOOK', 'AVAILABLE', '2023-01-28', '2023-01-29', 24, 3),
    ('540200000040', false, null, null, 10.00, 'EBOOK', 'AVAILABLE', '2023-01-28', '2023-01-29', 25, 3),

    ('540200000041', false, null, null, 1.00, 'AUDIO_BOOK', 'AVAILABLE', '2023-01-28', '2023-01-29', 25, 4),
    ('540200000042', false, null, null, 1.00, 'AUDIO_BOOK', 'AVAILABLE', '2023-01-28', '2023-01-29', 26, 4),
    ('540200000043', false, null, null, 1.00, 'AUDIO_BOOK', 'AVAILABLE', '2023-01-28', '2023-01-29', 26, 4),
    ('540200000044', false, null, null, 1.00, 'AUDIO_BOOK', 'AVAILABLE', '2023-01-28', '2023-01-29', 26, 4),
    ('540200000045', false, null, null, 1.00, 'AUDIO_BOOK', 'AVAILABLE', '2023-01-28', '2023-01-29', 27, 4),
    ('540200000046', false, null, null, 1.00, 'AUDIO_BOOK', 'AVAILABLE', '2023-01-28', '2023-01-29', 27, 4),
    ('540200000047', false, null, null, 1.00, 'AUDIO_BOOK', 'AVAILABLE', '2023-01-28', '2023-01-29', 27, 4),
    ('540200000048', false, null, null, 1.00, 'AUDIO_BOOK', 'AVAILABLE', '2023-01-28', '2023-01-29', 28, 4),
    ('540200000049', false, null, null, 1.00, 'AUDIO_BOOK', 'AVAILABLE', '2023-01-28', '2023-01-29', 28, 4),
    ('540200000050', false, null, null, 1.00, 'AUDIO_BOOK', 'AVAILABLE', '2023-01-28', '2023-01-29', 28, 4),

    ('540200000051', false, null, null, 78.00, 'PAPERBACK', 'AVAILABLE', '2023-01-28', '2023-01-29', 28, 5),
    ('540200000052', false, null, null, 78.00, 'PAPERBACK', 'AVAILABLE', '2023-01-28', '2023-01-29', 29, 5),
    ('540200000053', false, null, null, 78.00, 'PAPERBACK', 'AVAILABLE', '2023-01-28', '2023-01-29', 30, 5),
    ('540200000054', false, null, null, 78.00, 'PAPERBACK', 'AVAILABLE', '2023-01-28', '2023-01-29', 31, 5),
    ('540200000055', false, null, null, 78.00, 'PAPERBACK', 'AVAILABLE', '2023-01-28', '2023-01-29', 32, 5),
    ('540200000056', false, null, null, 78.00, 'PAPERBACK', 'AVAILABLE', '2023-01-28', '2023-01-29', 32, 5),
    ('540200000057', false, null, null, 78.00, 'PAPERBACK', 'AVAILABLE', '2023-01-28', '2023-01-29', 33, 5),
    ('540200000058', false, null, null, 78.00, 'PAPERBACK', 'AVAILABLE', '2023-01-28', '2023-01-29', 33, 5),
    ('540200000059', false, null, null, 78.00, 'PAPERBACK', 'AVAILABLE', '2023-01-28', '2023-01-29', 33, 5),
    ('540200000060', false, null, null, 78.00, 'PAPERBACK', 'AVAILABLE', '2023-01-28', '2023-01-29', 34, 5),

    ('540200000061', false, null, null, 178.00, 'HARDCOVER', 'LOST', '2023-01-28', '2023-01-29', 34, 6),
    ('540200000062', false, null, null, 178.00, 'HARDCOVER', 'LOST', '2023-01-28', '2023-01-29', 35, 6),
    ('540200000063', false, null, null, 178.00, 'HARDCOVER', 'AVAILABLE', '2023-01-28', '2023-01-29', 36, 6),
    ('540200000064', false, null, null, 178.00, 'HARDCOVER', 'AVAILABLE', '2023-01-28', '2023-01-29', 36, 6),
    ('540200000065', false, null, null, 178.00, 'HARDCOVER', 'AVAILABLE', '2023-01-28', '2023-01-29', 37, 6),
    ('540200000066', false, null, null, 178.00, 'HARDCOVER', 'AVAILABLE', '2023-01-28', '2023-01-29', 37, 6),
    ('540200000067', false, null, null, 178.00, 'HARDCOVER', 'AVAILABLE', '2023-01-28', '2023-01-29', 38, 6),
    ('540200000068', false, null, null, 178.00, 'HARDCOVER', 'AVAILABLE', '2023-01-28', '2023-01-29', 39, 6),
    ('540200000069', false, null, null, 178.00, 'HARDCOVER', 'AVAILABLE', '2023-01-28', '2023-01-29', 40, 6),
    ('540200000070', false, null, null, 178.00, 'HARDCOVER', 'AVAILABLE', '2023-01-28', '2023-01-29', 41, 6);
