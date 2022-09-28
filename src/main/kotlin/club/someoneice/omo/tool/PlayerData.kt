package club.someoneice.omo.tool

import net.minecraft.world.WorldServer

data class PlayerData(
    val world: WorldServer,
    val x: Double,
    val y: Double,
    val z: Double,
    val RotX: Float,
    val RotY: Float
)
