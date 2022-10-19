package club.someoneice.omo.command

import club.someoneice.omo.common.Player
import com.mojang.brigadier.CommandDispatcher
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.commands.Commands.argument
import net.minecraft.commands.arguments.EntityArgument
import net.minecraft.network.chat.TranslatableComponent
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraftforge.fml.common.Mod.EventBusSubscriber

@EventBusSubscriber
class CommandHelper {
    fun TPA(event: CommandDispatcher<CommandSourceStack>) {
        val tpa = event.register(
            Commands.literal("tpa")
                .then(argument("player", EntityArgument.players())
                    .executes { tpa ->
                        val playerName: ServerPlayer = EntityArgument.getPlayer(tpa, "player")
                        val playerAsk: ServerPlayer = tpa.source.playerOrException

                        if (Player.getKey(Player.playerTeleportList, playerName) != null) {
                            Player.playerTeleportList.remove(Player.getKey(Player.playerTeleportList, playerName))
                        }

                        Player.playerTeleportList[playerAsk] = playerName
                        Player.playerGoToList[playerAsk] = false

                        tpa.source.sendSuccess(TranslatableComponent("已经向 ${playerName.scoreboardName}发送了传送请求！") , true)
                        (playerName as net.minecraft.world.entity.player.Player).sendMessage(TranslatableComponent("${playerAsk.scoreboardName} 想要传送到你这里！"), (playerName as net.minecraft.world.entity.player.Player).uuid)

                        0
                    }
            )
        )
    }

    fun TPAHERE(event: CommandDispatcher<CommandSourceStack>) {
        val tpa = event.register(
            Commands.literal("tpahere")
                .then(argument("player", EntityArgument.players())
                    .executes { tpa ->
                        val playerName: ServerPlayer = EntityArgument.getPlayer(tpa, "player")
                        val playerAsk: ServerPlayer = tpa.source.playerOrException

                        if (Player.getKey(Player.playerTeleportList, playerName) != null) {
                            Player.playerTeleportList.remove(Player.getKey(Player.playerTeleportList, playerName))
                        }

                        Player.playerTeleportList[playerAsk] = playerName
                        Player.playerGoToList[playerAsk] = true

                        tpa.source.sendSuccess(TranslatableComponent("已经向 ${playerName.scoreboardName} 发送了传送请求！"), false)
                        (playerName as net.minecraft.world.entity.player.Player).sendMessage(TranslatableComponent("${playerAsk.scoreboardName} 希望传送你到他那边去！"), (playerName as net.minecraft.world.entity.player.Player).uuid)

                        0
                    }
                )
        )
    }

    fun TPACCEPT(event: CommandDispatcher<CommandSourceStack>) {
        val tpa = event.register(
            Commands.literal("tpaccept")
                .executes { tpa ->
                    val playerAsk: ServerPlayer = tpa.source.playerOrException

                    if (Player.getKey(Player.playerTeleportList, playerAsk) == null) {
                        (playerAsk as net.minecraft.world.entity.player.Player).sendMessage(TranslatableComponent("你没有收到任何请求！"), (playerAsk as net.minecraft.world.entity.player.Player).uuid)
                    } else {
                        val player: net.minecraft.world.entity.player.Player = Player.getKey(Player.playerTeleportList, playerAsk) as net.minecraft.world.entity.player.Player

                        when(Player.playerGoToList[Player.getKey(Player.playerTeleportList, playerAsk)]) {
                            true ->
                                playerAsk.teleportTo(player.level as ServerLevel, player.x, player.y, player.z, player.xRot, player.yRot)
                            false ->
                                (player as ServerPlayer).teleportTo((playerAsk as net.minecraft.world.entity.player.Player).level as ServerLevel, playerAsk.x, playerAsk.y, playerAsk.z, playerAsk.xRot, playerAsk.yRot)
                            else -> tpa.source.sendFailure(TranslatableComponent("参数或未知问题。"))
                        }

                        Player.playerTeleportList.remove(Player.getKey(Player.playerTeleportList, playerAsk))
                    }

                    0
                }
        )
    }

    fun TPDENY(event: CommandDispatcher<CommandSourceStack>) {
        val tpa = event.register(
            Commands.literal("tpdeny")
                .executes { tpa ->
                    val playerAsk: ServerPlayer = tpa.source.playerOrException

                    if (Player.getKey(Player.playerTeleportList, playerAsk) == null) {
                        tpa.source.sendFailure(TranslatableComponent("你没有收到任何请求！"))
                    } else {
                        Player.playerTeleportList.remove(Player.getKey(Player.playerTeleportList, playerAsk))
                        tpa.source.sendSuccess(TranslatableComponent("你拒绝了 ${Player.getKey(Player.playerTeleportList, playerAsk)?.scoreboardName} 的传送请求！"), false)
                        (Player.getKey(Player.playerTeleportList, playerAsk) as ServerPlayer).sendMessage(TranslatableComponent("${playerAsk.scoreboardName} 拒绝了你的请求！"), (Player.getKey(Player.playerTeleportList, playerAsk) as ServerPlayer).uuid)

                    }

                    0
                }
        )
    }

    fun BACK(event: CommandDispatcher<CommandSourceStack>) {
        val back = event.register(
            Commands.literal("back")
                .executes { back ->
                    val playerAsk: ServerPlayer = back.source.playerOrException

                    if (Player.playerDeath.containsKey(playerAsk)) {
                        playerAsk.teleportTo(Player.playerDeath[playerAsk]?.level as ServerLevel, Player.playerDeath[playerAsk]?.x as Double, Player.playerDeath[playerAsk]?.y as Double, Player.playerDeath[playerAsk]?.z as Double, Player.playerDeath[playerAsk]?.RotX as Float, Player.playerDeath[playerAsk]?.RotY as Float)
                        back.source.sendSuccess(TranslatableComponent("已经回到上一次死亡地点！"), true)
                    } else {
                        back.source.sendFailure(TranslatableComponent("你从未死亡."))
                    }

                    0
                }
        )
    }
}