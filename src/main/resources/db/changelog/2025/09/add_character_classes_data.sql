-- liquibase formatted sql

-- changeset wladw:1758048552-47
-- comment: Insert all character classes
INSERT INTO character_classes (name,
                               class_type,
                               base_hp,
                               base_strength,
                               base_intellect,
                               base_agility,
                               base_critical_chance,
                               main_stat,
                               created_at)
VALUES ('WARRIOR', 'MELEE_PHYSICAL', 150.0, 15.0, 5.0, 8.0, 3.0, 'STRENGTH', CURRENT_TIMESTAMP),
       ('MAGE', 'RANGED_MAGIC', 100.0, 5.0, 15.0, 7.0, 5.0, 'INTELLECT', CURRENT_TIMESTAMP),
       ('ARCHER', 'RANGED_PHYSICAL', 120.0, 8.0, 6.0, 15.0, 10.0, 'AGILITY', CURRENT_TIMESTAMP);
