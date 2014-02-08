package com.quane.little.language.data

import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import java.lang.ClassCastException

@RunWith(classOf[JUnitRunner])
class TestValue extends FunSuite {

  /* Test Boolean Values */

  test("test true as boolean") {
    val t = new Value(true)
    assert(t.asBool == true, "true as a boolean should be true")
  }

  test("test false as boolean") {
    val f = new Value(false)
    assert(f.asBool == false, "false as a boolean should be false")
  }

  test("test true as number") {
    val t = new Value(true)
    assert(t.asNumber == 1, "true as a number should be 1")
  }

  test("test false as number") {
    val f = new Value(false)
    assert(f.asNumber == 0, "false as a number should be 0")
  }

  test("test true as text") {
    val t = new Value(true)
    assert(t.asText == "true", "true as text should be 'true'")
  }

  test("test false as text") {
    val f = new Value(false)
    assert(f.asText == "false", "false as text should be 'false'")
  }

  /* Test Number Values */

  test("test 0 as boolean") {
    val num = new Value(0)
    assert(num.asBool == false, "0 as a boolean should be false")
  }

  test("test 1 as boolean") {
    val num = new Value(1)
    assert(num.asBool == true, "1 as a boolean should be true")
  }

  test("test 137 as boolean") {
    val num = new Value(137)
    intercept[ClassCastException] {
      num.asBool
    }
  }

  test("test number as number") {
    val num = new Value(137)
    assert(num.asNumber == 137, "137 as a number should be 137")
  }

  test("test number as text") {
    val num = new Value(137)
    assert(num.asText == "137", "137 as text should be '137'")
  }

  /* Test String Values */

  test("test 'true' as boolean") {
    val text = new Value("true")
    assert(text.asBool == true, "'true' as a boolean should be true")
  }

  test("test 'false' as boolean") {
    val text = new Value("false")
    assert(text.asBool == false, "'false' as a boolean should be false")
  }

  test("test 'hello world' as boolean") {
    val text = new Value("hello world")
    intercept[ClassCastException] {
      text.asBool
    }
  }

  test("test '137' as number") {
    val text = new Value("137")
    assert(text.asNumber == 137, "'137' as a number should be 137")
  }

  test("test 'true' as number") {
    val text = new Value("true")
    assert(text.asNumber == 1, "'true' as a number should be 1")
  }

  test("test 'false' as number") {
    val text = new Value("false")
    assert(text.asNumber == 0, "'false' as a number should be 0")
  }

  test("test 'hello world' as number") {
    val text = new Value("hello world")
    intercept[ClassCastException] {
      text.asNumber
    }
  }

  test("test 'hello world' as text") {
    val text = new Value("hello world")
    assert(text.asText == "hello world", "'hello world' as text should be 'hello world'")
  }

  test("test if 'hello world' equals 'hello world'") {
    val text = new Value("hello world")
    assert(text == "hello world", "'hello world' should equal 'hello world'")
  }

  test("test if 'hello world' equals 'hello moto'") {
    val text = new Value("hello world")
    assert(text != "hello moto", "'hello world' should not equal 'hello moto'")
  }

  test("test if 'hello world' equals 1234f") {
    val text = new Value("hello world")
    assert(text != 1234f, "'hello world' should not equal 1234f")
  }

  /* Test Nada */

  test("test nada as boolean") {
    val nada = new Nada
    assert(nada.asBool == false, "nada as a boolean should be false")
  }

  test("test nada as number") {
    val nada = new Nada
    assert(nada.asNumber == 0, "nada as a number should be 0")
  }

  test("test nada as text") {
    val nada = new Nada
    assert(nada.asText == "nada", "nada as text should be 'nada'")
  }

}
