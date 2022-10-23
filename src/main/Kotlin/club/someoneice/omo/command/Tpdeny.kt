package club.someoneice.omo.command

import club.someoneice.omo.event.PlayerListener
import club.someoneice.omo.tool.HashMapKeyHelper
import net.minecraft.command.CommandBase
import net.minecraft.command.CommandException
import net.minecraft.command.ICommandSender
import net.minecraft.server.MinecraftServer
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.TextComponentString
import net.minecraft.util.text.TextComponentTranslation

class Tpdeny : CommandBase() {
    override fun getName(): String {
        return "tpdeny"
    }

    override fun getUsage(sender: ICommandSender): String {
        return "/tpdeny"
    }

    @Throws(CommandException::class)
    override fun execute(server: MinecraftServer, sender: ICommandSender, msg: Array<String>) {
        val playerName:String? = HashMapKeyHelper.getKey(PlayerListener.PlayerTPList, sender.name)

        if (playerName != null) {
            PlayerListener.PlayerTPList.remove(playerName.toString())

            sender.sendMessage(TextComponentString("已經向 {$playerName} 發送了請求！") as ITextComponent)
            getPlayer(server, sender, playerName.toString()).sendMessage(TextComponentTranslation("${sender.name} 拒絕了你的請求！") as ITextComponent)
        } else sender.sendMessage(TextComponentTranslation("你沒有收到任何請求。")  as ITextComponent)
    }

    override fun getRequiredPermissionLevel(): Int {
        return 1
    }
}