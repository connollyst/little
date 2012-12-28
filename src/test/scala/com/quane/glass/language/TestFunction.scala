package com.quane.glass.language

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import com.quane.glass.core.language.Function
import com.quane.glass.core.language.AssignmentStatement
import com.quane.glass.core.language.ReturnStatement

/** @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestFunction extends FunSuite {

    /** Test evaluating a {@code Function} where two objects are created.<br/>
      * Assert their stored values are accurate.
      */
    test("test function: new") {
        val fun = new Function(null)
        fun.addStep(new AssignmentStatement("Obj1", "A", fun))
        fun.addStep(new AssignmentStatement("Obj2", "B", fun))
        fun.evaluate
        val obj1 = fun.fetch("Obj1")
        val obj2 = fun.fetch("Obj2")
        assert(obj1.value == "A", "expected Obj1 to be 'A' but is: " + obj1.value)
        assert(obj2.value == "B", "expected Obj2 to be 'B' but is: " + obj2.value)
    }

    /** Test evaluating a {@code Function} where two objects are created and then
      * the value of the former changed to that of the latter.<br/>
      * Assert their stored values are accurate.
      */
    test("test function: assignment") {
        val fun = new Function(null)
        fun.addStep(new AssignmentStatement("Obj1", "A", fun))
        fun.addStep(new AssignmentStatement("Obj2", "B", fun))
        // TODO we need a new AssignmentStatement that sets one Variable to another?
        fun.addStep(new AssignmentStatement("Obj1", "Obj2", fun))
        fun.evaluate
        val obj1 = fun.fetch("Obj1")
        val obj2 = fun.fetch("Obj2")
        assert(obj1.value == "B", "expected Obj1 to be 'B' but is: " + obj1.value)
        assert(obj2.value == "B", "expected Obj2 to be 'B' but is: " + obj2.value)
    }

    /** Test evaluating a {@code Function} with a return value.<br/>
      * Note: this doesn't work yet. I'm not sure how exactly to implement it,
      * but, more importantly I don't know if we need it yet..
      */
    test("test function: return step") {
        val fun = new Function(null)
        fun.addStep(new AssignmentStatement("Obj1", "A", fun))
        fun.addStep(new ReturnStatement("Obj1", fun))
        val obj1 = fun.evaluate
        // assert(obj1.value == "A", "expected Obj1 to be 'A' but is: " + obj1.value)
    }

}