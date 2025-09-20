-- liquibase formatted sql

-- changeset wladw:1758048549-44
-- comment: Create admin user
INSERT INTO users (login, password, email, is_enabled, created_at, updated_at)
VALUES (
           'admin',
           '$2a$12$c2mb2lNufGKiL07aJeEPXO.LaKwcDBLnASkCUkBYdTtQndNTMGHKm',
           'admin@dungeonborn.com',
           TRUE,
           CURRENT_TIMESTAMP,
           CURRENT_TIMESTAMP
       );

-- changeset wladw:1758048550-45
-- comment: add permissions to admin user
INSERT INTO role (user_id, role_name, created_at)
VALUES (
           (SELECT id FROM users WHERE login = 'admin'),
           'ADMIN',
            CURRENT_TIMESTAMP
       );

-- changeset wladw:1758048551-46
-- comment: add permissions to admin user
INSERT INTO permissions (permission_type, role_id, created_at)
VALUES
    ('READ_USER', (SELECT id FROM role WHERE user_id = '1'), CURRENT_TIMESTAMP),
    ('UPDATE_USER', (SELECT id FROM role WHERE user_id = '1'), CURRENT_TIMESTAMP),
    ('DELETE_USER', (SELECT id FROM role WHERE user_id = '1'), CURRENT_TIMESTAMP),
    ('CREATE_CHAR', (SELECT id FROM role WHERE user_id = '1'), CURRENT_TIMESTAMP),
    ('READ_CHAR', (SELECT id FROM role WHERE user_id = '1'), CURRENT_TIMESTAMP),
    ('UPDATE_CHAR', (SELECT id FROM role WHERE user_id = '1'), CURRENT_TIMESTAMP),
    ('DELETE_CHAR', (SELECT id FROM role WHERE user_id = '1'), CURRENT_TIMESTAMP),
    ('CREATE_DUNGEON', (SELECT id FROM role WHERE user_id = '1'), CURRENT_TIMESTAMP),
    ('READ_DUNGEON', (SELECT id FROM role WHERE user_id = '1'), CURRENT_TIMESTAMP),
    ('UPDATE_DUNGEON', (SELECT id FROM role WHERE user_id = '1'), CURRENT_TIMESTAMP),
    ('DELETE_DUNGEON', (SELECT id FROM role WHERE user_id = '1'), CURRENT_TIMESTAMP)
