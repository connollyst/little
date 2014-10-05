package com.quane.little.language

import com.quane.little.language.data.ValueType
import org.junit.runner.RunWith
import org.scalatest.WordSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.mock.MockitoSugar

@RunWith(classOf[JUnitRunner])
class TestRuntime extends WordSpec with ShouldMatchers with MockitoSugar {

  "Runtime" should {
    "be created without errors" in {
      new Runtime
    }
    "return stored function definition" in {
      // Given
      val runtime = new Runtime
      val original = new FunctionDefinition("myFunction")
      original.addParam("x", ValueType.Integer)
      original.addParam("y", ValueType.Integer)
      runtime.saveFunction(original)
      // When
      val result = runtime.fetchFunction("myFunction")
      // Then
      result should be(original)
    }
    "error when saving the same function twice" in {
      // Given
      val runtime = new Runtime
      val original = new FunctionDefinition("myFunction")
      original.addParam("x", ValueType.Integer)
      original.addParam("y", ValueType.Integer)
      runtime.saveFunction(original)
      // Then
      intercept[IllegalAccessException] {
        // When
        runtime.saveFunction(original)
      }
    }
    "error when saving another function with the same name" in {
      // Given
      val runtime = new Runtime
      val original = new FunctionDefinition("myFunction")
      original.addParam("x", ValueType.Integer)
      original.addParam("y", ValueType.Integer)
      runtime.saveFunction(original)
      val duplicate = new FunctionDefinition("myFunction")
      duplicate.addParam("z", ValueType.Double)
      // Then
      intercept[IllegalAccessException] {
        // When
        runtime.saveFunction(duplicate)
      }
    }
  }

}
