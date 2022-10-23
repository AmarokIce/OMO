package club.someoneice.omo.command

import club.someoneice.omo.event.PlayerListener
import club.someoneice.omo.tool.HashMapKeyUtil
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

            if (HashMapKeyUtil.getKey(PlayerListener.PlayerTPList, player.displayName) != null) {
                PlayerListener.PlayerTPList.remove(HashMapKeyUtil.getKey(PlayerListener.PlayerTPList, player.displayName).toString())
            }

            PlayerListener.PlayerTPList.put(sender.commandSenderName, player.displayName)

            sender.addChatMessage(ChatComponentText("已经向 ${player.displayName} 发送传送请求！") as IChatComponent)
            player.addChatMessage(ChatComponentText(" ${sender.commandSenderName} 希望传送到你这边！") as IChatComponent)

            PlayerListener.PlayerTPList.put(sender.commandSenderName, player.displayName)
            PlayerListener.PlayerGoing.put(sender.commandSenderName, false)

        } else if (msg.size == 2) {
            when (sender.commandSenderName) {
                msg[0] -> {
                    val player: EntityPlayerMP = getPlayer(sender, msg[1])

                    if (HashMapKeyUtil.getKey(PlayerListener.PlayerTPList, player.displayName) != null) {
                        PlayerListener.PlayerTPList.remove(HashMapKeyUtil.getKey(PlayerListener.PlayerTPList, player.displayName).toString())
                    }

                    PlayerListener.PlayerTPList.put(sender.commandSenderName, player.displayName)
                    PlayerListener.PlayerGoing.put(sender.commandSenderName, false)


                    sender.addChatMessage(ChatComponentText("已经向 ${player.displayName} 发送传送请求！") as IChatComponent)
                    player.addChatMessage(ChatComponentText(" ${sender.commandSenderName} 希望传送到你这边！") as IChatComponent)
                }

                msg[1] -> {
                    val player: EntityPlayerMP = getPlayer(sender, msg[0])

                    if (HashMapKeyUtil.getKey(PlayerListener.PlayerTPList, player.displayName) != null) {
                        PlayerListener.PlayerTPList.remove(HashMapKeyUtil.getKey(PlayerListener.PlayerTPList, player.displayName).toString())
                    }

                    PlayerListener.PlayerTPList.put(sender.commandSenderName, player.displayName)

                    sender.addChatMessage(ChatComponentText("已经向 ${player.displayName} 发送传送请求！") as IChatComponent)
                    player.addChatMessage(ChatComponentText(" ${sender.commandSenderName} 希望传送你到他那边！") as IChatComponent)

                }

                else -> sender.addChatMessage(ChatComponentText("参数错误。") as IChatComponent)
            }

        } else sender.addChatMessage(ChatComponentText("参数错误。") as IChatComponent)
    }

    override fun getRequiredPermissionLevel(): Int {
        return 0
    }

    override fun canCommandSenderUseCommand(sender: ICommandSender?): Boolean {
        return true
    }
}