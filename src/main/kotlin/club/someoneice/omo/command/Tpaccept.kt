package club.someoneice.omo.command

import club.someoneice.omo.event.PlayerListener
import club.someoneice.omo.tool.HashMapKeyHelper
import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.util.ChatComponentText
import net.minecraft.util.IChatComponent

class Tpaccept : CommandBase() {
    override fun getCommandName(): String {
        return "tpaccept"
    }

    override fun getCommandUsage(p_71518_1_: ICommandSender): String {
        return "/tpaccept"
    }

    override fun processCommand(sender: ICommandSender, msg: Array<String>) {
        val playerName:String? = HashMapKeyHelper.getKey(PlayerListener.PlayerTPList, sender.commandSenderName)

        if (playerName != null) {
            val player: EntityPlayerMP = sender as EntityPlayerMP
            val playerAsk: EntityPlayerMP = getPlayer(sender, playerName)
            if (player.worldObj == playerAsk.worldObj) {
                if (!PlayerListener.PlayerGoing[playerName]!!) playerAsk.playerNetServerHandler.setPlayerLocation(player.posX, player.posY, player.posZ, player.rotationYaw, player.rotationPitch)
                else player.playerNetServerHandler.setPlayerLocation(playerAsk.posX, playerAsk.posY, playerAsk.posZ, playerAsk.rotationYaw, playerAsk.rotationPitch)
            } else {
                player.addChatMessage(ChatComponentText("你们不在同一维度！") as IChatComponent)
                playerAsk.addChatMessage(ChatComponentText("你们不在同一维度！") as IChatComponent)
            }



            PlayerListener.PlayerTPList.remove(playerName.toString())
        } else sender.addChatMessage(ChatComponentText("你沒有任何請求！") as IChatComponent)
    }

    override fun getRequiredPermissionLevel(): Int {
        return 1
    }


}