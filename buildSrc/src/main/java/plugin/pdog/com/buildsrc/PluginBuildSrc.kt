package plugin.pdog.com.buildsrc

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.nio.charset.Charset

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
        appExtension.applicationVariants.all {
            it.outputs.forEach {
                println("${project.name}  ${it.name}  ${it.processManifest}")
                val processorTask = it.processManifest
                val inputs = processorTask.inputs
                inputs.files.forEach innerForEach@{
                    if (it.name.endsWith("Manifest.xml")) {
                        println("manifest.xml print four lines ------ start")
                        it.readLines(Charset.defaultCharset()).forEachIndexed({ index, s ->
                            if (index < 4) {
                                println(s)
                            }
                        })
                    } else {
                        println(it.absolutePath)
                    }
//
                }
            }
        }
    }
}
