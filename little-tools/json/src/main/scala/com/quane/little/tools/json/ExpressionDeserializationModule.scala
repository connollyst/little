package com.quane.little.tools.json

import com.fasterxml.jackson.databind.module.SimpleModule
import com.quane.little.language.Block
import com.quane.little.language.data.Value
import com.quane.little.tools.json.deserializers.{BlockDeserializer, ValueDeserializer}

/** A Jackson [[com.fasterxml.jackson.databind.Module]] which provides custom
  * deserializers for [[com.quane.little.language.Expression]] objects.
  *
  * @author Sean Connolly
  */
class ExpressionDeserializationModule
  extends SimpleModule {

  addDeserializer(classOf[Value], new ValueDeserializer)
  addDeserializer(classOf[Block], new BlockDeserializer)

}
