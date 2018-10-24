package me.pdog

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File

class CheckResource : Plugin<Project> {
    override fun apply(project: Project) {
        project.plugins.all {
            if (it !is AppPlugin) {
                return@all
            }

            project.extensions.getByType(AppExtension::class.java).run {
                this.applicationVariants.all { variant ->
                    variant.mergeResources.doLast {
                        it.inputs.files.forEach {
                            printFileName(it)
                        }
                    }
                }
            }
        }
    }

    private fun printFileName(file: File) {
        if (file.isDirectory) {
            file.listFiles().forEach {
                printFileName(it)
            }
        } else {
            println(file.name)
        }

    }
}