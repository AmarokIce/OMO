package club.someoneice.omo.command

import club.someoneice.omo.event.PlayerListener
import club.someoneice.omo.tool.HashMapKeyHelper
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
        val playerName:String? = HashMapKeyHelper.getKey(PlayerListener.PlayerTPList, sender.commandSenderName)

        if (playerName != null) {
            PlayerListener.PlayerTPList.remove(playerName.toString())

            sender.addChatMessage(ChatComponentText("你拒絕了 $playerName 的請求！") as IChatComponent)
            getPlayer(sender, playerName.toString()).addChatMessage(ChatComponentText("${sender.commandSenderName} 拒絕了你的傳送請求！") as IChatComponent)
        } else sender.addChatMessage(ChatComponentText("你沒有任何請求！") as IChatComponent)

    }

    override fun getRequiredPermissionLevel(): Int {
        return 1
    }

}