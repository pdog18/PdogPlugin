package me.pdog

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File

class CheckResource : Plugin<Project> {

    private fun findDup(files: List<File>?, map: HashMap<String, String>) {
        files?.forEach {
            if (it.isDirectory ) {
                if (it.name.startsWith("values")){
                    findDup(it.listFiles()?.toList(), map)
                }
            } else {
                val fileName = "${it.parentFile.name}/${it.name}"

                println(fileName)
                map.put(fileName, it.absolutePath)?.let { oldFilePath ->
                    throw Exception("""
                        两个 module 中, 有文件相同了！
                        ${it.absolutePath} 
                        $oldFilePath
                        """)
                }
            }
        }
    }

    override fun apply(project: Project) {
        project.afterEvaluate {
            project.extensions.getByType(AppExtension::class.java).run {
                this.applicationVariants.all { variant ->
                    val map = hashMapOf<String, String>()
                    findDup(variant.allRawAndroidResources.files.toList(), map)
                }
            }
        }
    }
}