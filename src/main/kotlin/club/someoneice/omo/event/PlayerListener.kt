package club.someoneice.omo.event

import club.someoneice.omo.tool.PlayerData
import cpw.mods.fml.common.eventhandler.Event
import cpw.mods.fml.common.eventhandler.EventPriority
import cpw.mods.fml.common.eventhandler.SubscribeEvent
import cpw.mods.fml.common.gameevent.PlayerEvent
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.ChatComponentText
import net.minecraft.world.WorldServer
import net.minecraftforge.event.entity.player.PlayerDropsEvent
import java.util.*

open class PlayerListener: Event() {
    companion object {
        val PlayerTPList: HashMap<String, String> = HashMap<String, String>()
        val PlayerGoing: HashMap<String, Boolean> = HashMap<String, Boolean>()

        val PlayerList: ArrayList<String> = ArrayList<String>()
        val PlayerDataList: HashMap<String, PlayerData> = HashMap<String, PlayerData>()
        val PlayerPortList: HashMap<String, PlayerData> = HashMap<String, PlayerData>()
        var playerName:String = ""
    }

    @SubscribeEvent(priority= EventPriority.LOWEST)
    fun onPlayerLogin(event: PlayerEvent.PlayerLoggedInEvent) {
        val player: EntityPlayer = event.player as EntityPlayer
        playerName = player.displayName
        PlayerList.add(playerName)
        event.player.addChatMessage(ChatComponentText(player.uniqueID.toString()))
    }

    @SubscribeEvent(priority= EventPriority.LOWEST)
    fun onPlayerDie(event: PlayerDropsEvent) {
        event.entityPlayer.addChatMessage(ChatComponentText(event.entityPlayer.uniqueID.toString()))
        val player: String = event.entityPlayer.displayName
        if (PlayerList.contains(player)) {
            val playerData = PlayerData(event.entityPlayer.worldObj as WorldServer, event.entityPlayer?.posX as Double, event.entityPlayer.posY, event.entityPlayer.posZ, event.entityPlayer.rotationYaw, event.entityPlayer.rotationPitch)
            PlayerDataList.put(player , playerData)
        }
    }
}