--liquibase formatted sql
--changeset lukas:0008
insert into
    person(first_name, last_name, gender, phone, pesel, nationality, date_of_birth, mothers_name, fathers_name, street_address, city, state, zip_code, country)
values
    ('Kasia', 'Lubiczytać', 'FEMALE', '111-111-111', '91011212345', 'Angielskie', '1991-01-12', 'Julia', 'Jan', 'Konopacka 2d/23', 'Poznań', 'Śląsk', '11-111', 'Anglia'),
    ('Kamil', 'Nielubi', 'MALE', '222-222-222', '92021312345', 'Polskie', '1992-02-13', 'Agnieszka', 'Zygmunt', 'Adamskiego 5', 'Warszawa', 'Mazowsze', '22-222', 'Polska'),
    ('Adam', 'Mickiewicz', 'OTHER', '333-333-333', '93031412345', 'Hiszpańskie', '1993-03-14', 'Anna', 'Przemysław', 'Witosa 23/402', 'Katowice', 'Śląsk', '33-333', 'Hiszpania'),
    ('Ambroży',	'Kleks', 'MALE', '444-444-444', '94041512345', 'Niemieckie', '1994-04-15', 'Katarzyna', 'Dawid', 'Al. Korfantego 4b/2', 'Chorzów', 'Warmińsko-Mazurskie', '44-444', 'Niemcy'),
    ('Papa',	'Smerf', 'MALE',	'555-555-555', '95051612345', 'Chińskie', '1995-05-16', 'Anna', 'Łukasz', 'Powstańców Śląskich 34', 'Białystok', 'Lubelskie', '55-555', 'Chiny'),
    ('Lady',	'Gaga', 'MALE',	'666-666-666', '96061712345', 'Japońskie', '1996-06-17', 'Julita', 'Jakub', 'Kwiatowa 3', 'Warszawa', 'Kujawsko-Pomorskie', '66-666', 'Japonia'),
    ('Mietek',	'Żul', 'MALE',	'777-777-777', '97071812345', 'Francuskie', '1997-07-18', 'Dżesika', 'Andrzej', 'Ptasia 34', 'Most', 'Małopolska', '77-777', 'Francja'),
    ('Ojciec',	'Mateusz', 'MALE',	'888-888-888', '98081912345', 'Rosyjskie', '1998-08-19', 'Aneta', 'Dobromir', 'Barlickiego 13/31', 'Wielkopolska', 'State 8', '88-888', 'Rosja');