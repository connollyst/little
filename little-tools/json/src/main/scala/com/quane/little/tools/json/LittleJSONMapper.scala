package com.quane.little.tools.json

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.quane.little.language.data.ValueType

class LittleJSONMapper extends ObjectMapper {

  registerModule(new DefaultScalaModule)
  setDefaultTyping(new LanguageTypeResolverBuilder)

}
