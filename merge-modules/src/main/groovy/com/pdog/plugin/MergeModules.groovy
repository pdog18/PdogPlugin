package com.pdog.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class MergeModules implements Plugin<Project> {
    @Override
    void apply(Project target) {
        target.rootProject.subprojects.each {
            if (it == target) {
                println("❌${it.name}")
                return
            }

            println("✅${it.name}")
            target.dependencies.implementation it
        }
    }
}