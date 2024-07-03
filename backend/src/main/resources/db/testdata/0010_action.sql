--liquibase formatted sql
--changeset lukas:0011
insert into
    action(member_id, type, message, created_at)
values
    (1, 'LOGIN', 'Zalogowanie czytelnika Kasia Lubiczytać, Nr karty 540100000001', '2024-06-05 22:40:34.730348'),
    (1, 'REQUEST_NEW', 'Wysłano zamówienie Araya', '2024-06-05 22:40:34.845347'),
    (1, 'NOTIFICATION_EMAIL', 'Wysłano e-mail do czytelnika (Utworzenie zamówienia) 540200000002', '2024-06-05 22:40:34.846347'),
    (1, 'NOTIFICATION_SMS', 'Wysłano wiadomość SMS do czytelnika (Utworzenie zamówienia) 540200000002', '2024-06-05 22:40:34.847346'),
    (1, 'REQUEST_COMPLETED', 'Zrealizowano Araya', '2024-06-05 22:40:35.028348'),
    (1, 'NOTIFICATION_EMAIL', 'Wysłano e-mail do czytelnika (Zrealizowanie zamówienia) 540200000002', '2024-06-05 22:40:35.029347'),
    (1, 'NOTIFICATION_SMS', 'Wysłano wiadomość SMS do czytelnika (Zrealizowanie zamówienia) 540200000002', '2024-06-05 22:40:35.030348'),
    (1, 'BOOK_BORROWED', 'Wypożyczono Araya', '2024-06-05 22:40:35.172347'),
    (1, 'NOTIFICATION_EMAIL', 'Wysłano e-mail do czytelnika (Wypożyczenie dokumentu) 540200000002', '2024-06-05 22:40:35.173348'),
    (1, 'NOTIFICATION_SMS', 'Wysłano wiadomość SMS do czytelnika (Wypożyczenie dokumentu) 540200000002', '2024-06-05 22:40:35.174348'),
    (1, 'BOOK_RENEWED', 'Prolongata czytelnika Araya', '2024-06-05 22:40:35.360349'),
    (1, 'NOTIFICATION_EMAIL', 'Wysłano e-mail do czytelnika (Prolongata dokumentu) 540200000002', '2024-06-05 22:40:35.361347'),
    (1, 'NOTIFICATION_SMS', 'Wysłano wiadomość SMS do czytelnika (Prolongata dokumentu) 540200000002', '2024-06-05 22:40:35.362348'),
    (1, 'BOOK_RETURNED', 'Zwrócono Araya', '2024-06-05 22:40:35.494348'),
    (1, 'NOTIFICATION_EMAIL', 'Wysłano e-mail do czytelnika (Zwrócenie dokumentu) 540200000002', '2024-06-05 22:40:35.495347'),
    (1, 'NOTIFICATION_SMS', 'Wysłano wiadomość SMS do czytelnika (Zwrócenie dokumentu) 540200000002', '2024-06-05 22:40:35.496349'),
    (1, 'LOGOUT', 'Wylogowanie czytelnika Kasia Lubiczytać, Nr karty 540100000001', '2024-06-05 22:40:35.555349'),

    (2, 'LOGIN', 'Zalogowanie czytelnika Kamil Nielubi, Nr karty 540100000002', '2024-06-05 22:40:34.730348'),
    (2, 'REQUEST_NEW', 'Wysłano zamówienie Araya', '2024-06-05 22:40:34.845347'),
    (2, 'NOTIFICATION_EMAIL', 'Wysłano e-mail do czytelnika (Utworzenie zamówienia) 540200000002', '2024-06-05 22:40:34.846347'),
    (2, 'NOTIFICATION_SMS', 'Wysłano wiadomość SMS do czytelnika (Utworzenie zamówienia) 540200000002', '2024-06-05 22:40:34.847346'),
    (2, 'REQUEST_COMPLETED', 'Zrealizowano Araya', '2024-06-05 22:40:35.028348'),
    (2, 'NOTIFICATION_EMAIL', 'Wysłano e-mail do czytelnika (Zrealizowanie zamówienia) 540200000002', '2024-06-05 22:40:35.029347'),
    (2, 'NOTIFICATION_SMS', 'Wysłano wiadomość SMS do czytelnika (Zrealizowanie zamówienia) 540200000002', '2024-06-05 22:40:35.030348'),
    (2, 'BOOK_BORROWED', 'Wypożyczono Araya', '2024-06-05 22:40:35.172347'),
    (2, 'NOTIFICATION_EMAIL', 'Wysłano e-mail do czytelnika (Wypożyczenie dokumentu) 540200000002', '2024-06-05 22:40:35.173348'),
    (2, 'NOTIFICATION_SMS', 'Wysłano wiadomość SMS do czytelnika (Wypożyczenie dokumentu) 540200000002', '2024-06-05 22:40:35.174348'),
    (2, 'BOOK_RENEWED', 'Prolongata czytelnika Araya', '2024-06-05 22:40:35.360349'),
    (2, 'NOTIFICATION_EMAIL', 'Wysłano e-mail do czytelnika (Prolongata dokumentu) 540200000002', '2024-06-05 22:40:35.361347'),
    (2, 'NOTIFICATION_SMS', 'Wysłano wiadomość SMS do czytelnika (Prolongata dokumentu) 540200000002', '2024-06-05 22:40:35.362348'),
    (2, 'BOOK_RETURNED', 'Zwrócono Araya', '2024-06-05 22:40:35.494348'),
    (2, 'NOTIFICATION_EMAIL', 'Wysłano e-mail do czytelnika (Zwrócenie dokumentu) 540200000002', '2024-06-05 22:40:35.495347'),
    (2, 'NOTIFICATION_SMS', 'Wysłano wiadomość SMS do czytelnika (Zwrócenie dokumentu) 540200000002', '2024-06-05 22:40:35.496349'),
    (2, 'LOGOUT', 'Wylogowanie czytelnika Kamil Nielubi, Nr karty 540100000002', '2024-06-05 22:40:35.555349');


