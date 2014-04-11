package com.quane.little.tools.json

import com.quane.little.language.Block
import com.quane.little.language.data.Value
import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers

/** Test JSON serialization of [[Block]] expressions.
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestBlockSerialization
  extends FlatSpec with ShouldMatchers with JSONTestUtilities {

  "json serializer" should "serialize block" in {
    val name = "block"
    val block = new Block()
    assertSerialization(getJSON(name), block)
  }

  it should "serialize block with values" in {
    val name = "block_with_values"
    val block = new Block
    block.addStep(Value("abc"))
    block.addStep(Value(123))
    assertSerialization(getJSON(name), block)
  }

}
