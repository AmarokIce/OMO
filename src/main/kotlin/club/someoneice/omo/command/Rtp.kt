package club.someoneice.omo.command

import club.someoneice.omo.cofing.PineappleConfig
import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.init.Blocks
import net.minecraft.world.World
import java.util.*

class Rtp: CommandBase() {
    override fun getCommandName(): String {
        return "rtp"
    }

    override fun getCommandUsage(sender: ICommandSender): String {
        return "/rtp"
    }

    override fun processCommand(sender: ICommandSender, msg: Array<String>) {
        val player = getPlayer(sender, sender.commandSenderName.toString()) as EntityPlayerMP
        val world: World = sender.entityWorld
        val random = Random()
        var x = random.nextInt(PineappleConfig.PosX)
        var y = random.nextInt(80) + 40
        var z = random.nextInt(PineappleConfig.PosZ)

        if (!random.nextBoolean()) x = 0 - x
        if (!random.nextBoolean()) z = 0 - z

        while (world.getBlock(x, y, z) != Blocks.air) y += 2
        while (world.getBlock(x, y - 2, z) == Blocks.air) y -= 1

        player.playerNetServerHandler.setPlayerLocation(x.toDouble(), y.toDouble(), z.toDouble(), player.rotationYaw, player.rotationPitch)
    }

    override fun getRequiredPermissionLevel(): Int {
        return if (PineappleConfig.RTP) 0 else 4
    }

    override fun canCommandSenderUseCommand(sender: ICommandSender): Boolean {
        return if (PineappleConfig.RTP) true else sender.canCommandSenderUseCommand(this.requiredPermissionLevel, this.commandName)
    }
}