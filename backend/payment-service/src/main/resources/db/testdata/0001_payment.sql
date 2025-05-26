--liquibase formatted sql
--changeset lukas:0001
insert into
    payment(amount, creation_date, user_id, method, status, description)
values
    ('24.34', '2023-03-30 13:33:55', 1, 'CREDIT_CARD', 'FAILED', 'Late return'),
    ('24.34', '2023-03-30 13:35:23', 1, 'CREDIT_CARD', 'COMPLETED', 'Late return'),
    ('24.34', '2023-04-23 09:23:11', 2, 'CASH', 'COMPLETED', 'Lost book'),
    ('123.23', '2023-04-24 19:11:13', 3, 'CASH', 'COMPLETED', 'Lost book');