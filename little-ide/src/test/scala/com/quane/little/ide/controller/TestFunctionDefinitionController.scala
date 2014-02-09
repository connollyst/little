package com.quane.little.ide.controller

import com.quane.little.language.Runtime
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TestFunctionDefinitionController extends FunSuite {

  test("test compiled scope") {
    val scope = new Runtime
    val controller = new FunctionDefinitionController
    val function = controller.compile(scope)
    assert(function.scope == scope)
  }

  test("test compiled name (default)") {
    val controller = new FunctionDefinitionController
    val function = controller.compile(new Runtime)
    assert(function.name == "name")
  }

  test("test compiled name") {
    val controller = new FunctionDefinitionController
    controller.name = "newName"
    val function = controller.compile(new Runtime)
    assert(function.name == "newName")
  }

  test("test compiled with 0 steps") {
    val controller = new FunctionDefinitionController()
    val function = controller.compile(new Runtime)
    assert(function.block.length == 0)
  }

  test("test compiled with 1 step") {
    val controller = new FunctionDefinitionController()
      .addStep(new FunctionReferenceController)
    val function = controller.compile(new Runtime)
    assert(function.block.length == 1)
  }

  test("test compiled with 2 steps") {
    val controller = new FunctionDefinitionController()
      .addStep(new FunctionReferenceController)
      .addStep(new PrintController)
    val function = controller.compile(new Runtime)
    assert(function.block.length == 2)
  }


}
