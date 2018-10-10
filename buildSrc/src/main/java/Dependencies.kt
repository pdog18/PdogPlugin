import Versions.constraint_layout_version

object Versions {
    const val min_sdk = 21
    const val target_sdk = 26
    const val compile_sdk = 26
    const val version_code = 1
    const val version_name = "1.0"

    internal const val android_gradle_plugin = "3.0.1"
    internal const val kotlin = "1.2.41"
    internal const val support_lib = "26.1.0"
    internal const val retrofit = "2.3.0"
    internal const val rxjava = "2.1.9"
    internal const val constraint_layout_version = "1.1.3"
}

object Deps {
    const val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    const val kotlin_gradle_plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val app_compat_v7 = "com.android.support:appcompat-v7:${Versions.support_lib}"
    const val support_v4 = "com.android.support:support-v4:${Versions.support_lib}"
    const val design = "com.android.support:design:${Versions.support_lib}"
    const val recyclerview_v7 = "com.android.support:recyclerview-v7:${Versions.support_lib}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofit_rxjava = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}"
    const val rxjava = "io.reactivex.rxjava2:rxjava:${Versions.rxjava}"
    const val android_gradle_plugin = "com.android.tools.build:gradle:${Versions.android_gradle_plugin}"
    const val constraint_layout = "com.android.support.constraint:constraint-layout:$constraint_layout_version"
}