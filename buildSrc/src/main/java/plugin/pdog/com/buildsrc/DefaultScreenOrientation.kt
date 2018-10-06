package plugin.pdog.com.buildsrc

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import groovy.util.Node
import groovy.util.XmlNodePrinter
import groovy.util.XmlParser
import groovy.xml.QName
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.*

class DefaultScreenOrientation : Plugin<Project> {
    override fun apply(project: Project) {
        project.plugins.all {
            if (it !is AppPlugin) {
                return@all
            }

            project.extensions.getByType(AppExtension::class.java).run {
                this.applicationVariants.all { variant ->
                    variant.outputs.forEach {

                        it.processManifest.run {
                            this.doLast {
                                findManifest(this.manifestOutputDirectory)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun findManifest(file: File) {
        if (file.isDirectory) {
            file.listFiles().forEach { findManifest(it) }
        } else if (file.name == "AndroidManifest.xml") {
            updateManifest(file)
        }
    }


    private fun updateManifest(androidManifest: File) {
        val xml = XmlParser().parse(FileReader(androidManifest))
        val application = xml.getAt(QName("application"))
        val activities = application.getAt(QName("activity"))

        val uri = "http://schemas.android.com/apk/res/android"
        val screenOrientationQName = QName(uri, "screenOrientation", "android")

        activities.forEach {
            with(it as Node) {
                if (this.attribute(screenOrientationQName) == null) {
                    this.attributes()[screenOrientationQName] = "portrait"
                }
            }
        }

        // Write the manifest file
        val pw = PrintWriter(androidManifest)
        val printer = XmlNodePrinter(pw)
        printer.print(xml)
    }
}