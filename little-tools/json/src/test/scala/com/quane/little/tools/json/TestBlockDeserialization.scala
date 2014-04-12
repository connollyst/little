package com.quane.little.tools.json

import com.quane.little.language.Block
import com.quane.little.language.data.Value
import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers

/** Test of JSON deserialization.
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestBlockDeserialization
  extends FlatSpec with ShouldMatchers with JSONTestUtilities {

  "json deserializer" should "deserialize block" in {
    val name = "block"
    val block = deserialize[Block](name)
    block should be(new Block())
  }

  it should "deserialize block with values" in {
    val name = "block_with_values"
    val block = deserialize[Block](name)
    block should be(new Block() {
      addStep(Value("abc"))
      addStep(Value(123))
    })
  }

}
