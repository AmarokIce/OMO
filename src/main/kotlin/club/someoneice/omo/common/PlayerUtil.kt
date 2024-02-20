package club.someoneice.omo.common

import net.minecraft.server.level.ServerPlayer

object PlayerUtil {
    val playerTeleportList = HashMap<ServerPlayer, ServerPlayer>()
    val playerGoToList = HashMap<ServerPlayer, Boolean>()

    val playerDeath = HashMap <ServerPlayer, PlayerPath>()

    fun getKey(map: java.util.HashMap<ServerPlayer, ServerPlayer>, value: ServerPlayer): ServerPlayer? {
        var key: ServerPlayer? = null
        for (getKey in map.keys) {
            if (map[getKey] == value) {
                key = getKey
            }
        }

        return key
    }
}
