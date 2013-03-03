package com.quane.little.language

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import com.quane.little.language.data.Variable
import com.quane.little.language.data.Text

/** Tests for the {@code Scope} trait.
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestScope extends FunSuite {

    test("test scope: fetch from parent scope") {
        val fun1 = new Function(null)
        val fun2 = new Function(fun1)
        fun1.save(new Variable("Obj1", new Text("A")))
        fun2.save(new Variable("Obj2", new Text("B")))
        val obj1 = fun2.fetch("Obj1")
        val obj2 = fun2.fetch("Obj2")
        val obj1Value = obj1.value.primitive
        val obj2Value = obj2.value.primitive
        assert(obj1Value == "A", "expected 'Obj1' to be 'A' but is: " + obj1Value)
        assert(obj2Value == "B", "expected 'Obj2' to be 'B' but is: " + obj2Value)
    }

    test("test scope: is defined: positive") {
        val fun1 = new Function(null)
        fun1.save(new Variable("Obj1", new Text("A")))
        val defined = fun1.isDefined("Obj1");
        assert(defined, "expected 'Obj1' to be defined")
    }

    test("test scope: is defined: negative") {
        val fun1 = new Function(null)
        val defined = fun1.isDefined("Obj1");
        assert(!defined, "didn't expect 'Obj1' to be defined")
    }

}