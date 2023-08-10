package club.someoneice.omo.command

import club.someoneice.omo.tool.TeleportHelper
import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.ChatComponentText
import net.minecraft.util.ChunkCoordinates
import net.minecraft.world.Teleporter

class Home: CommandBase() {
    override fun getCommandName(): String {
        return "home"
    }

    override fun getCommandUsage(sender: ICommandSender): String {
        return "/home"
    }

    override fun processCommand(sender: ICommandSender, msg: Array<String>) {
        val player: EntityPlayerMP = sender as EntityPlayerMP
        val nbt: NBTTagCompound = player.entityData
        if (nbt.hasKey("home")) {
            val nbtPos: NBTTagCompound = nbt.getTag("home") as NBTTagCompound
            if (player.worldObj.provider.dimensionId != nbtPos.getInteger("world"))
                player.mcServer.configurationManager.transferPlayerToDimension(player, nbtPos.getInteger("world"), TeleportHelper(player.mcServer.worldServerForDimension(nbtPos.getInteger("world"))) as Teleporter)
            player.playerNetServerHandler.setPlayerLocation(nbtPos.getDouble("posX"), nbtPos.getDouble("posY"), nbtPos.getDouble("posZ"), player.rotationYaw, player.rotationPitch);
        } else if (player.bedLocation != null) {
            val bedLocal: ChunkCoordinates = player.getBedLocation(0)
            if (player.worldObj.provider.dimensionId != 0)
                player.mcServer.configurationManager.transferPlayerToDimension(player, 0, TeleportHelper(player.mcServer.worldServerForDimension(0)) as Teleporter)
            player.playerNetServerHandler.setPlayerLocation(bedLocal.posX.toDouble(), bedLocal.posY.toDouble(), bedLocal.posZ.toDouble(), player.rotationYaw, player.rotationPitch)
        } else {
            sender.addChatMessage(ChatComponentText("You have no home and you have no bed!"))
        }
    }

    override fun getRequiredPermissionLevel(): Int {
        return 0
    }

    override fun canCommandSenderUseCommand(sender: ICommandSender?): Boolean {
        return true
    }
}