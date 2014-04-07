package com.quane.little.tools.json

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule

class LittleJSONMapper extends ObjectMapper {

  registerModule(new DefaultScalaModule)
  setDefaultTyping(new LanguageTypeResolverBuilder)

}
