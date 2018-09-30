package plugin.pdog.com.buildsrc

import org.gradle.api.Plugin
import org.gradle.api.Project

class PluginBuildSrc : Plugin<Project> {
    override fun apply(project: Project) {
        project.afterEvaluate {
            println(it.name)
        }
    }
}
