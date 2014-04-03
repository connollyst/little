package com.quane.little.tools.json.deserializers

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.{DeserializationContext, JsonDeserializer}
import com.quane.little.language.Block

/**
 *
 *
 * @author Sean Connolly
 */
class BlockDeserializer extends JsonDeserializer[Block] {

  override def deserialize(jp: JsonParser, ctxt: DeserializationContext): Block = {
    new Block()
  }

}
