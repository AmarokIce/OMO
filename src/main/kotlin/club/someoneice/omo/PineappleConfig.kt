package club.someoneice.omo

class PineappleConfig {
    companion object {
        var RTP = true
        var PosX: Int = 5000
        var PosZ: Int = 5000
        var OMOTP = false
    }

    init {
        val config = OAOMain.config
        config.load()
        config.addCustomCategoryComment("OMO", "歡迎！這裡是OMO的配置頁面")
        val login = config["Boolean", "Can player use /rtp ?", RTP]
        login.comment = "如果为true，玩家将可以使用/rtp。"
        RTP = login.boolean

        val getPosX = config["Int", "Enter PosZ.", PosX]
        getPosX.comment = "If you wanna to changed random TP X range, changed this.I don't recommend setting more than 20000, too much tp will also consume the server's network"
        PosX = getPosX.getInt(PosX)

        val getPosZ = config["Int", "Enter PosZ.", PosZ]
        getPosZ.comment = "If you wanna to changed random TP Y range, changed this.I don't recommend setting more than 20000, too much tp will also consume the server's network "
        PosZ = getPosZ.getInt(PosZ)

        val omoTp = config["Boolean", "Can player use /omotp ?", OMOTP]
        omoTp.comment = "如果为true，玩家将可以使用/omotp"
        OMOTP = omoTp.getBoolean(OMOTP)

        config.save()
    }
}