package com.quane.glass.core.language

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

import com.quane.glass.core.language.data.Number
import com.quane.glass.core.language.data.Text
import com.quane.glass.core.language.memory.Pointer

@RunWith(classOf[JUnitRunner])
class TestMemory extends FunSuite {

    test("test one") {
        val scope = new Function(null)
        val name = "sean"
        val value = new Text("is cool")
        val textPointer = new Pointer(scope, name, classOf[Text])
        val setStatement = new SetterStatement(textPointer, new Text("5"))
        scope.evaluate
        assert(scope.isDefined("name"))
        //        assert((new Test(f, OR, t)).isTrue)
    }

}
