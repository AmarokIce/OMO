package club.someoneice.omo.command

import club.someoneice.omo.common.Player
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.context.CommandContext
import net.minecraft.command.CommandSource
import net.minecraft.command.Commands
import net.minecraft.command.Commands.argument
import net.minecraft.command.arguments.EntityArgument
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.util.Util
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.StringTextComponent
import net.minecraft.util.text.TranslationTextComponent
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

                        tpa.source.sendSuccess(StringTextComponent("已經向${playerName.scoreboardName}發送請求！") as ITextComponent , true)
                        (playerName as PlayerEntity).sendMessage(StringTextComponent("${playerAsk.scoreboardName} 希望傳送到你這來！") as ITextComponent, Util.NIL_UUID)

                        // playerAsk.sendMessage(CommandContext("已經向 ${playerName.displayName} 發送一個傳送請求！") as ITextComponent, playerAsk.uuid)
                        // playerName.sendMessage(CommandContext(" ${playerName.displayName} 希望傳送到你這邊來！") as ITextComponent, playerName.uuid)

                        1
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

                        tpa.source.sendSuccess(StringTextComponent("已經向 ${playerName.scoreboardName} 發送一個傳送請求！") as ITextComponent, false)
                        (playerName as PlayerEntity).sendMessage(StringTextComponent("${playerAsk.scoreboardName} 希望傳送你到他那去！") as ITextComponent, Util.NIL_UUID)

                        1
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
                        (playerAsk as PlayerEntity).sendMessage(StringTextComponent("你没有任何请求！") as ITextComponent, playerAsk.uuid)
                    } else {
                        val player: PlayerEntity = Player.getKey(Player.playerTeleportList, playerAsk) as PlayerEntity

                        when(Player.playerGoToList[Player.getKey(Player.playerTeleportList, playerAsk)]) {
                            true ->
                                playerAsk.teleportTo(player.level as ServerWorld, player.x, player.y, player.z, player.xRot, player.yRot)
                            false ->
                                (player as ServerPlayerEntity).teleportTo((playerAsk as PlayerEntity).level as ServerWorld, playerAsk.x, playerAsk.y, playerAsk.z, playerAsk.xRot, playerAsk.yRot)
                            else -> tpa.source.sendFailure(StringTextComponent("遇到問題。") as ITextComponent)
                        }

                        Player.playerTeleportList.remove(Player.getKey(Player.playerTeleportList, playerAsk))
                    }

                    1
                }
        )
    }

    fun TPDENY(event: CommandDispatcher<CommandSource>) {
        val tpa = event.register(
            Commands.literal("tpdeny")
                .executes { tpa ->
                    val playerAsk: ServerPlayerEntity = tpa.source.playerOrException

                    if (Player.getKey(Player.playerTeleportList, playerAsk) == null) {
                        tpa.source.sendFailure(StringTextComponent("你没有任何请求！") as ITextComponent)
                    } else {
                        Player.playerTeleportList.remove(Player.getKey(Player.playerTeleportList, playerAsk))
                        tpa.source.sendSuccess(StringTextComponent("你拒絕了${Player.getKey(Player.playerTeleportList, playerAsk)?.scoreboardName} 的请求！") as ITextComponent, false)
                        (Player.getKey(Player.playerTeleportList, playerAsk) as ServerPlayerEntity).sendMessage(StringTextComponent("${playerAsk.scoreboardName}拒絕了你的傳送請求！") as ITextComponent, Util.NIL_UUID)

                    }

                    1
                }
        )
    }
}