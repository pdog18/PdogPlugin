package com.pdog.compiler.attributes;

import com.pdog.attributes.KeepAttributes;

import java.util.Collections;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

public class KeepCompiler extends AbstractProcessor {

    private Filer filer;
    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filer = processingEnvironment.getFiler();
        messager = processingEnvironment.getMessager();
    }


    /**
     * roundEnvironment.getRootElements() 返回有该注解的所有类
     */
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        log("process ->>>>>>>>>>  ");
        for (Element element : roundEnvironment.getRootElements()) {
            final KeepAttributes annotation = element.getAnnotation(KeepAttributes.class);
            if (annotation != null) {
                log("" + element.getEnclosingElement() + "." + element.getSimpleName() + annotation.value());
            } else {
                log("annotation is null, element'name = " + element.getEnclosingElement() + "." + element.getSimpleName());
            }
        }

        return false;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(KeepAttributes.class.getCanonicalName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }


    private void log(Object msg) {
        messager.printMessage(Diagnostic.Kind.OTHER, msg.toString());
    }
}
