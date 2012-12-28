package com.quane.glass.language

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import com.quane.glass.core.language.Function
import com.quane.glass.core.language.data.Variable

/** Tests for the {@code Scope} trait.
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestScope extends FunSuite {

    test("test scope: fetch from parent scope") {
        val fun1 = new Function(null)
        val fun2 = new Function(fun1)
        fun1.save(new Variable("MyA", "A"))
        fun2.save(new Variable("MyB", "B"))
        val myA = fun2.fetch("MyA")
        val myB = fun2.fetch("MyB")
        assert(myA.value == "A", "expected 'MyA' to be 'A' but is: " + myA.value)
        assert(myB.value == "B", "expected 'MyB' to be 'B' but is: " + myB.value)
    }

    test("test scope: is defined: positive") {
        val fun1 = new Function(null)
        fun1.save(new Variable("MyA", "A"))
        val defined = fun1.isDefined("MyA");
        assert(defined, "expected 'MyA' to be defined")
    }

    test("test scope: is defined: negative") {
        val fun1 = new Function(null)
        val defined = fun1.isDefined("MyA");
        assert(!defined, "didn't expect 'MyA' to be defined")
    }

}