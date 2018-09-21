package com.pdog18.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project


class MyPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        println("------------------------")
        println(project.name)
        println("------------------------")
    }
}