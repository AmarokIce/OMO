package club.someoneice.omo.command

import club.someoneice.omo.event.PlayerListener
import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.util.ChatComponentText
import net.minecraft.util.IChatComponent

class Back: CommandBase() {
    override fun getCommandName(): String {
        return "back"
    }

    override fun getCommandUsage(sender: ICommandSender): String {
        return "/back"
    }

    override fun processCommand(sender: ICommandSender, msg: Array<String>) {
        val player: EntityPlayerMP = getPlayer(sender, sender.commandSenderName.toString()) as EntityPlayerMP
        if (PlayerListener.PlayerDataList.containsKey(sender.commandSenderName)) {
            player.playerNetServerHandler.setPlayerLocation(PlayerListener.PlayerDataList[sender.commandSenderName]?.x as Double , PlayerListener.PlayerDataList[sender.commandSenderName]?.y as Double, PlayerListener.PlayerDataList[sender.commandSenderName]?.z as Double, PlayerListener.PlayerDataList[sender.commandSenderName]?.RotX as Float, PlayerListener.PlayerDataList[sender.commandSenderName]?.RotY as Float)
            sender.addChatMessage(ChatComponentText("已經傳送到死亡地點！") as IChatComponent)
        } else {
            sender.addChatMessage(ChatComponentText("沒有死亡記錄！") as IChatComponent)
        }

    }

    override fun getRequiredPermissionLevel(): Int {
        return 1
    }
}