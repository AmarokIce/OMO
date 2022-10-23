package club.someoneice.omo

import net.minecraftforge.common.ForgeConfigSpec
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue
import net.minecraftforge.common.ForgeConfigSpec.IntValue


class Config() {
    val builder: ForgeConfigSpec.Builder = ForgeConfigSpec.Builder()
    val common: ForgeConfigSpec = init()

    companion object {
        var rtp: BooleanValue? = null
        var PosX: IntValue? = null
        var PosZ: IntValue? = null
    }

    fun init(): ForgeConfigSpec {
        builder.comment("OMO").push("omo")

        rtp = builder.comment("Can player use RTP").define("RTP", true)
        PosX = builder.comment("RTP max X").defineInRange("Random Max X", 5000, 0, Int.MAX_VALUE)
        PosZ = builder.comment("RTP max Z").defineInRange("Random Max Z", 5000, 0, Int.MAX_VALUE)

        builder.pop()
        return builder.build()
    }
}