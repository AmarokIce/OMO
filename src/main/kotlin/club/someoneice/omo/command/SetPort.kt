package club.someoneice.omo.command

import club.someoneice.omo.event.PlayerListener
import club.someoneice.omo.tool.PlayerData
import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.util.ChatComponentText
import net.minecraft.util.IChatComponent
import net.minecraft.world.WorldServer

class SetPort: CommandBase() {
    override fun getCommandName(): String {
        return "setport"
    }

    override fun getCommandUsage(sender: ICommandSender): String {
        return "/setport"
    }

    override fun processCommand(sender: ICommandSender, msg: Array<String>) {
        val player: EntityPlayerMP = getPlayer(sender, sender.commandSenderName.toString()) as EntityPlayerMP
        PlayerListener.PlayerPortList.put(sender.commandSenderName, PlayerData(player.worldObj as WorldServer, player.posX, player.posY, player.posZ, player.rotationYaw, player.rotationPitch))
        sender.addChatMessage(ChatComponentText("已经标记地点！") as IChatComponent)

    }

    override fun getRequiredPermissionLevel(): Int {
        return 1
    }
}