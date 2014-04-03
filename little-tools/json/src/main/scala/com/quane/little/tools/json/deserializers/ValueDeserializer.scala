package com.quane.little.tools.json.deserializers

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode
import com.quane.little.language.data.Value

class ValueDeserializer
  extends JsonDeserializer[Value] {

  override def deserialize(jsonParser: JsonParser, context: DeserializationContext): Value = {
    val oc = jsonParser.getCodec
    val node: JsonNode = oc.readTree(jsonParser)
    // TODO should be based on 'type'
    val value = node.get("primitive")
    if (value.isBoolean) {
      Value(value.asBoolean)
    } else if (value.isInt) {
      Value(value.asInt)
    } else if (value.isDouble) {
      Value(value.asDouble)
    } else {
      Value(value.asText)
    }
  }

}