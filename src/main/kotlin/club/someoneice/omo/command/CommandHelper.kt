package club.someoneice.omo.command

import club.someoneice.omo.Config
import club.someoneice.omo.common.PlayerUtil
import com.mojang.brigadier.CommandDispatcher
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.commands.Commands.argument
import net.minecraft.commands.arguments.EntityArgument
import net.minecraft.commands.arguments.coordinates.Vec3Argument
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.phys.Vec3
import net.minecraftforge.fml.common.Mod.EventBusSubscriber
import java.util.*

@EventBusSubscriber
class CommandHelper {
    fun TPA(event: CommandDispatcher<CommandSourceStack>) {
        event.register(
            Commands.literal("tpa").requires {it.hasPermission(0)}
                .then(argument("player", EntityArgument.players())
                    .executes { tpa ->
                        val playerName: ServerPlayer = EntityArgument.getPlayer(tpa, "player")
                        val playerAsk: ServerPlayer = tpa.source.playerOrException

                        if (PlayerUtil.getKey(PlayerUtil.playerTeleportList, playerName) != null) {
                            PlayerUtil.playerTeleportList.remove(PlayerUtil.getKey(PlayerUtil.playerTeleportList, playerName))
                        }

                        PlayerUtil.playerTeleportList[playerAsk] = playerName
                        PlayerUtil.playerGoToList[playerAsk] = false

                        tpa.source.sendSuccess({ Component.literal("Successful send an ask to ${playerName.scoreboardName}！") } , true)
                        playerName.sendSystemMessage(Component.literal("${playerAsk.scoreboardName} wanna teleport to your side！"))

                        0
                    }
            )
        )
    }

    fun TPAHERE(event: CommandDispatcher<CommandSourceStack>) {
        event.register(
            Commands.literal("tpahere").requires {it.hasPermission(0)}
                .then(argument("player", EntityArgument.players())
                    .executes { tpa ->
                        val playerName: ServerPlayer = EntityArgument.getPlayer(tpa, "player")
                        val playerAsk: ServerPlayer = tpa.source.playerOrException

                        if (PlayerUtil.getKey(PlayerUtil.playerTeleportList, playerName) != null) {
                            PlayerUtil.playerTeleportList.remove(PlayerUtil.getKey(PlayerUtil.playerTeleportList, playerName))
                        }

                        PlayerUtil.playerTeleportList[playerAsk] = playerName
                        PlayerUtil.playerGoToList[playerAsk] = true

                        tpa.source.sendSuccess({ Component.literal("Successful send an ask to ${playerName.scoreboardName}！") }, false)
                        playerName.sendSystemMessage(Component.literal("${playerAsk.scoreboardName} wanna teleport you to his side！"))

                        0
                    }
                )
        )
    }

    fun TPACCEPT(event: CommandDispatcher<CommandSourceStack>) {
        event.register(
            Commands.literal("tpaccept").requires {it.hasPermission(0)}
                .executes { tpa ->
                    val playerAsk: ServerPlayer = tpa.source.playerOrException

                    if (PlayerUtil.getKey(PlayerUtil.playerTeleportList, playerAsk) == null) {
                        playerAsk.sendSystemMessage(Component.literal("You have no ask！"))
                    } else {
                        val player: ServerPlayer = PlayerUtil.getKey(PlayerUtil.playerTeleportList, playerAsk) ?: return@executes 0

                        when(PlayerUtil.playerGoToList[PlayerUtil.getKey(PlayerUtil.playerTeleportList, playerAsk)]) {
                            true ->
                                playerAsk.teleportTo(player.level() as ServerLevel, player.x, player.y, player.z, player.xRot, player.yRot)
                            false ->
                                player.teleportTo((playerAsk as net.minecraft.world.entity.player.Player).level() as ServerLevel, playerAsk.x, playerAsk.y, playerAsk.z, playerAsk.xRot, playerAsk.yRot)
                            else -> tpa.source.sendFailure(Component.literal("Soemthing error."))
                        }

                        PlayerUtil.playerTeleportList.remove(PlayerUtil.getKey(PlayerUtil.playerTeleportList, playerAsk))
                    }

                    0
                }
        )
    }

    fun TPDENY(event: CommandDispatcher<CommandSourceStack>) {
        event.register(
            Commands.literal("tpdeny").requires {it.hasPermission(0)}
                .executes { tpa ->
                    val playerAsk: ServerPlayer = tpa.source.playerOrException

                    if (PlayerUtil.getKey(PlayerUtil.playerTeleportList, playerAsk) == null) {
                        tpa.source.sendFailure(Component.literal("You have no ask！"))
                    } else {
                        PlayerUtil.playerTeleportList.remove(PlayerUtil.getKey(PlayerUtil.playerTeleportList, playerAsk))
                        tpa.source.sendSuccess({ Component.literal("You deny the ask from ${PlayerUtil.getKey(PlayerUtil.playerTeleportList, playerAsk)?.scoreboardName}！") }, false)
                        PlayerUtil.getKey(PlayerUtil.playerTeleportList, playerAsk)?.sendSystemMessage(Component.literal("${playerAsk.scoreboardName} deny your ask！"))
                    }

                    0
                }
        )
    }

