package me.pdog

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File

class CheckResource : Plugin<Project> {

    private val allMap = mutableListOf<Map<String, File>>()

    private val gradleCachePath = "/Users/pdog/.gradle/caches/"


    private fun onApply(project: Project) {
        project.extensions.getByType(AppExtension::class.java).run {
            this.applicationVariants.all { variant ->

                val task = project.tasks.findByName("merge${variant.name.capitalize()}Resources")
                task?.doFirst { it ->
                    it.inputs.files.forEach {
                        // 为每个 inputs 找到 对应的 File Set , 然后添加到 inputs 中
                        val map = findResourceMap(it)
                        allMap.add(map)
                    }

                    checkResourceByAllMap(allMap)
                }
            }
        }
    }

    override fun apply(project: Project) {
        project.plugins.all {
            when (it) {
                is AppPlugin -> onApply(project)
                is LibraryPlugin -> onApply(project)
            }
        }
    }

    private fun checkResourceByAllMap(allSet: MutableList<Map<String, File>>) {
        val map = mutableMapOf<String, File>()
        allSet.forEach {
            it.forEach { name, newFile ->
                val oldFile = map.put(name, newFile)
                if (oldFile != null) {  // ops~! 发现相同的文件了
                    throw Exception("两个module 中有文件相同了！\n ${oldFile.absolutePath} \n ${newFile.absolutePath}")
                }
            }
        }
    }

    private fun findResourceMap(file: File?): Map<String, File> {
        val map = mutableMapOf<String, File>()
        // todo 这里应该通过 project 中的 res 路径来判断，另外如果有自定的res的话 ，那更加复杂
        if (file == null || file.absolutePath.startsWith(gradleCachePath, true)) {
            return map
        } else {
            collectionFileToMap(file, map)
        }

        return map
    }

    private fun collectionFileToMap(file: File?, map: MutableMap<String, File>) {
        if (file == null) {
            return
        }
        if (file.isDirectory) {
            file.listFiles().forEach { it ->
                collectionFileToMap(it, map)
            }
        } else {
            map[file.name] = file
        }
    }
}