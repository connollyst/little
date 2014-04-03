package com.quane.little.tools.json

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.ObjectMapper.{DefaultTypeResolverBuilder, DefaultTyping}
import com.quane.little.language.Expression
import com.quane.little.tools.json.LanguageTypeResolverBuilder._

/** A [[com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder]] which
  * ensures objects in the [[com.quane.little.language]] package are serialized
  * with type information.
  *
  * @author Sean Connolly
  */
class LanguageTypeResolverBuilder
  extends DefaultTypeResolverBuilder(DefaultTyping.NON_FINAL) {

  init(JsonTypeInfo.Id.CLASS, null)
  inclusion(JsonTypeInfo.As.PROPERTY)

  /** Method called to check if the default type handler should be used for
    * given type.
    *
    * Only classes in the [[com.quane.little.language]] package are serialized
    * with type information.
    *
    * @param t the type of object being serialized
    * @return `true` if type information should be included
    */
  override def useForType(t: JavaType): Boolean =
    t.getRawClass.getName.startsWith(LANGUAGE_PACKAGE)

}

object LanguageTypeResolverBuilder {

  val LANGUAGE_PACKAGE = classOf[Expression].getPackage.getName

}
