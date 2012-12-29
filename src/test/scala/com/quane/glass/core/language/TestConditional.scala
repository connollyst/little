package com.quane.glass.core.language

import org.junit.runner.RunWith
import org.scalatest.BeforeAndAfter
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import com.quane.glass.core.language.Conditional
import com.quane.glass.core.language.Function
import com.quane.glass.core.language.data.True
import com.quane.glass.core.language.PrintStatement

@RunWith(classOf[JUnitRunner])
class TestConditional extends FunSuite with BeforeAndAfter {

    test("test positive conditional") {
        val test = new True;
        val func = new Function(null);
        func.addStep(new PrintStatement("Sean is Cool!"));
        val cond = new Conditional(test, func);
        cond.evaluate;

    }

}