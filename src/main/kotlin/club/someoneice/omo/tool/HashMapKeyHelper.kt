package club.someoneice.omo.tool

import java.util.*


object HashMapKeyHelper {
    fun getKey(map: HashMap<String, String>, value: String): String? {
        var key: String? = null
        for (getKey in map.keys) {
            if (map[getKey] == value) {
                key = getKey
            }
        }

        return key
    }
}