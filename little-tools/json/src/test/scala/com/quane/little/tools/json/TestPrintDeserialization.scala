package com.quane.little.tools.json

import com.quane.little.language.data._
import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import com.quane.little.language.PrintStatement

/** Test of [[PrintStatement]] deserialization from JSON.
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestPrintDeserialization
  extends FlatSpec with ShouldMatchers with JSONTestUtilities {

  "json deserializer" should "deserialize print string" in {
    val print = deserialize[PrintStatement]("print_string")
    print should be(new PrintStatement(new Text("abc")))
  }
  it should "deserialize print integer" in {
    val print = deserialize[PrintStatement]("print_integer")
    print should be(new PrintStatement(new NumberSimple(1234)))
  }
  it should "deserialize print double" in {
    val print = deserialize[PrintStatement]("print_double")
    print should be(new PrintStatement(new NumberDecimal(12.34)))
  }
  it should "deserialize print boolean true" in {
    val print = deserialize[PrintStatement]("print_boolean_true")
    print should be(new PrintStatement(new Bool(true)))
  }
  it should "deserialize print boolean false" in {
    val print = deserialize[PrintStatement]("print_boolean_false")
    print should be(new PrintStatement(new Bool(false)))
  }

}
