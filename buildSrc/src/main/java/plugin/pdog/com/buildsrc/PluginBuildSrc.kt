package plugin.pdog.com.buildsrc

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.tasks.MergeManifests
import groovy.util.XmlSlurper
import groovy.util.slurpersupport.NodeChild
import groovy.util.slurpersupport.NodeChildren
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.FileCollection
import org.gradle.api.internal.tasks.DefaultTaskInputs
import java.io.File
import java.util.logging.Logger

class PluginBuildSrc : Plugin<Project> {
    override fun apply(project: Project) {
        project.plugins.all {
            when (it) {
                is AppPlugin -> {
                    project.extensions.getByType(AppExtension::class.java).run {
                        findProcessManifest(project, this)
                    }
                }
            }
        }
    }

    private fun findProcessManifest(project: Project, appExtension: AppExtension) {
        appExtension.applicationVariants.all { variant ->
            variant.outputs.forEach {
                val mergeManifests = it.processManifest as MergeManifests
                mergeManifests.doFirst {
                    val allEmptyLaunchModeActivityFiles = findAllEmptyLaunchModeActivityFiles(it.inputs.files)
                    allEmptyLaunchModeActivityFiles.forEach {
                        println(it.key)
                        it.value.forEach {
                            val nodeName = it.attributes()["{http://schemas.android.com/apk/res/android}name"]
                            println(nodeName)
                        }
                    }
                }
            }
        }
    }

    private fun findAllEmptyLaunchModeActivityFiles(file: FileCollection): Map<File, List<NodeChild>> {
        val map = hashMapOf<File, List<NodeChild>>()
        file.forEach {
            if (it.isDirectory) {
                return@forEach
            }

            val emptyLaunchModeActivitiesOnFile = findActivityFromManifestFile(it)
            if (emptyLaunchModeActivitiesOnFile.isNotEmpty()) {
                map[it] = emptyLaunchModeActivitiesOnFile
            }
        }
        return map
    }

    private fun findActivityFromManifestFile(file: File): List<NodeChild> {
        val emptyLaunchModeActivities = arrayListOf<NodeChild>()

        val androidManifest = XmlSlurper().parse(file)
        val application = androidManifest.children().getAt(0) as NodeChild

        val children = application.children()
        val childrenCount = children.size()
        if (childrenCount <= 0) {
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
        }

        println("-------findActivityFromManifestFile end--------")
        return emptyLaunchModeActivities
    }
}
