package com.quane.little.tools.json

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule

class JSONSerializer {

  val mapper = new ObjectMapper
  mapper.registerModule(new DefaultScalaModule)
  mapper.setDefaultTyping(new LanguageTypeResolverBuilder)

  def serialize(value: Any): String = mapper.writeValueAsString(value)

}