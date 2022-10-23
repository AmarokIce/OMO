package club.someoneice.omo.command

import club.someoneice.omo.event.PlayerListener
import club.someoneice.omo.tool.HashMapKeyHelper
import net.minecraft.command.CommandBase
import net.minecraft.command.CommandException
import net.minecraft.command.ICommandSender
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.server.MinecraftServer
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.TextComponentTranslation

class Tpaccept : CommandBase() {
    override fun getName(): String {
        return "tpaccept"
    }

    override fun getUsage(sender: ICommandSender): String {
        return "/tpaccept"
    }

    @Throws(CommandException::class)
    override fun execute(server: MinecraftServer, sender: ICommandSender, msg: Array<String>) {
        val playerName:String? = HashMapKeyHelper.getKey(PlayerListener.PlayerTPList, sender.name)

        if (playerName != null) {
            val player: EntityPlayerMP = sender as EntityPlayerMP
            val playerAsk: EntityPlayerMP = getPlayer(server, sender, playerName)
            if (player.world == playerAsk.world) {
                if (!PlayerListener.PlayerGoing[playerName]!!) playerAsk.connection.setPlayerLocation(player.posX, player.posY, player.posZ, player.rotationYaw, player.rotationPitch)
                else player.connection.setPlayerLocation(playerAsk.posX, playerAsk.posY, playerAsk.posZ, playerAsk.rotationYaw, playerAsk.rotationPitch)
            } else {
                player.sendMessage(TextComponentTranslation("You are not in the same world.") as ITextComponent)
                playerAsk.sendMessage(TextComponentTranslation("You are not in the same world.") as ITextComponent)
            }



            PlayerListener.PlayerTPList.remove(playerName.toString())
        } else sender.sendMessage(TextComponentTranslation("You not had any ask.") as ITextComponent)
    }
}