    fun BACK(event: CommandDispatcher<CommandSourceStack>) {
        event.register(
            Commands.literal("back").requires {it.hasPermission(0)}
                .executes { back ->
                    val playerAsk: ServerPlayer = back.source.playerOrException

                    if (PlayerUtil.playerDeath.containsKey(playerAsk)) {
                        playerAsk.teleportTo(PlayerUtil.playerDeath[playerAsk]?.level as ServerLevel, PlayerUtil.playerDeath[playerAsk]?.x as Double, PlayerUtil.playerDeath[playerAsk]?.y as Double, PlayerUtil.playerDeath[playerAsk]?.z as Double, PlayerUtil.playerDeath[playerAsk]?.rotX as Float, PlayerUtil.playerDeath[playerAsk]?.rotY as Float)
                        back.source.sendSuccess({ Component.literal("Successful back to the side of last dead！") }, true)
                    } else {
                        back.source.sendFailure(Component.literal("You have never dead."))
                    }

                    0
                }
        )
    }

    fun RTP(event: CommandDispatcher<CommandSourceStack>) {
        event.register(
            Commands.literal("rtp").requires {it.hasPermission(0)}
				.executes { rtp ->
                val player: ServerPlayer = rtp.source.playerOrException
                val world: Level = player.level()
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
                    rtp.source.sendFailure(Component.literal("Server Close RTP."))
                }

                0
            }
        )
    }

    fun OMOTP(event: CommandDispatcher<CommandSourceStack>) {
        event.register(
            Commands.literal("omotp").requires {
                it.hasPermission(0)
            }.then(argument("player", EntityArgument.players()).executes {
                val playerSource: ServerPlayer = it.source.playerOrException

                val player: ServerPlayer = EntityArgument.getPlayer(it, "player")

                val x: Double = player.x
                val y: Double = player.y
                val z: Double = player.z
                val world: ServerLevel = player.level() as ServerLevel

                playerSource.teleportTo(world, x, y, z, playerSource.xRot, playerSource.yRot)

                0
            }).then(argument("pos", Vec3Argument.vec3()).executes {
                val player: ServerPlayer = it.source.playerOrException

                val vec: Vec3 = Vec3Argument.getVec3(it, "pos")

                val x: Double = vec.x
                val y: Double = vec.y
                val z: Double = vec.z

                player.teleportTo(x, y, z)

                0
            })
        )
    }

    fun HOME(event: CommandDispatcher<CommandSourceStack>) {
        event.register(
            Commands.literal("home").requires { it.hasPermission(0)}.executes {
                val player: ServerPlayer = it.source.playerOrException
                val playerNBT: CompoundTag = player.persistentData
                player.level()
                if (playerNBT.contains("home")) {
                    val nbt : CompoundTag = playerNBT.getCompound("home")
                    var world: ServerLevel? = null
                    for (i in it.source.server.levelKeys()) {
                        if (i.registry().namespace == nbt.getString("world")) {
                            world = it.source.server.getLevel(i)
                            break
                        }
                    }
                    player.teleportTo(world as ServerLevel, nbt.getDouble("posX"), nbt.getDouble("posY"), nbt.getDouble("posZ"), player.yRot, player.xRot)
                } else if (player.sleepingPos.orElse(null) != null) {
                    player.teleportTo(it.source.server.overworld(), player.sleepingPos.get().x.toDouble(), player.sleepingPos.get().y.toDouble(), player.sleepingPos.get().z.toDouble(), player.yRot, player.xRot)
                } else it.source.sendFailure(Component.literal("You have no home or bed!"))

                0
            }
        )
    }

    fun SETHOME(event: CommandDispatcher<CommandSourceStack>) {
        event.register(
            Commands.literal("sethome").requires { it.hasPermission(0)}.executes {
                val player: ServerPlayer = it.source.playerOrException
                val playerNBT: CompoundTag = player.persistentData
                val nbtPos = CompoundTag()
                nbtPos.putDouble("posX", player.x)
                nbtPos.putDouble("posY", player.y)
                nbtPos.putDouble("posZ", player.z)
                nbtPos.putString("world", player.level().dimension().registry().namespace)
                playerNBT.put("home", nbtPos)
                it.source.sendSuccess({ Component.literal("Successful to set home!") }, false)

                0
            }
        )
    }
}