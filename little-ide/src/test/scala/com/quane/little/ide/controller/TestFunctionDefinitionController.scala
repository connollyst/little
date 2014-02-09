package com.quane.little.ide.controller

import com.quane.little.language.Runtime
import com.quane.little.web.controller.FunctionDefinitionController
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TestFunctionDefinitionController extends FunSuite {

  test("test compiled scope") {
    val scope = new Runtime
    val controller = new FunctionDefinitionController
    val definition = controller.compile(scope)
    assert(definition.scope == scope)
  }

  test("test compiled name (default)") {
    val controller = new FunctionDefinitionController
    val definition = controller.compile(new Runtime)
    assert(definition.name == "name")
  }

  test("test compiled name") {
    val controller = new FunctionDefinitionController
    controller.name = "newName"
    val definition = controller.compile(new Runtime)
    assert(definition.name == "newName")
  }

}
