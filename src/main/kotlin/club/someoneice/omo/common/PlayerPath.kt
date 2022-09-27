package club.someoneice.omo.common

import net.minecraft.world.server.ServerWorld

data class PlayerPath(
    val level: ServerWorld,
    val x: Double,
    val y: Double,
    val z: Double,
    val RotX: Float,
    val RotY: Float,
)
