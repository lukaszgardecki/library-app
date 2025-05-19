--liquibase formatted sql
--changeset lukas:0001
insert into
    borrowers(first_name, last_name, birthday, user_id, loans_count, last_loan_date)
values
    ('Kasia', 'Lubiczytać', '1991-01-12', 1, 0, '2025-02-12'),
    ('Kamil', 'Nielubi', '1992-02-13', 2, 0, '2023-02-20'),
    ('Adam', 'Mickiewicz', '1993-03-14', 3, 0, '2023-02-20'),
    ('Ambroży',	'Kleks', '1994-04-15', 4, 0, null),
    ('Papa', 'Smerf', '1995-05-16', 5, 0, null),
    ('Lady', 'Gaga', '1996-06-17', 6, 0, null),
    ('Mietek', 'Żul', '1997-07-18', 7, 0, null),
    ('Ojciec', 'Mateusz', '1998-08-19', 8, 0, null);