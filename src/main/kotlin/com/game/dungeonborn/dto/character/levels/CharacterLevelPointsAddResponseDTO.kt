package com.game.dungeonborn.dto.character.levels

data class CharacterLevelPointsAddResponseDTO(
    var level: Int,
    var points: Double,
    var levelUp: Boolean,
    var nextLevelPoints: Double,
) {
}