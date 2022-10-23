package club.someoneice.omo.command

import club.someoneice.omo.cofing.PineappleConfig
import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.init.Blocks
import net.minecraft.server.MinecraftServer
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import java.util.*

class Rtp: CommandBase() {
    override fun getName(): String {
        return "rtp"
    }

    override fun getUsage(sender: ICommandSender): String {
        return "/rtp"
    }

    override fun execute(server: MinecraftServer, sender: ICommandSender, msg: Array<String>) {
        val player = sender.commandSenderEntity as EntityPlayerMP
        val world: World = sender.entityWorld
        val random = Random()
        var x = random.nextInt(PineappleConfig.PosX)
        var y = random.nextInt(80) + 40
        var z = random.nextInt(PineappleConfig.PosZ)

        if (!random.nextBoolean()) x = 0 - x
        if (!random.nextBoolean()) z = 0 - z

        while (world.getBlockState(BlockPos(x, y, z)) != Blocks.AIR.defaultState) y += 2
        while (world.getBlockState(BlockPos(x, y, z)) != Blocks.AIR.defaultState) y -= 1

        player.connection.setPlayerLocation(x.toDouble(), y.toDouble(), z.toDouble(), player.rotationYaw, player.rotationPitch)
    }

    override fun getRequiredPermissionLevel(): Int {
        return if (PineappleConfig.RTP) 0 else 4
    }

    override fun checkPermission(server: MinecraftServer, sender: ICommandSender): Boolean {
        return if (PineappleConfig.RTP) true else sender.canUseCommand(this.requiredPermissionLevel, this.name)
    }
}