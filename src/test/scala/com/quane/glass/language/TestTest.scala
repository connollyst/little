package com.quane.glass.language

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import com.quane.glass.core.language.Test
import com.quane.glass.core.language.data.True
import com.quane.glass.core.language.data.False
import com.quane.glass.core.language.AND
import com.quane.glass.core.language.OR

@RunWith(classOf[JUnitRunner])
class TestTest extends FunSuite {

    val t: True = new True
    val f: False = new False

    // Test AND
    test("t && t == t") {
        assert((new Test(t, AND, t)).isTrue)
    }
    test("f && f == f") {
        assert((new Test(f, AND, f)).isFalse)
    }
    test("t && f == f") {
        assert((new Test(t, AND, f)).isFalse)
    }
    test("f && t == f") {
        assert((new Test(f, AND, t)).isFalse)
    }

    // Test OR
    test("t || t == f") {
        assert((new Test(t, OR, t)).isTrue)
    }
    test("f || f == t") {
        assert((new Test(f, OR, f)).isFalse)
    }
    test("t || f == t") {
        assert((new Test(t, OR, f)).isTrue)
    }
    test("f || t == t") {
        assert((new Test(f, OR, t)).isTrue)
    }

}
