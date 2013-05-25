package com.quane.little.game.entity

import org.scalatest.{BeforeAndAfter, FunSuite}
import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith
import org.scalatest.mock.MockitoSugar
import com.quane.little.game.physics.bodies.EntityBody
import com.quane.little.game.InteractionManager

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