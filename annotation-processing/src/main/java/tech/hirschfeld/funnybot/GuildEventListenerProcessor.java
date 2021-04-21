package tech.hirschfeld.funnybot;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;

/**
 * GuildEventListenerProcessor
 */
@SupportedAnnotationTypes({ "tech.hirschfeld.funnybot.GuildEventListener" })
@SupportedSourceVersion(SourceVersion.RELEASE_11)
public class GuildEventListenerProcessor extends AbstractProcessor {

  private Messager messager;

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(GuildEventListener.class);

    for (Element element : annotatedElements) {
      if (element.getKind() == ElementKind.CLASS) {
        try {
          Long.parseUnsignedLong(element.getAnnotation(GuildEventListener.class).guildId());
        } catch (NumberFormatException ex) {
          messager.printMessage(Diagnostic.Kind.ERROR, "The GuildId should be an unsigned 64-Bit Integer", element);
        }
      }
    }

    //return false; // don't claim annotations to allow other processors to process them
    return true;
  }

  @Override
  public synchronized void init(ProcessingEnvironment processingEnv) {
    super.init(processingEnv);
    messager = processingEnv.getMessager();
  }

}