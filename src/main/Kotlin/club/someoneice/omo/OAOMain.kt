package club.someoneice.omo

import club.someoneice.omo.cofing.PineappleConfig
import club.someoneice.omo.command.*
import club.someoneice.omo.event.PlayerListener
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.common.config.Configuration
import net.minecraftforge.fml.common.FMLCommonHandler
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.event.FMLServerStartingEvent


@Mod(modid = OAOMain.MODID, version = OAOMain.VERSION, dependencies = "required-after:forgelin")
class OAOMain {
    companion object {
        lateinit var config: Configuration
        const val MODID: String = "omo"
        const val VERSION: String = "1.0.1"
    }


    @Mod.Instance
    var instance: OAOMain? = null

    @Mod.EventHandler
    fun init(event: FMLInitializationEvent) {

        // Event
        MinecraftForge.EVENT_BUS.register(PlayerListener())
        FMLCommonHandler.instance().bus().register(PlayerListener())
    }

    @Mod.EventHandler
    fun perInit(event: FMLPreInitializationEvent) {
        config = Configuration(event.suggestedConfigurationFile)
        PineappleConfig()
    }

    @Mod.EventHandler
    fun serverStarting(event: FMLServerStartingEvent) {
        event.registerServerCommand(Tpa())
        event.registerServerCommand(Tpaccept())
        event.registerServerCommand(Tpdeny())
        event.registerServerCommand(Back())
        event.registerServerCommand(Rtp())
    }
}
