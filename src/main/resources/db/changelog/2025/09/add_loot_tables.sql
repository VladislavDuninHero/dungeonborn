-- liquibase formatted sql

-- changeset wladw:1758048572-70
INSERT INTO loot_table (
    id,
    name,
    description,
    created_at
)
VALUES (
           1,
           'Training camp loot',
            'Loot for Training camp dungeon',
           CURRENT_TIMESTAMP
       )