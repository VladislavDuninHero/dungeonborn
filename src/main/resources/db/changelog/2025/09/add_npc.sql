-- liquibase formatted sql

-- changeset wladw:1758048572-70
INSERT INTO npc (
    id,
    name,
    lvl,
    is_friendly,
    is_boss,
    spawn_chance,
    max_hp,
    max_mana,
    damage_min,
    damage_max,
    critical_chance,
    critical_power,
    armor,
    created_at
)
VALUES
    (1, 'Common training partner', 1, false, false, 100, 100, 50, 1, 5, 0.1, 1.0, 1, CURRENT_TIMESTAMP),
    (2, 'Strong training partner', 1, false, false, 100, 150, 70, 2, 6, 0.2, 1.1, 1, CURRENT_TIMESTAMP),
    (3, 'Advanced training partner', 1, false, false, 100, 200, 100, 3, 8, 0.2, 1.2, 1, CURRENT_TIMESTAMP),
    (4, 'Mentor', 1, false, false, 100, 250, 120, 4, 10, 0.3, 1.3, 1, CURRENT_TIMESTAMP),
    (5, 'Chief mentor', 1, false, false, 100, 400, 200, 10, 20, 0.5, 1.4, 2, CURRENT_TIMESTAMP)