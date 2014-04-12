package com.quane.little.tools.json

import com.quane.little.language.data._
import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers

/** Test of JSON deserialization.
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestValueDeserialization
  extends FlatSpec with ShouldMatchers with JSONTestUtilities {

  "json deserializer" should "deserialize value blank" in {
    val value = deserialize[Value]("value_blank")
    value should be(new Text(""))
  }
  it should "deserialize value string" in {
    val value = deserialize[Value]("value_string")
    value should be(new Text("abc"))
  }
  it should "deserialize value integer" in {
    val value = deserialize[Value]("value_integer")
    value should be(new NumberSimple(1234))
  }
  it should "deserialize value double" in {
    val value = deserialize[Value]("value_double")
    value should be(new NumberDecimal(12.34))
  }
  it should "deserialize value boolean true" in {
    val value = deserialize[Value]("value_boolean_true")
    value should be(new Bool(true))
  }
  it should "deserialize value boolean false" in {
    val value = deserialize[Value]("value_boolean_false")
    value should be(new Bool(false))
  }

}
