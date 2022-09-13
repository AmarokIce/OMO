package club.someoneice.omo

import club.someoneice.omo.cofing.PineappleConfig
import club.someoneice.omo.command.Back
import club.someoneice.omo.command.Tpa
import club.someoneice.omo.command.Tpaccept
import club.someoneice.omo.command.Tpdeny
import club.someoneice.omo.event.PlayerListener
import cpw.mods.fml.common.FMLCommonHandler
import cpw.mods.fml.common.Mod
import cpw.mods.fml.common.Mod.EventHandler
import cpw.mods.fml.common.event.FMLInitializationEvent
import cpw.mods.fml.common.event.FMLPreInitializationEvent
import cpw.mods.fml.common.event.FMLServerStartingEvent
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.common.config.Configuration


@Mod(modid = OAOMain.MODID, version = OAOMain.VERSION, dependencies = "required-after:legacymckotlin")
class OAOMain {
    companion object {
        lateinit var config: Configuration
        const val MODID: String = "omo"
        const val VERSION: String = "1.0.1"
    }


    @Mod.Instance
    var instance: OAOMain? = null

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

    }
}
