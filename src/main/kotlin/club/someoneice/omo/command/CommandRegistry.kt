package club.someoneice.omo.command

import club.someoneice.omo.Config
import com.mojang.brigadier.CommandDispatcher
import net.minecraft.commands.CommandSourceStack
import net.minecraftforge.event.RegisterCommandsEvent
import net.minecraftforge.event.server.ServerStartedEvent
import net.minecraftforge.event.server.ServerStoppedEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod.EventBusSubscriber

/**
* Register The Command.
*/
@EventBusSubscriber(modid = "omo", bus = EventBusSubscriber.Bus.FORGE)
object CommandRegistry {
    var init = false
    private fun register(event: CommandDispatcher<CommandSourceStack>) {
        val cmd = CommandHelper()
        cmd.TPA(event)
        cmd.TPACCEPT(event)
        cmd.TPDENY(event)
        cmd.TPAHERE(event)
        cmd.BACK(event)
        cmd.RTP(event)
        cmd.HOME(event)
        cmd.SETHOME(event)
        if (Config.omotp!!.get()) cmd.OMOTP(event)
    }

    @SubscribeEvent
    fun register(event: ServerStartedEvent) {
        if (!init) {
            register(event.server.commands.dispatcher)
            init = true
        }
    }

    @SubscribeEvent
    fun register(event: RegisterCommandsEvent) {
        if (init) register(event.dispatcher)
    }

    @SubscribeEvent
    fun onServerStopped(event: ServerStoppedEvent?) {
        init = false
    }
}