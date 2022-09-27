package club.someoneice.omo.event

import club.someoneice.omo.common.Player
import club.someoneice.omo.common.PlayerPath
import net.minecraft.entity.player.PlayerEntity
import net.minecraftforge.fml.common.Mod.EventBusSubscriber
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.event.entity.living.LivingDeathEvent
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.world.server.ServerWorld
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent
import org.apache.logging.log4j.core.jmx.Server

@EventBusSubscriber
object PlayerEvent {
    @SubscribeEvent
    fun onPlayerLoginIn(event: PlayerLoggedInEvent) {
        Player.playerList[event.player.scoreboardName] = event.player as ServerPlayerEntity
    }

    @SubscribeEvent
    fun onPlayerDeath(event: LivingDeathEvent) {
        val player = event.entity.scoreboardName

        if (Player.playerList.containsKey(player)) {
            val isPlayer = Player.playerList[player] as ServerPlayerEntity
            val playerPathList = PlayerPath(isPlayer.level as ServerWorld, isPlayer.x, isPlayer.y, isPlayer.z, isPlayer.xRot, isPlayer.yRot)
            Player.playerDeath[isPlayer] = playerPathList
        }
    }
}