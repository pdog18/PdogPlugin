package com.pdog.compiler.attributes

import com.pdog.attributes.KeepAttributes
import java.awt.DisplayMode

import java.io.File
import java.io.FileWriter
import java.io.IOException
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

    private var filer: Filer? = null
    private lateinit var messager: Messager
    private val once = AtomicBoolean()

    @Synchronized
    override fun init(processingEnvironment: ProcessingEnvironment) {
        super.init(processingEnvironment)
        filer = processingEnvironment.filer
        messager = processingEnvironment.messager
    }

    /**
     * roundEnvironment.getRootElements() 返回有该注解的所有类
     */
    override fun process(set: Set<TypeElement>, roundEnvironment: RoundEnvironment): Boolean {
        log("process  start ->>>>>>>>>>  ")

        if (once.compareAndSet(false, true)) {
            log("once!!")
            val rootProject = File("").absoluteFile
            val build = File(rootProject, "build/keep-build.txt")
            build.createNewFile()

            roundEnvironment.rootElements
                    .filter { it[KeepAttributes::class] != null }
                    .map { getTargetClassName(it) to it[KeepAttributes::class].value }
                    .toList()
                    .forEach {
                        build.appendText("${it.first}: ${it.second}\r\n")
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
