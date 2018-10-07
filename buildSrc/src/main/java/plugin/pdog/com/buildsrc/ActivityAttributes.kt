package plugin.pdog.com.buildsrc

open class ActivityAttributes {
    var screenOrientation: String? = null
    var launchMode: String? = null
    var windowSoftInputMode: String? = null
    var configChanges: String? = null
//    val theme: String? = null

    fun getAllAttributes() = mapOf(
            "screenOrientation" to screenOrientation,
            "launchMode" to launchMode,
            "windowSoftInputMode" to windowSoftInputMode,
            "configChanges" to configChanges)
}