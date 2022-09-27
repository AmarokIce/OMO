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

                        tpa.source.sendSuccess(StringTextComponent("Now make a tp ask to ${playerName.scoreboardName}！") as ITextComponent , true)
                        (playerName as PlayerEntity).sendMessage(StringTextComponent("${playerAsk.scoreboardName} wanna tp to your here！") as ITextComponent, Util.NIL_UUID)

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

                        tpa.source.sendSuccess(StringTextComponent("Now make a tp ask to ${playerName.scoreboardName} ！") as ITextComponent, false)
                        (playerName as PlayerEntity).sendMessage(StringTextComponent("${playerAsk.scoreboardName} wanna tp you to his side！") as ITextComponent, Util.NIL_UUID)

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
                        (playerAsk as PlayerEntity).sendMessage(StringTextComponent("you have no tp ask！") as ITextComponent, playerAsk.uuid)
                    } else {
                        val player: PlayerEntity = Player.getKey(Player.playerTeleportList, playerAsk) as PlayerEntity

                        when(Player.playerGoToList[Player.getKey(Player.playerTeleportList, playerAsk)]) {
                            true ->
                                playerAsk.teleportTo(player.level as ServerWorld, player.x, player.y, player.z, player.xRot, player.yRot)
                            false ->
                                (player as ServerPlayerEntity).teleportTo((playerAsk as PlayerEntity).level as ServerWorld, playerAsk.x, playerAsk.y, playerAsk.z, playerAsk.xRot, playerAsk.yRot)
                            else -> tpa.source.sendFailure(StringTextComponent("Something happen error.") as ITextComponent)
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
                        tpa.source.sendFailure(StringTextComponent("You have no tp ask！") as ITextComponent)
                    } else {
                        Player.playerTeleportList.remove(Player.getKey(Player.playerTeleportList, playerAsk))
                        tpa.source.sendSuccess(StringTextComponent("You deny the tp ask from ${Player.getKey(Player.playerTeleportList, playerAsk)?.scoreboardName} ！") as ITextComponent, false)
                        (Player.getKey(Player.playerTeleportList, playerAsk) as ServerPlayerEntity).sendMessage(StringTextComponent("${playerAsk.scoreboardName} deny your tp ask！") as ITextComponent, Util.NIL_UUID)
                    }

                    1
                }
        )
    }

    fun BACK(event: CommandDispatcher<CommandSource>) {
        val tpa = event.register(
            Commands.literal("back")
                .executes { back ->
                    val playerAsk: ServerPlayerEntity = back.source.playerOrException

                    if (Player.playerDeath.containsKey(playerAsk)) {
                        playerAsk.teleportTo(Player.playerDeath[playerAsk]?.level as ServerWorld, Player.playerDeath[playerAsk]?.x as Double, Player.playerDeath[playerAsk]?.y as Double, Player.playerDeath[playerAsk]?.z as Double, Player.playerDeath[playerAsk]?.RotX as Float, Player.playerDeath[playerAsk]?.RotY as Float)
                        back.source.sendSuccess(StringTextComponent("Now back the last died！"), true)
                    } else {
                        back.source.sendFailure(StringTextComponent("Something happen error or you have never died."))
                    }

                    1
                }
        )
    }
}