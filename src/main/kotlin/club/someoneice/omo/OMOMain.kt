package club.someoneice.omo

import club.someoneice.omo.command.CommandRegistry
import club.someoneice.omo.event.PlayerEvent
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import thedarkcolour.kotlinforforge.forge.FORGE_BUS
import thedarkcolour.kotlinforforge.forge.MOD_BUS

@Mod(OMOMain.ID)
object OMOMain {
    const val ID: String = "omo"
    val LOGGER: Logger = LogManager.getLogger()

    init {
        // usage of the KotlinEventBus
        MOD_BUS.addListener(::onClientSetup)
        FORGE_BUS.addListener(::onServerAboutToStart)

        MinecraftForge.EVENT_BUS.register(PlayerEvent)
        MinecraftForge.EVENT_BUS.register(CommandRegistry)
    }

    private fun onClientSetup(event: FMLClientSetupEvent) {
        LOGGER.log(Level.INFO, "Initializing client...")
    }

    private fun onServerAboutToStart(event: FMLDedicatedServerSetupEvent) {
        LOGGER.log(Level.INFO, "Server starting...")
        // MinecraftForge.EVENT_BUS.register(PlayerEvent)

    }
}