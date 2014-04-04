package com.quane.little.tools.json

import com.quane.little.language.Block
import com.quane.little.language.data.Value
import com.quane.little.tools.json.JSONTestUtilities._
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.scalatest.mock.MockitoSugar

/** Test JSON serialization of [[Block]] expressions.
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestBlockSerialization
  extends FunSuite
  with MockitoSugar {

  test("serialize block") {
    val name = "block"
    val block = new Block()
    assertSerialization(getJSON(name), block)
  }

  test("serialize block with values") {
    val name = "block_with_values"
    val block = new Block
    block.addStep(Value("abc"))
    block.addStep(Value(123))
    assertSerialization(getJSON(name), block)
  }

}
