package com.quane.little.tools.json

import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.ObjectMapper.{DefaultTypeResolverBuilder, DefaultTyping}
import com.quane.little.language.Expression

/** A [[com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder]] which
  * ensures objects in the [[com.quane.little.language]] package are serialized
  * with type information.
  *
  * @author Sean Connolly
  */
class LanguageTypeResolverBuilder
  extends DefaultTypeResolverBuilder(DefaultTyping.JAVA_LANG_OBJECT) {

  private val expressionPackage = classOf[Expression].getPackage.getName

  override def useForType(t: JavaType): Boolean = {
    if (t.getRawClass.getName.startsWith(expressionPackage)) {
      true
    } else {
      false
    }
  }

}
