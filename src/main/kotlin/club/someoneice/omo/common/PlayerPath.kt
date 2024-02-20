package club.someoneice.omo.common

import net.minecraft.server.level.ServerLevel

data class PlayerPath(
    val level: ServerLevel,
    val x: Double,
    val y: Double,
    val z: Double,
    val rotX: Float,
    val rotY: Float,
)
