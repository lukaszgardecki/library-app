--liquibase formatted sql
--changeset lukas:0002
insert into
    access_token(token, token_type, expired, revoked, user_id)
values
    ('eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBleGFtcGxlLmNvbSIsImlhdCI6MTY5NjE0NTY2NiwiZXhwIjoxNjk2MTQ3MTA2fQ.QE7I-trmWolOaPAA13i1pOLQYvUXFfiBpa4VcPKMBz4', 'ACCESS', false, false, 1),
    ('eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyQGV4YW1wbGUuY29tIiwiaWF0IjoxNjk2MTU1MDEzLCJleHAiOjE2OTYxNTY0NTN9.E_dCj3h58dJ_BESRonZC13zvB76b8lGjaXUtHKChiMM', 'ACCESS', false, false, 2);
