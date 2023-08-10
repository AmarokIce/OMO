package club.someoneice.omo.command

import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.ChatComponentText

class SetHome: CommandBase() {
    override fun getCommandName(): String {
        return "sethome"
    }

    override fun getCommandUsage(sender: ICommandSender): String {
        return "/sethome"
    }

    override fun processCommand(sender: ICommandSender, msg: Array<String>) {
        val player: EntityPlayerMP = sender as EntityPlayerMP
        val nbt: NBTTagCompound = player.entityData
        val nbtPos = NBTTagCompound()
        nbtPos.setInteger("world", player.worldObj.provider.dimensionId)
        nbtPos.setDouble("posX", player.posX)
        nbtPos.setDouble("posY", player.posY)
        nbtPos.setDouble("posZ", player.posZ)

        nbt.setTag("home", nbtPos)
        sender.addChatMessage(ChatComponentText("Successful to set home!"))
    }

    override fun getRequiredPermissionLevel(): Int {
        return 0
    }

    override fun canCommandSenderUseCommand(sender: ICommandSender?): Boolean {
        return true
    }
}