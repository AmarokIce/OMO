package club.someoneice.omo.command

import club.someoneice.omo.common.Player
import club.someoneice.omo.config.Config
import com.mojang.brigadier.CommandDispatcher
import net.minecraft.block.Blocks
import net.minecraft.command.CommandSource
import net.minecraft.command.Commands
import net.minecraft.command.Commands.argument
import net.minecraft.command.arguments.EntityArgument
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.StringTextComponent
import net.minecraft.world.World
import net.minecraft.world.server.ServerWorld
import net.minecraftforge.fml.common.Mod.EventBusSubscriber
import java.util.*

@EventBusSubscriber
class CommandHelper {
    fun TPA(event: CommandDispatcher<CommandSource>) {
        val tpa = event.register(
            Commands.literal("tpa")
                .then(argument("player", EntityArgument.players())
                    .executes { tpa ->
                        val playerName: ServerPlayerEntity = EntityArgument.getPlayer(tpa, "player")
                        val playerAsk: ServerPlayerEntity = tpa.source.playerOrException

                        if (Player.getKey(Player.playerTeleportList, playerName) != null) {
                            Player.playerTeleportList.remove(Player.getKey(Player.playerTeleportList, playerName))
                        }

                        Player.playerTeleportList[playerAsk] = playerName
                        Player.playerGoToList[playerAsk] = false

                        tpa.source.sendSuccess(StringTextComponent("已经向 ${playerName.scoreboardName}发送了传送请求！") , true)
                        (playerName as PlayerEntity).sendMessage(StringTextComponent("${playerAsk.scoreboardName} 想要传送到你这里！"), (playerName as PlayerEntity).uuid)

                        0
                    }
            )
        )
    }

    fun TPAHERE(event: CommandDispatcher<CommandSource>) {
        val tpa = event.register(
            Commands.literal("tpahere")
                .then(argument("player", EntityArgument.players())
                    .executes { tpa ->
                        val playerName: ServerPlayerEntity = EntityArgument.getPlayer(tpa, "player")
                        val playerAsk: ServerPlayerEntity = tpa.source.playerOrException

                        if (Player.getKey(Player.playerTeleportList, playerName) != null) {
                            Player.playerTeleportList.remove(Player.getKey(Player.playerTeleportList, playerName))
                        }

                        Player.playerTeleportList[playerAsk] = playerName
                        Player.playerGoToList[playerAsk] = true

                        tpa.source.sendSuccess(StringTextComponent("已经向 ${playerName.scoreboardName} 发送了传送请求！"), false)
                        (playerName as PlayerEntity).sendMessage(StringTextComponent("${playerAsk.scoreboardName} 希望传送你到他那边去！"), (playerName as PlayerEntity).uuid)

                        0
                    }
                )
        )
    }

    fun TPACCEPT(event: CommandDispatcher<CommandSource>) {
        val tpa = event.register(
            Commands.literal("tpaccept")
                .executes { tpa ->
                    val playerAsk: ServerPlayerEntity = tpa.source.playerOrException

                    if (Player.getKey(Player.playerTeleportList, playerAsk) == null) {
                        (playerAsk as PlayerEntity).sendMessage(StringTextComponent("你没有收到任何请求！"), (playerAsk as PlayerEntity).uuid)
                    } else {
                        val player: PlayerEntity = Player.getKey(Player.playerTeleportList, playerAsk) as PlayerEntity

                        when(Player.playerGoToList[Player.getKey(Player.playerTeleportList, playerAsk)]) {
                            true ->
                                playerAsk.teleportTo(player.level as ServerWorld, player.x, player.y, player.z, player.xRot, player.yRot)
                            false ->
                                (player as ServerPlayerEntity).teleportTo((playerAsk as PlayerEntity).level as ServerWorld, playerAsk.x, playerAsk.y, playerAsk.z, playerAsk.xRot, playerAsk.yRot)
                            else -> tpa.source.sendFailure(StringTextComponent("参数或未知问题。"))
                        }

                        Player.playerTeleportList.remove(Player.getKey(Player.playerTeleportList, playerAsk))
                    }

                    0
                }
        )
    }

    fun TPDENY(event: CommandDispatcher<CommandSource>) {
        val tpa = event.register(
            Commands.literal("tpdeny")
                .executes { tpa ->
                    val playerAsk: ServerPlayerEntity = tpa.source.playerOrException

                    if (Player.getKey(Player.playerTeleportList, playerAsk) == null) {
                        tpa.source.sendFailure(StringTextComponent("你没有收到任何请求！"))
                    } else {
                        Player.playerTeleportList.remove(Player.getKey(Player.playerTeleportList, playerAsk))
                        tpa.source.sendSuccess(StringTextComponent("你拒绝了 ${Player.getKey(Player.playerTeleportList, playerAsk)?.scoreboardName} 的传送请求！"), false)
                        (Player.getKey(Player.playerTeleportList, playerAsk) as ServerPlayerEntity).sendMessage(StringTextComponent("${playerAsk.scoreboardName} 拒绝了你的请求！"), (Player.getKey(Player.playerTeleportList, playerAsk) as ServerPlayerEntity).uuid)

                    }

                    0
                }
        )
    }

    fun BACK(event: CommandDispatcher<CommandSource>) {
        val back = event.register(
            Commands.literal("back")
                .executes { back ->
                    val playerAsk: ServerPlayerEntity = back.source.playerOrException

                    if (Player.playerDeath.containsKey(playerAsk)) {
                        playerAsk.teleportTo(Player.playerDeath[playerAsk]?.level as ServerWorld, Player.playerDeath[playerAsk]?.x as Double, Player.playerDeath[playerAsk]?.y as Double, Player.playerDeath[playerAsk]?.z as Double, Player.playerDeath[playerAsk]?.RotX as Float, Player.playerDeath[playerAsk]?.RotY as Float)
                        back.source.sendSuccess(StringTextComponent("已经回到上一次死亡地点！"), true)
                    } else {
                        back.source.sendFailure(StringTextComponent("你从未死亡."))
                    }

                    0
                }
        )
    }

    fun RTP(event: CommandDispatcher<CommandSource>) {
        val rtp = event.register(
            Commands.literal("rtp")
                .executes { rtp ->
                    val player: ServerPlayerEntity = rtp.source.playerOrException
                    val world: World = player.level
                    if (Config.rtp!!.get()) {
                        val random = Random()
                        var x: Int = random.nextInt(Config.PosX!!.get())
                        var z: Int = random.nextInt(Config.PosZ!!.get())
                        if (!random.nextBoolean()) x = 0 - x
                        if (!random.nextBoolean()) z = 0 - z

                        var y: Int = random.nextInt(80) + 40
                        while (world.getBlockState(BlockPos(x, y, z)) != Blocks.AIR.defaultBlockState()) y += 2
                        while (world.getBlockState(BlockPos(x, y - 2, z)) == Blocks.AIR.defaultBlockState()) y -= 1

                        player.connection.teleport(x.toDouble(), y.toDouble(), z.toDouble(), player.xRot, player.yRot)
                    } else {
                        rtp.source.sendFailure(StringTextComponent("Server Close RTP.") as ITextComponent)
                    }

                    0
                }
        )
    }
}