package com.quane.glass.game.entity

import org.junit.runner.RunWith
import org.scalatest.BeforeAndAfter
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.scalatest.mock.MockitoSugar

import com.quane.glass.game.InteractionManager
import com.quane.glass.game.physics.bodies.EntityBody

@RunWith(classOf[JUnitRunner])
class TestFood
        extends FunSuite
        with BeforeAndAfter
        with MockitoSugar {

    test("test food touched by mob") {
        val body = mock[EntityBody]
        val manager = mock[InteractionManager]
        val food = new Food(body, manager, 10)
        val mob = mock[Mob]
        food.touchedBy(mob)
        assert(food.isRemoved)
    }

}