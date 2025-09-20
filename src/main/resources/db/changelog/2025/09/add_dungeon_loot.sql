-- liquibase formatted sql

-- changeset wladw:1758048574-72
INSERT INTO dungeon_loot (
    id,
    exp_reward,
    loot_table_id,
    created_at
)
VALUES
    (1, 0.1, 1, CURRENT_TIMESTAMP)