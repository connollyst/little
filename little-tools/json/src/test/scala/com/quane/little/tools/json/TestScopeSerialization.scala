package com.quane.little.tools.json

import com.quane.little.language.Runtime
import com.quane.little.tools.json.JSONTestUtilities._
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.scalatest.mock.MockitoSugar

/** Test JSON serialization of [[com.quane.little.language.Scope]] objects.
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestScopeSerialization
  extends FunSuite
  with MockitoSugar {

  // TODO does it make sense to ever serialize a scope??

  test("serialize runtime") {
    val name = "runtime"
    val runtime = new Runtime()
    val actual = new JSONSerializer().serialize(runtime)
    val expected = getJSON(name)
    assertJSON(expected, actual)
  }

}
