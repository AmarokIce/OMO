package club.someoneice.omo.command

import net.minecraftforge.fml.common.Mod.EventBusSubscriber
import com.mojang.brigadier.CommandDispatcher
import net.minecraft.command.CommandSource
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent
import club.someoneice.omo.command.CommandRegistry
import net.minecraftforge.event.RegisterCommandsEvent
import net.minecraftforge.fml.event.server.FMLServerStoppedEvent

/**
* Register The Command.
*/
@EventBusSubscriber(modid = "omo", bus = EventBusSubscriber.Bus.FORGE)
object CommandRegistry {
    var init = false
    private fun register(event: CommandDispatcher<CommandSource>) {
        val cmd = CommandHelper()
        cmd.TPA(event)
        cmd.TPACCEPT(event)
        cmd.TPDENY(event)
        cmd.TPAHERE(event)
    }

    @SubscribeEvent
    fun register(event: FMLServerAboutToStartEvent) {
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
    fun onServerStopped(event: FMLServerStoppedEvent?) {
        init = false
    }
}