--liquibase formatted sql
--changeset lukas:0001
insert into book (title, subject, publisher, isbn, language, pages, format, publication_date)
values
    ('Araya', 'White Plains', 'Xena Hallut', '460302346-4', 'Hungarian', 195, 'MAGAZINE', '2023-01-29'),
    ('Euphoria (Eyforiya)', 'Seattle', 'Essie Bizzey', '556006580-3', 'German', 319, 'JOURNAL', '2022-01-29'),
    ('Judex', 'Suai', 'Rosemary Cayzer', '276843041-2', 'Norwegian', 50, 'NEWSPAPER', '2021-01-29'),
    ('Come Blow Your Horn', 'Twin Hills', 'Ulysses Olphert', '731579514-6', 'Zulu', 646, 'EBOOK', '1990-01-29'),
    ('Downtown', 'error: Could n', 'Marcus Curtois', '640694109-8', 'Kurdish', 80, 'AUDIO_BOOK', '2023-01-29'),
    ('Code, The (Mentale, La)', 'Kornasoren-Numfoor Island', 'Franz Courcey', '755402775-1', 'Icelandic', 103, 'PAPERBACK', '2022-01-29'),
    ('Planes, Trains & Automobiles', 'Salerno', 'Sollie Bendson', '888080366-2', 'Catalan', 578, 'HARDCOVER', '2015-01-29'),
    ('Why Man Creates', 'Phaplu', 'Amalie Naulty', '220997607-3', 'Aymara', 520, 'MAGAZINE', '2016-01-29'),
    ('Castle on the Hudson', 'Belmonte', 'Buddy Stapylton', '303510209-0', 'Thai', 276, 'JOURNAL', '2017-01-29'),
    ('Confidential Report', 'Wantoat', 'Neilla Joul', '097126972-6', 'Arabic', 303, 'NEWSPAPER', '2018-01-29'),
    ('Korczak', 'Bunsil - Umboi Island', 'Cosette Bayley', '374093219-8', 'Italian', 681, 'EBOOK', '2019-01-29'),
    ('Murderous Maids (Blessures assassines, Les)', 'Lexington', 'Etheline Puddifer', '078057294-7', 'Gujarati', 191, 'AUDIO_BOOK', '2023-01-29'),
    ('Flu', 'Cornwall', 'Pennie Yurkiewicz', '352306803-7', 'Icelandic', 233, 'PAPERBACK', '2012-01-29'),
    ('Gods and Monsters', 'Stykkishólmur', 'Mada Lates', '675181525-5', 'Czech', 502, 'HARDCOVER', '2011-01-29'),
    ('Toolbox Murders, The', 'error: Could not access blank bla', 'Arleen Rottery', '527711525-0', 'Japanese', 172, 'JOURNAL', '2011-01-29'),
    ('Don''t Deliver Us from Evil (Mais ne nous délivrez pas du mal)', 'Sinop', 'Mariya McCracken', '194481046-3', 'Hindi', 151, 'MAGAZINE', '2022-01-29');
