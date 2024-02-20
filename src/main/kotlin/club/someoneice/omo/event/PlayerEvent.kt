package club.someoneice.omo.event

import club.someoneice.omo.common.PlayerUtil
import club.someoneice.omo.common.PlayerPath
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraftforge.event.entity.living.LivingDeathEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod.EventBusSubscriber

@EventBusSubscriber
object PlayerEvent {
    @SubscribeEvent
    fun onPlayerDeath(event: LivingDeathEvent) {
        if (event.entity is net.minecraft.world.entity.player.Player) {
            val isPlayer = event.entity as ServerPlayer
            val playerPathList = PlayerPath(isPlayer.level() as ServerLevel, isPlayer.x, isPlayer.y, isPlayer.z, isPlayer.xRot, isPlayer.yRot)
            PlayerUtil.playerDeath[isPlayer] = playerPathList
        }
    }
}