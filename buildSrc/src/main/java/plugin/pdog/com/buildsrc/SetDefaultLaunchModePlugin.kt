package plugin.pdog.com.buildsrc

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import groovy.util.XmlSlurper
import groovy.util.slurpersupport.NodeChild
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.FileCollection
import java.io.File

class SetDefaultLaunchModePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.plugins.all {
            if (it !is AppPlugin) {
                return@all
            }

            project.extensions.getByType(AppExtension::class.java).run {
                this.applicationVariants.all { variant ->
                    variant.outputs.forEach {

                        val task = it.processManifest
                        task.doLast {
                            find(task.manifestOutputDirectory)
                        }
                    }
                }
            }
        }
    }

    private fun find(file: File) {
        if (file.isDirectory) {
            file.listFiles().forEach { find(it) }
        } else {
            if (file.name == "AndroidManifest.xml") {
                findActivityFromManifestFile(file)
            } else {
                println("other file ${file.name}")
            }
        }
    }


    private fun findActivityFromManifestFile(file: File): List<NodeChild> {
        val emptyLaunchModeActivities = arrayListOf<NodeChild>()

        val androidManifest = XmlSlurper().parse(file)
        val application = androidManifest.children().getAt(1) as NodeChild

        val children = application.children()
        val childrenCount = children.size()
        if (childrenCount <= 0) {
            println("childrenCount <= 0")
            return emptyLaunchModeActivities
        }
        println("-------findActivityFromManifestFile start--------")

        println(file.absolutePath)

        children.forEach {
            val component = it as NodeChild
            if (component.name() != "activity") {
                return@forEach
            }

            val launchMode = component.attributes()["{http://schemas.android.com/apk/res/android}launchMode"]
            val activityName = component.attributes()["{http://schemas.android.com/apk/res/android}name"]
            if (launchMode == null) {
                println("$activityName  not have any launch mode")
                emptyLaunchModeActivities.add(component)
            }

            println("activityName ${activityName}  launchMode ${launchMode}")
        }

        println("-------findActivityFromManifestFile end--------")
        return emptyLaunchModeActivities
    }
}