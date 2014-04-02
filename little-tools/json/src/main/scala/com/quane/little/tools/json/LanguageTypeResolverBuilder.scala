package com.quane.little.tools.json

import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.ObjectMapper.{DefaultTypeResolverBuilder, DefaultTyping}
import com.quane.little.language.Expression

/**
 *
 *
 * @author Sean Connolly
 */
class LanguageTypeResolverBuilder
  extends DefaultTypeResolverBuilder(DefaultTyping.NON_FINAL) {

  private val expressionPackage = classOf[Expression].getPackage.getName

  override def useForType(t: JavaType): Boolean = {
    if (t.getRawClass.getName.startsWith(expressionPackage)) {
      true
    } else {
      false
    }
  }
}
