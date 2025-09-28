-- liquibase formatted sql

-- changeset wladw:1758048554-49
-- comment: Insert all games items

INSERT INTO items (
       id,
       name,
       type,
       slot_type,
       item_level,
       quality,
       min_dungeon_level,
       stamina,
       strength,
       intellect,
       agility,
       critical_chance,
       critical_power,
       armor,
       is_available,
       drop_chance,
       created_at
)
VALUES (1, 'Common sword', 'WEAPON', 'WEAPON', 1.0, 'COMMON', 1, 6.0, 10.0, 0.0,
        3.0, 0.2, 0.4, 0.0, true, 80.0, CURRENT_TIMESTAMP),
       (2, 'Common staff', 'WEAPON', 'WEAPON', 1.0, 'COMMON', 1, 2.0, 0.0, 10.0,
        3.0, 0.2, 0.4, 0.0,true, 80.0, CURRENT_TIMESTAMP),
       (3, 'Common bow', 'WEAPON', 'WEAPON', 1.0, 'COMMON', 1, 3.0, 0.0, 0.0,
        12.0, 0.3, 0.6, 0.0,true, 80.0, CURRENT_TIMESTAMP),
       (4, 'Rare sword', 'WEAPON', 'WEAPON', 5.0, 'RARE', 3, 12.0, 20.0, 0.0,
        3.0, 0.4, 0.6, 0.0, true, 70.0, CURRENT_TIMESTAMP),
       (5, 'Rare staff', 'WEAPON', 'WEAPON', 5.0, 'RARE', 3, 4.0, 0.0, 20.0,
        3.0, 0.4, 0.6, 0.0,true, 70.0, CURRENT_TIMESTAMP),
       (6, 'Rare bow', 'WEAPON', 'WEAPON', 5.0, 'RARE', 3, 6.0, 0.0, 0.0,
        24.0, 0.6, 1.2, 0.0, true, 70.0, CURRENT_TIMESTAMP)
