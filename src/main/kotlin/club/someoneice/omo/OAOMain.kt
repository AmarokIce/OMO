package club.someoneice.omo

import alexsocol.patcher.KotlinAdapter
import club.someoneice.omo.command.*
import club.someoneice.omo.event.PlayerListener
import cpw.mods.fml.common.FMLCommonHandler
import cpw.mods.fml.common.Mod
import cpw.mods.fml.common.Mod.EventHandler
import cpw.mods.fml.common.event.FMLInitializationEvent
import cpw.mods.fml.common.event.FMLPreInitializationEvent
import cpw.mods.fml.common.event.FMLServerStartingEvent
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.common.config.Configuration


@Mod(modid = OAOMain.MODID, version = OAOMain.VERSION, modLanguageAdapter = KotlinAdapter.className)
class OAOMain {
    companion object {
        lateinit var config: Configuration
        const val MODID: String = "omo"
        const val VERSION: String = "1.0.1"
    }

    @EventHandler
    fun init(event: FMLInitializationEvent) {

        // Event
        MinecraftForge.EVENT_BUS.register(PlayerListener())
        FMLCommonHandler.instance().bus().register(PlayerListener())
    }

    @EventHandler
    fun perInit(event: FMLPreInitializationEvent) {
        config = Configuration(event.suggestedConfigurationFile)
        PineappleConfig()
    }

    @EventHandler
    fun serverStarting(event: FMLServerStartingEvent) {
        event.registerServerCommand(Tpa())
        event.registerServerCommand(Tpaccept())
        event.registerServerCommand(Tpdeny())
        event.registerServerCommand(Back())
        event.registerServerCommand(SetPort())
        event.registerServerCommand(Backport())
        event.registerServerCommand(Rtp())
        event.registerServerCommand(SetHome())
        event.registerServerCommand(Home())
        if (PineappleConfig.OMOTP) event.registerServerCommand(OMOTp())
    }
}
