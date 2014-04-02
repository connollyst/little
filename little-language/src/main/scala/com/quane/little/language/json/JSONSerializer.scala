package com.quane.little.language.json

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.`type`.TypeReference
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.databind.{JsonNode, DeserializationContext, JsonDeserializer, ObjectMapper}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.quane.little.language.data.Value
import java.lang.reflect.{Type, ParameterizedType}

object JSONSerializer {

  val mapper: ObjectMapper = new ObjectMapper()
  mapper.registerModule(DefaultScalaModule)
  mapper.registerModule(new LittleLanguageModule)

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

class LittleLanguageModule
  extends SimpleModule {

  addDeserializer(classOf[Value], new ValueDeserializer)

}

class ValueDeserializer
  extends JsonDeserializer[Value] {

  override def deserialize(jsonParser: JsonParser, context: DeserializationContext): Value = {
    val oc = jsonParser.getCodec
    val node: JsonNode = oc.readTree(jsonParser)
    val value = node.get("primitive")
    if (value.isBoolean) {
      Value(value.asBoolean())
    } else if (value.isInt) {
      Value(value.asInt())
    } else if (value.isDouble) {
      Value(value.asDouble())
    } else {
      Value(value.asText())
    }
  }

}