package com.quane.little.ide

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import java.io.{ObjectOutputStream, ByteArrayOutputStream}

@RunWith(classOf[JUnitRunner])
class TestTools extends FunSuite {

  /**
   * [[com.quane.little.ide.Tools]] need to be serialized to be sent as data
   * with drag and drop events
   */
  test("tools are serializable") {
    assertSerializable(Tools.events)
    assertSerializable(Tools.setters)
    assertSerializable(Tools.getters)
    assertSerializable(Tools.math)
  }

  /**
   * Assert tools are serializable by, well, serializing them.
   *
   * @param tools a vector of tools
   */
  private def assertSerializable(tools: Vector[Tool]) {
    val baos = new ByteArrayOutputStream(1024)
    val o = new ObjectOutputStream(baos)
    for (tool <- tools) {
      o.writeObject(tool)
    }
  }
}
