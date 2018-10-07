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

            val activityAttributes = project.extensions
                    .create("activityAttributes", ActivityAttributes::class.java)

            project.extensions.getByType(AppExtension::class.java).run {
                this.applicationVariants.all { variant ->
                    variant.outputs.forEach {

                        it.processManifest.run {
                            this.doLast {
                                val file = findManifest(this.manifestOutputDirectory)
                                updateManifest(file, activityAttributes)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun findManifest(file: File): File {
        file.listFiles().forEach {
            if (it.name == "AndroidManifest.xml") {
                return it
            }
        }

        throw NullPointerException("can't find AndroidManifest.xml")
    }


    private fun updateManifest(androidManifestFile: File, activityAttributes: ActivityAttributes) {
        val fileReader = FileReader(androidManifestFile)
        val androidManifestXmlNode = fileReader.use {
            XmlParser().parse(fileReader)
        }

        updateActivity(androidManifestXmlNode, activityAttributes)

        // Write the manifest file
        val pw = PrintWriter(androidManifestFile)
        pw.use {
            XmlNodePrinter(pw).print(androidManifestXmlNode)
        }

        //todo update other component
    }


    private fun updateActivity(androidManifest: Node, activityAttributes: ActivityAttributes) {
        val screenOrientationValue = activityAttributes.screenOrientation
        if (screenOrientationValue != null) {
            updateComponent(androidManifest,
                    "activity",
                    "screenOrientation", screenOrientationValue)
        }

        //todo update other component attributes
    }

    private fun updateComponent(androidManifest: Node, componentName: String, key: String, value: String) {
        val components = androidManifest.getAt(QName("application")).getAt(QName(componentName))
        val attributeKey = QName("http://schemas.android.com/apk/res/android", key, "android")

        components.forEach {
            with(it as Node) {
                if (this.attribute(attributeKey) == null) {
                    this.attributes()[attributeKey] = value
                }
            }
        }
    }
}