package club.someoneice.omo.event

import club.someoneice.omo.tool.PlayerData
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraftforge.event.entity.player.PlayerDropsEvent
import net.minecraftforge.fml.common.eventhandler.Event
import net.minecraftforge.fml.common.eventhandler.EventPriority
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.PlayerEvent

open class PlayerListener: Event() {
    companion object {
        val PlayerTPList = HashMap<String, String>()
        val PlayerGoing = HashMap<String, Boolean>()

        val PlayerList = HashMap<String, EntityPlayerMP>()
        val PlayerDataList = HashMap<String, PlayerData>()
        var playerName:String = ""
    }

    @SubscribeEvent(priority= EventPriority.LOWEST)
    fun onPlayerLogin(event: PlayerEvent.PlayerLoggedInEvent) {
        val player: EntityPlayer = event.player as EntityPlayer
        playerName = player.displayNameString
        PlayerList[playerName] = player as EntityPlayerMP
    }

    @SubscribeEvent(priority= EventPriority.LOWEST)
    fun onPlayerDie(event: PlayerDropsEvent) {
        val player: String = event.entityPlayer.displayNameString
        if (PlayerList.containsKey(player)) {
            val playerData = PlayerData(event.entityPlayer?.posX as Double, event.entityPlayer.posY, event.entityPlayer.posZ, event.entityPlayer.rotationYaw, event.entityPlayer.rotationPitch)
            PlayerDataList[player] = playerData
        }
    }
}