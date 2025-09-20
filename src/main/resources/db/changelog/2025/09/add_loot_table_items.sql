-- liquibase formatted sql

-- changeset wladw:1758048573-71
INSERT INTO loot_table_items (
    id,
    loot_table_id,
    item_id,
    created_at
)
VALUES
    (1,1, 1,CURRENT_TIMESTAMP),
    (2,1, 2,CURRENT_TIMESTAMP),
    (3,1, 3,CURRENT_TIMESTAMP)