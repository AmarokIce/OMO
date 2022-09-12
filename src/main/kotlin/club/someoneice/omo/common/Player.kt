package club.someoneice.omo.common

import net.minecraft.entity.player.ServerPlayerEntity

object Player{
    val playerTeleportList = HashMap<ServerPlayerEntity, ServerPlayerEntity>()
    val playerGoToList = HashMap<ServerPlayerEntity, Boolean>()

    fun getKey(map: java.util.HashMap<ServerPlayerEntity, ServerPlayerEntity>, value: ServerPlayerEntity): ServerPlayerEntity? {
        var key: ServerPlayerEntity? = null
        for (getKey in map.keys) {
            if (map[getKey] == value) {
                key = getKey
            }
        }

        return key
    }
}
