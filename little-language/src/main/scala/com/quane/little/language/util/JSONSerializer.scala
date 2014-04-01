package com.quane.little.language.util

import com.fasterxml.jackson.core.`type`.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.introspect.{Annotated, AnnotatedMember, JacksonAnnotationIntrospector}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import java.lang.reflect.{Type, ParameterizedType}
import scala.collection.JavaConversions._

/** Wra
  *
  * @author Sean Connolly
  */
object JSONSerializer {

  val mapper: ObjectMapper = new ObjectMapper()
  mapper.registerModule(DefaultScalaModule)
  mapper.setAnnotationIntrospector(new AnnotationIntrospector)

  def serialize(value: Any): String = {
    import java.io.StringWriter
    val writer = new StringWriter()
    mapper.writeValue(writer, value)
    writer.toString
  }

  def deserialize[T: Manifest](value: String): T =
    mapper.readValue(value, typeReference[T])

  private[this] def typeReference[T: Manifest] = new TypeReference[T] {
    override def getType = typeFromManifest(manifest[T])
  }

  private[this] def typeFromManifest(m: Manifest[_]): Type = {
    if (m.typeArguments.isEmpty) {
      m.erasure
    }
    else new ParameterizedType {
      def getRawType = m.erasure

      def getActualTypeArguments = m.typeArguments.map(typeFromManifest).toArray

      def getOwnerType = null
    }
  }

}

class AnnotationIntrospector
  extends JacksonAnnotationIntrospector {

  override def hasIgnoreMarker(m: AnnotatedMember): Boolean = {
    println("Looking for ignore marker: " + m)
    hasTransientAnnotation(m)
    super.hasIgnoreMarker(m)
  }

  private def hasTransientAnnotation(a: Annotated): Boolean = {
    a.annotations() foreach {
      annotation => println("   " + annotation)
    }
    false
  }

}