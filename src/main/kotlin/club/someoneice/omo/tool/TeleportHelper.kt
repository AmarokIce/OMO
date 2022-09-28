package club.someoneice.omo.tool

import net.minecraft.entity.Entity
import net.minecraft.world.WorldServer
import net.minecraft.world.Teleporter

// From ManaMetalMod. Now Player not need to install with ManaMetalMod.
class TeleportHelper(world: WorldServer?) : Teleporter(world) {

    override fun placeInPortal(player: Entity?, x: Double, y: Double, z: Double, Rot: Float) {}
    override fun removeStalePortalLocations(l: Long) {}
}