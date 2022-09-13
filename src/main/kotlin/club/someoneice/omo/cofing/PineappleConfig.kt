package club.someoneice.omo.cofing

import club.someoneice.omo.OAOMain
import club.someoneice.omo.event.PlayerListener

class PineappleConfig {


    companion object {
        private var player = PlayerListener.playerName
        var LoginMsg = "歡迎${player}加入伺服器 !"
        var OutMsg = "${player}離開了伺服器！"
    }

    init {
        val config = OAOMain.config
        config.load()
        config.addCustomCategoryComment("OMO", "歡迎！這裡是OMO的配置頁面")
        val login = config["String", "加入世界時的提示", LoginMsg]
        login.comment = "當玩家加入世界，會广播這個提示。"
        LoginMsg = login.string

        val out = config["String", "断开链接時的提示", OutMsg]
        out.comment = "當玩家断开链接，會广播這個提示。"
        OutMsg = out.string
        config.save()
    }
}