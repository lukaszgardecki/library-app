--liquibase formatted sql
--changeset lukas:0004
insert into
    users(person_id, registration_date, total_books_borrowed, total_books_requested, charge, card_id)
values
    (1, '2023-05-21', 0, 0, 0, 1);

insert into
    person(first_name, last_name, gender, phone, pesel, nationality, date_of_birth, mothers_name, fathers_name, street_address, city, state, zip_code, country)
values
    ('Kasia', 'Lubiczytać', 'FEMALE', '111-111-111', '91011212345', 'Angielskie', '1991-01-12', 'Julia', 'Jan', 'Konopacka 2d/23', 'Poznań', 'Śląsk', '11-111', 'Anglia');

insert into
    library_card (barcode, issued_at, status, user_id)
values
    ('540100000001', '2023-05-21 10:01:54', 'ACTIVE', 1);