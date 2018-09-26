package com.pdog18.plugin

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.api.ApplicationVariant
import com.android.build.gradle.api.BaseVariant
import groovy.util.Node
import groovy.util.XmlParser
import groovy.util.XmlSlurper
import org.gradle.api.DomainObjectSet
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader


class MyPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.afterEvaluate {
            println("------------------------")
            println(project.name)
            project.plugins.all {
                when (it) {
                    is AppPlugin -> {
                        println("is AppPlugin")
                        project.extensions.getByType(AppExtension::class.java).run {
                            printManifest(project, applicationVariants)
                        }
                    }
                }
            }
            println("------------------------")
        }
    }

    private fun printManifest(project: Project, variants: DomainObjectSet<ApplicationVariant>) {
        variants.all { variant ->
            println(variant.name)
//            val outputDir = project.buildDir.resolve(
//                    "generated/source/pdog-manifest/${variant.dirName}")
//
//            val task = project.tasks.create("manifest${variant.name.capitalize()}")
//            task.outputs.dir(outputDir)

            getManifestFile(variant)
        }
    }

    private fun getManifestFile(variant: BaseVariant) {
        val list = variant.sourceSets.map { it.manifestFile }
        list.forEach {
            if (it.exists()) {
                val reader = InputStreamReader(FileInputStream(it))
                val xml = XmlParser().parse(reader)
                xml.attributes().forEach {
                    xml.children().forEach {
                        val node = it as Node
                        node.children().forEach {
                            val node = it as Node
                            node.attributes().forEach {
                                println(" key = ${it.key}  value = ${it.value}")
                            }
                        }
                    }
                }
            }
        }
    }

    // Parse the variant's main manifest file in order to get the package id which is used to create
    // R.java in the right place.
    private fun getPackageName(variant: BaseVariant): String {
        val slurper = XmlSlurper(false, false)
        val list = variant.sourceSets.map { it.manifestFile }

        list.forEach {
            if (it.exists()) {
                it.readLines().forEach {
                    println(it)
                }
            }
        }

        // According to the documentation, the earlier files in the list are meant to be overridden by the later ones.
        // So the first file in the sourceSets list should be main.
        val result = slurper.parse(list[0])
        return result.getProperty("@package").toString()
    }
}