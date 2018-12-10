package com.pdog.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class MergeModules implements Plugin<Project> {

    @Override
    void apply(Project target) {
        target.afterEvaluate {
            def mergeMap = []
            def skipMap = []
            def missMap = []
            target.rootProject.subprojects.each { project ->
                try {
                    if (!project.property('application').toBoolean()) {
                        mergeMap << project
                    } else {
                        skipMap << project
                    }
                } catch (ignored) {
                    missMap << project
                }
            }

            println("mergeMap ->  ${mergeMap}")
            println("skipMap -> ${skipMap}")
            println("missMap -> ${missMap}")

            mergeMap.each { project ->
                target.dependencies.implementation project.project
            }
        }
    }
}