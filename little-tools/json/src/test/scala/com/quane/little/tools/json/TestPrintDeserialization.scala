package com.quane.little.tools.json

import com.quane.little.language.data._
import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import com.quane.little.language.Printer

/** Test of [[Printer]] deserialization from JSON.
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestPrintDeserialization
  extends FlatSpec with ShouldMatchers with JSONTestUtilities {

  "json deserializer" should "deserialize print string" in {
    val print = deserialize[Printer]("print_string")
    print should be(new Printer(new TextValue("abc")))
  }
  it should "deserialize print integer" in {
    val print = deserialize[Printer]("print_integer")
    print should be(new Printer(new NumberValue(1234)))
  }
  it should "deserialize print double" in {
    val print = deserialize[Printer]("print_double")
    print should be(new Printer(new NumberValue(12.34)))
  }
  it should "deserialize print boolean true" in {
    val print = deserialize[Printer]("print_boolean_true")
    print should be(new Printer(new TrueFalseValue(true)))
  }
  it should "deserialize print boolean false" in {
    val print = deserialize[Printer]("print_boolean_false")
    print should be(new Printer(new TrueFalseValue(false)))
  }

}
