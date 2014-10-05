package com.quane.little.language

import com.quane.little.language.data.{Value, Variable}
import org.junit.runner.RunWith
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.mock.MockitoSugar
import org.scalatest.{WordSpec, FunSuite}
import org.scalatest.junit.JUnitRunner

/** Tests for the [[com.quane.little.language.Scope]] trait.
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestScope extends WordSpec with ShouldMatchers with MockitoSugar  {

  "Scope" should {
    "fetch value from parent scope" in {
      // Given
      val fun1 = new Scope(None)
      val fun2 = new Scope(fun1)
      fun1.save(new Variable("Obj1", Value("A")))
      fun2.save(new Variable("Obj2", Value("B")))
      // When
      val obj1 = fun2.fetch("Obj1")
      val obj2 = fun2.fetch("Obj2")
      // Then
      val obj1Value = obj1.value.primitive
      val obj2Value = obj2.value.primitive
      assert(obj1Value == "A", "expected 'Obj1' to be 'A' but is: " + obj1Value)
      assert(obj2Value == "B", "expected 'Obj2' to be 'B' but is: " + obj2Value)
    }
    "update value in parent scope" in {
      // Given
      val fun1 = new Scope(None)
      fun1.save(new Variable("Obj1", Value("A")))
      val fun2 = new Scope(fun1)
      fun2.save(new Variable("Obj1", Value("B")))
      // When
      val obj1 = fun1.fetch("Obj1")
      // Then
      assert(obj1.value.asText == "B", "expected 'Obj1' in upper scope to be 'B'")
    }
    "report variable is defined" in {
      // Given
      val fun1 = new Scope(None)
      fun1.save(new Variable("Obj1", Value("A")))
      // When
      val defined = fun1.contains("Obj1")
      // Then
      assert(defined, "expected 'Obj1' to be defined")
    }
    "report variable is NOT defined" in {
      // Given
      val fun1 = new Scope(None)
      fun1.save(new Variable("Obj2", Value("A")))
      // When
      val defined = fun1.contains("Obj1")
      // Then
      assert(!defined, "didn't expect 'Obj1' to be defined")
    }
  }

}