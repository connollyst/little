package com.quane.little.language.data

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TestValue extends FunSuite {

  /* Test Boolean Values */

  test("true as boolean") {
    val t = Value(true)
    assert(t.asBool == true, "true as a boolean should be true")
  }

  test("false as boolean") {
    val f = Value(false)
    assert(f.asBool == false, "false as a boolean should be false")
  }

  test("true as number") {
    val t = Value(true)
    assert(t.asNumber == 1, "true as a number should be 1")
  }

  test("false as number") {
    val f = Value(false)
    assert(f.asNumber == 0, "false as a number should be 0")
  }

  test("true as text") {
    val t = Value(true)
    assert(t.asText == "true", "true as text should be 'true'")
  }

  test("false as text") {
    val f = Value(false)
    assert(f.asText == "false", "false as text should be 'false'")
  }

  /* Test Number Values */

  test("0 as boolean") {
    val num = Value(0)
    assert(num.asBool == false, "0 as a boolean should be false")
  }

  test("1 as boolean") {
    val num = Value(1)
    assert(num.asBool == true, "1 as a boolean should be true")
  }

  test("137 as boolean") {
    val num = Value(137)
    assert(num.asBool == false, "137 as a boolean should be false")
  }

  test("number as number") {
    val num = Value(137)
    assert(num.asNumber == 137, "137 as a number should be 137")
  }

  test("number as text") {
    val num = Value(137)
    assert(num.asText == "137", "137 as text should be '137'")
  }

  /* Test String Values */

  test("'true' as boolean") {
    val text = Value("true")
    assert(text.asBool == true, "'true' as a boolean should be true")
  }

  test("'false' as boolean") {
    val text = Value("false")
    assert(text.asBool == false, "'false' as a boolean should be false")
  }

  test("'hello world' as boolean") {
    val text = Value("hello world")
    intercept[ClassCastException] {
      text.asBool
    }
  }

  test("'137' as number") {
    val text = Value("137")
    assert(text.asNumber == 137, "'137' as a number should be 137")
  }

  test("'true' as number") {
    val text = Value("true")
    assert(text.asNumber == 1, "'true' as a number should be 1")
  }

  test("'false' as number") {
    val text = Value("false")
    assert(text.asNumber == 0, "'false' as a number should be 0")
  }

  test("'hello world' as number") {
    val text = Value("hello world")
    intercept[ClassCastException] {
      text.asNumber
    }
  }

  test("'hello world' as text") {
    val text = Value("hello world")
    assert(text.asText == "hello world", "'hello world' as text should be 'hello world'")
  }

  test("if 'hello world' equals 'hello world'") {
    val text = Value("hello world")
    assert(text == "hello world", "'hello world' should equal 'hello world'")
  }

  test("if 'hello world' equals 'hello moto'") {
    val text = Value("hello world")
    assert(text != "hello moto", "'hello world' should not equal 'hello moto'")
  }

  test("if 'hello world' equals 1234f") {
    val text = Value("hello world")
    assert(text != 1234f, "'hello world' should not equal 1234f")
  }

  /* Test Nada */

  test("nada as boolean") {
    val nada = new Nada
    assert(nada.asBool == false, "nada as a boolean should be false")
  }

  test("nada as number") {
    val nada = new Nada
    assert(nada.asNumber == 0, "nada as a number should be 0")
  }

  test("nada as text") {
    val nada = new Nada
    assert(nada.asText == "nada", "nada as text should be 'nada'")
  }

}
