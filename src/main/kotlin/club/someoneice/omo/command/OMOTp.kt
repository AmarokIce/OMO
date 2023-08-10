package club.someoneice.omo.command

import club.someoneice.omo.PineappleConfig
import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.init.Blocks
import net.minecraft.util.ChatComponentText
import net.minecraft.world.World
import java.util.*

class OMOTp: CommandBase() {
    override fun getCommandName(): String {
        return "omotp"
    }

    override fun getCommandUsage(sender: ICommandSender): String {
        return "/omotp [player] or /omotp [x] [y] [z]"
    }

    override fun processCommand(sender: ICommandSender, msg: Array<String>) {
        val senderPlayer = sender as EntityPlayerMP
        var x: Double = (senderPlayer).posX
        var y: Double = (senderPlayer).posY
        var z: Double = (senderPlayer).posZ
        val player: EntityPlayerMP = senderPlayer
        if (msg.size == 1) {
            val askPlayer = getPlayer(sender, msg[0])
            x = askPlayer.posX
            y = askPlayer.posY
            z = askPlayer.posZ
        } else {
            try {
                x = Integer.parseInt(msg[0]).toDouble()
                y = Integer.parseInt(msg[1]).toDouble()
                z = Integer.parseInt(msg[2]).toDouble()
            } catch (_: Exception) {
                sender.addChatMessage(ChatComponentText("/omotp [player] or /omotp [x] [y] [z]"))
            }
        }

        player.playerNetServerHandler.setPlayerLocation(x, y, z, player.rotationYaw, player.rotationPitch)
    }

    override fun getRequiredPermissionLevel(): Int {
        return 0
    }

    override fun canCommandSenderUseCommand(sender: ICommandSender): Boolean {
        return true
    }
}