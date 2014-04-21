package com.quane.little.tools.json

import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import com.quane.little.language.PrintStatement
import com.quane.little.language.data._

/** Test [[PrintStatement]] serialization to JSON.
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestPrintSerialization
  extends FlatSpec with ShouldMatchers with JSONTestUtilities {

  "JSON serializer" should "serialize print string" in {
    val name = "print_string"
    val v = new PrintStatement(new Text("abc"))
    assertSerialization(getJSON(name), v)
  }
  it should "serialize print integer" in {
    val name = "print_integer"
    val v = new PrintStatement(new NumberSimple(1234))
    assertSerialization(getJSON(name), v)
  }
  it should "serialize print double" in {
    val name = "print_double"
    val v = new PrintStatement(new NumberDecimal(12.34d))
    assertSerialization(getJSON(name), v)
  }
  it should "serialize print boolean true" in {
    val name = "print_boolean_true"
    val v = new PrintStatement(new Bool(true))
    assertSerialization(getJSON(name), v)
  }
  it should "serialize print boolean false" in {
    val name = "print_boolean_false"
    val v = new PrintStatement(new Bool(false))
    assertSerialization(getJSON(name), v)
  }

}
