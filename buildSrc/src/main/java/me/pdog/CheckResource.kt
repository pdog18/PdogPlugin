package me.pdog

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File

class CheckResource : Plugin<Project> {

    private var resourceNameMap: HashMap<String, File> = hashMapOf()
    private var throwableMap: HashMap<String, MutableList<File>> = hashMapOf()
    private var allProject: MutableList<File> = mutableListOf()

    override fun apply(project: Project) {
        project.plugins.all {
            if (it !is AppPlugin) {
                return@all
            }

            project.rootProject.subprojects.forEach {
                allProject.add(it.projectDir)
            }

            project.extensions.getByType(AppExtension::class.java).run {
                this.applicationVariants.all { variant ->
                    variant.mergeResources.doLast {

                        it.inputs.files.forEach {
                            if (isModuleResourceFile(it)) {
                                collectionFile(it, resourceNameMap, throwableMap)
                            }
                        }

                        println(resourceNameMap.size)
                        println(throwableMap.size)

                        throwableMap.forEach {
                            println("${it.key}  ->  ${it.value.map { it.absolutePath }.reduce { acc, s -> "$acc\n$s" }}")
                        }
                        project.buildDir
                    }
                }
            }
        }
    }

    private fun isModuleResourceFile(file: File): Boolean {
        allProject.forEach {
            if (file.absolutePath.startsWith(it.absolutePath)) {
                println("module file  -> ${it.absolutePath}")
                return true
            }
        }
        return false
    }


    private fun collectionFile(file: File, resourceNameMap: HashMap<String, File>, throwableMap: HashMap<String, MutableList<File>>) {
        if (file.isDirectory) {
            file.listFiles().forEach {
                collectionFile(it, resourceNameMap, throwableMap)
            }
        } else {
            val newFile = file
            val fileName = file.name

            // todo  屏蔽同项目中的内容
            val oldFile = resourceNameMap.put(fileName, file)
            // 如果文件已经存在，那么将会记录异常，等待抛出
            oldFile?.let {
                val newList = mutableListOf<File>()
                val list = throwableMap[fileName]

                list?.let {
                    newList.addAll(it)
                }

                newList.add(newFile)

                throwableMap[fileName] = newList
            }
        }
    }
}