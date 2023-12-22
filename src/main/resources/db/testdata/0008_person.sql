--liquibase formatted sql
--changeset lukas:0008
insert into
    person(first_name, last_name, phone, street_address, city, state, zip_code, country)
values
    ('Kasia', 'Lubiczytać', '111-111-111', 'Street 1', 'City 1', 'State 1', 'Zip 1', 'Country 1'),
    ('Kamil', 'Nielubi', '222-222-222', 'Street 2', 'City 2', 'State 2', 'Zip 2', 'Country 2'),
    ('Adam', 'Mickiewicz', '333-333-333', 'Street 3', 'City 3', 'State 3', 'Zip 3', 'Country 3'),
    ('Ambroży',	'Kleks', '444-444-444', 'Street 4', 'City 4', 'State 4', 'Zip 4', 'Country 4'),
    ('Papa',	'Smerf',	'555-555-555', 'Street 5', 'City 5', 'State 5', 'Zip 5', 'Country 5'),
    ('Lady',	'Gaga',	'666-666-666', 'Street 6', 'City 6', 'State 6', 'Zip 6', 'Country 6'),
    ('Mietek',	'Żul',	'777-777-777', 'Street 7', 'City 7', 'State 7', 'Zip 7', 'Country 7'),
    ('Ojciec',	'Mateusz',	'888-888-888', 'Street 8', 'City 8', 'State 8', 'Zip 8', 'Country 8');