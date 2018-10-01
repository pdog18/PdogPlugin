package plugin.pdog

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import groovy.util.slurpersupport.GPathResult
import org.gradle.api.Plugin
import org.gradle.api.Project

class LookTasksPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.plugins.all {
            if (it instanceof AppPlugin) {
                def android = project.extensions.android
                findProcessManifest(project, android)
            }
        }
    }

    static def findProcessManifest(Project project, AppExtension appExtension) {
        appExtension.applicationVariants.all {
            it.outputs.forEach {
                println("${project.name}  ${it.name}  ${it.processManifest}")
                def processManifestTaskInputs = it.processManifest.inputs
                processManifestTaskInputs.files.forEach {
                    if (it.name.endsWith(".xml")) {
                        def xparser = new XmlSlurper()
                        GPathResult androidManifest = xparser.parse(it)
                        println(androidManifest.@package)
                    }
                }
            }
        }
    }
}