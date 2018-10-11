package com.pdog.compiler.attributes

import javax.lang.model.element.Element
import kotlin.reflect.KClass


internal inline operator fun <reified T : Annotation> Element.get(annotationType: KClass<T>) =
        getAnnotation(annotationType.java)

internal inline fun <reified T> Any.cast() = this as T


/**
 * 从 [Element] 上获取指定注解 [annotationType] 的所有键值
 */
internal inline fun <reified T : Annotation> Element.indexAnnotation(annotationType: KClass<T>): Map<String, String> {
    val annotation = this[annotationType]

    return annotationType.java.declaredFields.map {
        it.name to it.get(annotation).cast<String>()
    }.toMap()
}