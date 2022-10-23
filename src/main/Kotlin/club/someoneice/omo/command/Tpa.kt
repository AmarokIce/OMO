package club.someoneice.omo.command

import club.someoneice.omo.event.PlayerListener
import club.someoneice.omo.tool.HashMapKeyHelper
import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.server.MinecraftServer
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.TextComponentTranslation

class Tpa : CommandBase() {
    override fun getName(): String {
        return "tpa"
    }

    override fun getUsage(sender: ICommandSender): String {
        return "/tpa - [player] [player2]"
    }

    override fun execute(server: MinecraftServer, sender: ICommandSender, msg: Array<String>) {
        if (msg.size == 1 && sender.name != msg[0]) {
            val player: EntityPlayerMP = getPlayer(server, sender, msg[0])

            if (HashMapKeyHelper.getKey(PlayerListener.PlayerTPList, player.displayNameString) != null) {
                PlayerListener.PlayerTPList.remove(
                    HashMapKeyHelper.getKey(
                        PlayerListener.PlayerTPList,
                        player.displayNameString
                    ).toString()
                )
            }

            PlayerListener.PlayerTPList[sender.name] = player.displayNameString
            sender.sendMessage(TextComponentTranslation("Now make a ask to ${player.name}") as ITextComponent)
            player.sendMessage(TextComponentTranslation("  ${sender.name} 想要傳送到你這邊來!") as ITextComponent)

            PlayerListener.PlayerTPList[sender.name] = player.name
            PlayerListener.PlayerGoing[sender.name] = false

        } else if (msg.size == 2) {
                when (sender.name) {
                    msg[0] -> {
                        val player: EntityPlayerMP = getPlayer(server, sender, msg[1])

                        if (HashMapKeyHelper.getKey(PlayerListener.PlayerTPList, player.name) != null) {
                            PlayerListener.PlayerTPList.remove(HashMapKeyHelper.getKey(PlayerListener.PlayerTPList, player.name).toString())
                        }

                        PlayerListener.PlayerTPList[sender.name] = player.name
                        PlayerListener.PlayerGoing[sender.name] = false


                        sender.sendMessage(TextComponentTranslation("已經向 ${player.name} 發送請求！") as ITextComponent)
                        player.sendMessage(TextComponentTranslation(" ${sender.name} 想要傳送到你這邊來!") as ITextComponent)
                    }

                    msg[1] -> {
                        val player: EntityPlayerMP = getPlayer(server, sender, msg[0])

                        if (HashMapKeyHelper.getKey(PlayerListener.PlayerTPList, player.name) != null) {
                            PlayerListener.PlayerTPList.remove(HashMapKeyHelper.getKey(PlayerListener.PlayerTPList, player.name).toString())
                        }

                        PlayerListener.PlayerTPList[sender.name] = player.name
                        PlayerListener.PlayerGoing[sender.name] = true

                        sender.sendMessage(TextComponentTranslation("已經向 ${player.name} 發送請求！ ") as ITextComponent)
                        player.sendMessage(TextComponentTranslation(" ${sender.name} 想將你傳送到它那邊去 !") as ITextComponent)

                    }

                    else -> sender.sendMessage(TextComponentTranslation("參數錯誤。") as ITextComponent)
                }

            } else sender.sendMessage(TextComponentTranslation("參數錯誤。") as ITextComponent)
        }

    override fun getRequiredPermissionLevel(): Int {
        return 1
    }
}