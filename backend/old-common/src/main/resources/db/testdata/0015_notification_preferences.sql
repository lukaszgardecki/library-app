--liquibase formatted sql
--changeset lukas:0015
insert into
    notification_preferences(user_id, email_enabled, sms_enabled)
values
    (1, true, false),
    (2, true, true),
    (3, false, true),
    (4, false, false),
    (5, true, true),
    (6, false, false),
    (7, true, true),
    (8, false, false);