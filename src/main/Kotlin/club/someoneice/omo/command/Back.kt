package club.someoneice.omo.command

import club.someoneice.omo.event.PlayerListener
import net.minecraft.command.CommandBase
import net.minecraft.command.CommandException
import net.minecraft.command.ICommandSender
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.server.MinecraftServer
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.TextComponentTranslation

class Back : CommandBase() {
    override fun getName(): String {
        return "back"
    }

    override fun getUsage(sender: ICommandSender): String {
        return "back"
    }

    @Throws(CommandException::class)
    override fun execute(server: MinecraftServer, sender: ICommandSender, msg: Array<String>) {
        val player: EntityPlayerMP = getPlayer(server, sender, sender.name) as EntityPlayerMP
        if (PlayerListener.PlayerDataList.containsKey(sender.name)) {
            player.connection.setPlayerLocation(PlayerListener.PlayerDataList[sender.name]?.x as Double , PlayerListener.PlayerDataList[sender.name]?.y as Double, PlayerListener.PlayerDataList[sender.name]?.z as Double, PlayerListener.PlayerDataList[sender.name]?.RotX as Float, PlayerListener.PlayerDataList[sender.name]?.RotY as Float)
            sender.sendMessage(TextComponentTranslation("已返回上一次死亡地點!") as ITextComponent)
        } else {
            sender.sendMessage(TextComponentTranslation("你從未死亡.") as ITextComponent)
        }

    }

    override fun getRequiredPermissionLevel(): Int {
        return 1
    }
}