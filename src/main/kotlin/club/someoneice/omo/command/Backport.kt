package club.someoneice.omo.command


import club.someoneice.omo.event.PlayerListener
import club.someoneice.omo.tool.TeleportHelper
import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.util.ChatComponentText
import net.minecraft.util.IChatComponent
import net.minecraft.world.Teleporter
import net.minecraft.world.World

class Backport : CommandBase() {
    override fun getCommandName(): String {
        return "backport"
    }

    override fun getCommandUsage(p_71518_1_: ICommandSender): String {
        return "/backport"
    }

    override fun processCommand(sender: ICommandSender, msg: Array<String>) {
        val player: EntityPlayerMP = getPlayer(sender, sender.commandSenderName.toString()) as EntityPlayerMP
        if (PlayerListener.PlayerPortList.containsKey(sender.commandSenderName)) {
            val world: World? = PlayerListener.PlayerPortList[player.commandSenderName]?.world
            player.mcServer.configurationManager.transferPlayerToDimension(player, world?.provider?.dimensionId as Int, TeleportHelper(player.mcServer.worldServerForDimension(world.provider?.dimensionId as Int)) as Teleporter)

            player.playerNetServerHandler.setPlayerLocation(PlayerListener.PlayerPortList[player.displayName]?.x as Double, PlayerListener.PlayerPortList[player.displayName]?.y as Double, PlayerListener.PlayerPortList[player.displayName]?.z as Double, PlayerListener.PlayerPortList[player.displayName]?.RotX as Float, PlayerListener.PlayerPortList[player.displayName]?.RotY as Float)
            sender.addChatMessage(ChatComponentText("Back the port！") as IChatComponent)
        } else sender.addChatMessage(ChatComponentText("You have no port this time.(Please use home if you want to back anytime)") as IChatComponent)
    }

    override fun getRequiredPermissionLevel(): Int {
        return 0
    }

    override fun canCommandSenderUseCommand(sender: ICommandSender?): Boolean {
        return true
    }
}