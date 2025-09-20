-- liquibase formatted sql

-- changeset wladw:1758048571-69
INSERT INTO dungeon_level (
    id,
    lvl,
    recommended_power,
    total_enemies_npc,
    rating_points,
    dungeon_rounds,
    dungeon_id,
    dungeon_loot_id,
    created_at
)
VALUES
    (1,1,0,1,0.1,1,1,1, CURRENT_TIMESTAMP),
    (2,2,1,2,0.1,2,1,1, CURRENT_TIMESTAMP),
    (3,3,1.5,3,0.1,3,1,1, CURRENT_TIMESTAMP),
    (4,4,2,4,0.1,4,1,1, CURRENT_TIMESTAMP),
    (5,5,2.5,5,0.1,5,1,1, CURRENT_TIMESTAMP),
    (6,6,3,5,0.1,6,1,1, CURRENT_TIMESTAMP),
    (7,7,3.5,5,0.1,7,1,1, CURRENT_TIMESTAMP),
    (8,8,4,5,0.1,8,1,1, CURRENT_TIMESTAMP),
    (9,9,4.5,5,0.1,9,1,1, CURRENT_TIMESTAMP),
    (10,10,5,5,1,10,1,1, CURRENT_TIMESTAMP)