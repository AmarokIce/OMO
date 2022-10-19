package club.someoneice.omo.event

import club.someoneice.omo.common.Player
import club.someoneice.omo.common.PlayerPath
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraftforge.event.entity.living.LivingDeathEvent
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod.EventBusSubscriber

@EventBusSubscriber
object PlayerEvent {
    @SubscribeEvent
    fun onPlayerLoginIn(event: PlayerLoggedInEvent) {
        Player.playerList[event.entity.scoreboardName] = event.entity as ServerPlayer
    }

    @SubscribeEvent
    fun onPlayerDeath(event: LivingDeathEvent) {
        val player = event.entity.scoreboardName

        if (Player.playerList.containsKey(player)) {
            val isPlayer = Player.playerList[player] as ServerPlayer
            val playerPathList = PlayerPath(isPlayer.level as ServerLevel, isPlayer.x, isPlayer.y, isPlayer.z, isPlayer.xRot, isPlayer.yRot)
            Player.playerDeath[isPlayer] = playerPathList
        }
    }
}