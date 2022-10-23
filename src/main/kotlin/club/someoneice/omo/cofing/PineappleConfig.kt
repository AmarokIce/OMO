package club.someoneice.omo.cofing

import club.someoneice.omo.OAOMain
import club.someoneice.omo.event.PlayerListener

class PineappleConfig {
    companion object {
        var RTP = true
        var PosX: Int = 5000
        var PosZ: Int = 5000
    }

    init {
        val config = OAOMain.config
        config.load()
        config.addCustomCategoryComment("OMO", "歡迎！這裡是OMO的配置頁面")
        val login = config["Boolean", "是否允许玩家使用RTP", RTP]
        login.comment = "如果为true，玩家将可以使用/rtp。"
        RTP = login.boolean

        val getPosX = config["Int", "最大X距离.", PosX]
        getPosX.comment = " "
        PosX = getPosX.getInt(PosX)

        val getPosZ = config["Int", "Enter PosZ.", PosZ]
        getPosZ.comment = "If you wanna to changed random TP Y range, changed this.I don't recommend setting more than 20000, too much tp will also consume the server's network "
        PosZ = getPosZ.getInt(PosZ)

        config.save()
    }
}