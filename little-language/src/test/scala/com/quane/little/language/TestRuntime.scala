package com.quane.little.language

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite

@RunWith(classOf[JUnitRunner])
class TestRuntime extends FunSuite {

  test("test new runtime") {
    new Runtime
  }

}
