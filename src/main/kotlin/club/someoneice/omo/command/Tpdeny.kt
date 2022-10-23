package club.someoneice.omo.command

import club.someoneice.omo.event.PlayerListener
import club.someoneice.omo.tool.HashMapKeyUtil
import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender
import net.minecraft.util.ChatComponentText
import net.minecraft.util.IChatComponent


class Tpdeny: CommandBase() {
    override fun getCommandName(): String {
        return "tpdeny"
    }

    override fun getCommandUsage(sender: ICommandSender): String {
        return "/tpdeny"
    }

    override fun processCommand(sender: ICommandSender, msg: Array<String>) {
        val playerName:String? = HashMapKeyUtil.getKey(PlayerListener.PlayerTPList, sender.commandSenderName)

        if (playerName != null) {
            PlayerListener.PlayerTPList.remove(playerName.toString())

            sender.addChatMessage(ChatComponentText("你拒绝了 ${playerName}！") as IChatComponent)
            getPlayer(sender, playerName.toString()).addChatMessage(ChatComponentText("${sender.commandSenderName} 拒絕了你的傳送請求！") as IChatComponent)
        } else sender.addChatMessage(ChatComponentText("你没有收到请求！") as IChatComponent)

    }

    override fun getRequiredPermissionLevel(): Int {
        return 0
    }

    override fun canCommandSenderUseCommand(sender: ICommandSender?): Boolean {
        return true
    }
}