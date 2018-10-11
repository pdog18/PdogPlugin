package com.pdog.compiler.attributes

import com.pdog.attributes.KeepAttributes

import java.io.File
import java.util.concurrent.atomic.AtomicBoolean

import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Filer
import javax.annotation.processing.Messager
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic
import kotlin.reflect.KClass

class KeepCompiler : AbstractProcessor() {

    private lateinit var filer: Filer
    private lateinit var options: Map<String, String>
    private lateinit var messager: Messager
    private val once = AtomicBoolean()

    private lateinit var buildDir: File

    @Synchronized
    override fun init(processingEnvironment: ProcessingEnvironment) {
        super.init(processingEnvironment)
        filer = processingEnvironment.filer
        messager = processingEnvironment.messager
        options = processingEnvironment.options

        val buildDirPath = options["buildDir"]
        if (buildDirPath == null) {
            log("""
请确保你在对应的project中的build.gradle中设置了
kapt {
    arguments {
        arg("buildDir", project.buildDir)
    }
}
""".trim(), Diagnostic.Kind.ERROR)
            throw NullPointerException()
        } else {
            buildDir = File(options["buildDir"], "keep-build.txt")
        }
    }

    /**
     * roundEnvironment.getRootElements() 返回有该注解的所有类
     */
    override fun process(set: Set<TypeElement>, roundEnvironment: RoundEnvironment): Boolean {
        log("process  start ->>>>>>>>>>  ")

        if (once.compareAndSet(false, true)) {
            log("once!!")

            log(buildDir.absolutePath)
            if (buildDir.exists()) {
                buildDir.delete()
            }
            buildDir.createNewFile()

            roundEnvironment.rootElements
                    .filter { it[KeepAttributes::class] != null }
                    .map {
                        log("configChanges = ${it[KeepAttributes::class].configChanges}")
                        getTargetClassName(it) to it[KeepAttributes::class].configChanges
                    }
                    .toList()
                    .forEach {
                        buildDir.appendText("${it.first}: ${it.second}\r\n")
                        log("${it.first}:  ${it.second}")
                    }
        }

        log("process  end ->>>>>>>>>>  ")

        return false
    }


    override fun getSupportedAnnotationTypes(): Set<String> {
        return setOf<String>(KeepAttributes::class.java.canonicalName)
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latestSupported()
    }


    private fun log(msg: Any, kind: Diagnostic.Kind = Diagnostic.Kind.OTHER) {
        messager.printMessage(kind, msg.toString())
    }

    private fun getTargetClassName(element: Element): String {
        return "${element.enclosingElement}.${element.simpleName}"
    }

    private inline operator fun <reified T : Annotation> Element.get(annotationType: KClass<T>) =
            getAnnotation(annotationType.java)

}