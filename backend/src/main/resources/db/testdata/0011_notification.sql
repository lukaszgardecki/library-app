--liquibase formatted sql
--changeset lukas:0012
insert into
    notification(created_at, subject, content, book_id, book_title, member_id, type, read)
values
    ('2024-05-18 09:01:17.397449', 'Zrealizowanie zamówienia', 'The reservation has been successfully completed.', 4, 'Book 1', 2, 'REQUEST_COMPLETED', FALSE),
    ('2024-05-19 09:02:17.397449', 'Zrealizowanie zamówienia', 'The reservation has been successfully completed.', 5,  'Book 2', 2, 'REQUEST_COMPLETED', FALSE),
    ('2024-05-20 09:03:17.397449', 'Zrealizowanie zamówienia', 'The reservation has been successfully completed.', 6,  'Book 3', 2, 'REQUEST_COMPLETED', FALSE),
    ('2024-05-21 09:04:17.397449', 'Zrealizowanie zamówienia', 'The reservation has been successfully completed.', 7,  'Book 4', 5, 'REQUEST_COMPLETED', FALSE),
    ('2024-05-22 09:05:17.397449', 'Zrealizowanie zamówienia', 'The reservation has been successfully completed.', 8,  'Book 5', 5, 'REQUEST_COMPLETED', FALSE),
    ('2024-05-23 09:06:17.397449', 'Zrealizowanie zamówienia', 'The reservation has been successfully completed.', 9,  'Book 6', 5, 'REQUEST_COMPLETED', FALSE),
    ('2024-05-24 09:07:17.397449', 'Zrealizowanie zamówienia', 'The reservation has been successfully completed.', 10,  'Book 7', 5, 'REQUEST_COMPLETED', FALSE),
    ('2024-05-24 09:08:17.397449', 'Zrealizowanie zamówienia', 'The reservation has been successfully completed.', 11,  'Book 8', 5, 'REQUEST_COMPLETED', FALSE),
    ('2024-05-24 09:09:17.397449', 'Zrealizowanie zamówienia', 'The reservation has been successfully completed.', 12,  'Book 9', 5, 'REQUEST_COMPLETED', FALSE),
    ('2024-05-24 09:10:17.397449', 'Zrealizowanie zamówienia', 'The reservation has been successfully completed.', 13,  'Book 10', 5, 'REQUEST_COMPLETED', FALSE),
    ('2024-05-24 09:11:17.397449', 'Zrealizowanie zamówienia', 'The reservation has been successfully completed.', 14,  'Book 11', 5, 'REQUEST_COMPLETED', FALSE),
    ('2024-05-24 09:12:17.397449', 'Zrealizowanie zamówienia', 'The reservation has been successfully completed.', 15,  'Book 12', 5, 'REQUEST_COMPLETED', FALSE),
    ('2024-05-24 09:13:17.397449', 'Zrealizowanie zamówienia', 'The reservation has been successfully completed.', 16,  'Book 13', 5, 'REQUEST_COMPLETED', FALSE),
    ('2024-05-24 09:14:17.397449', 'Zrealizowanie zamówienia', 'The reservation has been successfully completed.', 17,  'Book 14', 5, 'REQUEST_COMPLETED', FALSE),
    ('2024-05-24 09:15:17.397449', 'Zrealizowanie zamówienia', 'The reservation has been successfully completed.', 18,  'Book 15', 5, 'REQUEST_COMPLETED', FALSE);



