package club.someoneice.omo.command

import club.someoneice.omo.event.PlayerListener
import club.someoneice.omo.tool.HashMapKeyHelper
import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.util.ChatComponentText
import net.minecraft.util.IChatComponent

class Tpa: CommandBase() {
    override fun getCommandName(): String {
        return "tpa"
    }

    override fun getCommandUsage(p_71518_1_: ICommandSender): String {
        return "/tpa - [player] [player2]"
    }

    override fun processCommand(sender: ICommandSender, msg: Array<String>) {
        if (msg.size == 1 && sender.commandSenderName != msg[0]) {
            val player: EntityPlayerMP = getPlayer(sender, msg[0])

            if (HashMapKeyHelper.getKey(PlayerListener.PlayerTPList, player.displayName) != null) {
                PlayerListener.PlayerTPList.remove(HashMapKeyHelper.getKey(PlayerListener.PlayerTPList, player.displayName).toString())
            }

            PlayerListener.PlayerTPList.put(sender.commandSenderName, player.displayName)

            sender.addChatMessage(ChatComponentText("已經向 ${player.displayName} 發送一個傳送請求！") as IChatComponent)
            player.addChatMessage(ChatComponentText(" ${sender.commandSenderName} 希望傳送到你這邊來！") as IChatComponent)

            PlayerListener.PlayerTPList.put(sender.commandSenderName, player.displayName)
            PlayerListener.PlayerGoing.put(sender.commandSenderName, false)

        } else if (msg.size == 2) {
            when (sender.commandSenderName) {
                msg[0] -> {
                    val player: EntityPlayerMP = getPlayer(sender, msg[1])

                    if (HashMapKeyHelper.getKey(PlayerListener.PlayerTPList, player.displayName) != null) {
                        PlayerListener.PlayerTPList.remove(HashMapKeyHelper.getKey(PlayerListener.PlayerTPList, player.displayName).toString())
                    }

                    PlayerListener.PlayerTPList.put(sender.commandSenderName, player.displayName)
                    PlayerListener.PlayerGoing.put(sender.commandSenderName, false)


                    sender.addChatMessage(ChatComponentText("已經向 ${player.displayName} 發送一個傳送請求！") as IChatComponent)
                    player.addChatMessage(ChatComponentText(" ${sender.commandSenderName} 希望傳送到你這邊來！") as IChatComponent)
                }

                msg[1] -> {
                    val player: EntityPlayerMP = getPlayer(sender, msg[0])

                    if (HashMapKeyHelper.getKey(PlayerListener.PlayerTPList, player.displayName) != null) {
                        PlayerListener.PlayerTPList.remove(HashMapKeyHelper.getKey(PlayerListener.PlayerTPList, player.displayName).toString())
                    }

                    PlayerListener.PlayerTPList.put(sender.commandSenderName, player.displayName)

                    sender.addChatMessage(ChatComponentText("已經向 ${player.displayName} 發送一個傳送請求！") as IChatComponent)
                    player.addChatMessage(ChatComponentText(" ${sender.commandSenderName} 希望你被傳送到他那邊去！") as IChatComponent)

                }

                else -> sender.addChatMessage(ChatComponentText("未知的參數或錯誤。") as IChatComponent)
            }

        } else sender.addChatMessage(ChatComponentText("未知的參數或錯誤。") as IChatComponent)
    }

    override fun getRequiredPermissionLevel(): Int {
        return 1
    }
}