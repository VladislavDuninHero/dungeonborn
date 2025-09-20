-- liquibase formatted sql

-- changeset wladw:1758048570-68
INSERT INTO dungeon (
    id,
    name,
    created_at
)
VALUES (
    1,
    'Training camp',
CURRENT_TIMESTAMP
)