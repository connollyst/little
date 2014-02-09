package com.quane.little.ide.controller

import com.quane.little.web.controller.FunctionReferenceController
import com.quane.little.language.Runtime
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TestFunctionReferenceController extends FunSuite {

  test("test compiled scope") {
    val scope = new Runtime
    val controller = new FunctionReferenceController
    val ref = controller.compile(scope)
    assert(ref.scope == scope)
  }

  test("test compiled name (default)") {
    val controller = new FunctionReferenceController
    val ref = controller.compile(new Runtime)
    assert(ref.name == "name")
  }

  test("test compiled name") {
    val controller = new FunctionReferenceController
    controller.name = "newName"
    val ref = controller.compile(new Runtime)
    assert(ref.name == "newName")
  }


}
