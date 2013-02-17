package com.quane.glass.game.entity

import org.junit.runner.RunWith
import org.scalatest.BeforeAndAfter
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito.when
import com.quane.glass.game.Game
import com.quane.glass.game.physics.bodies.EntityBody
import com.quane.glass.game.physics.PhysicsEngine
import com.quane.glass.game.physics.WorldCleaner

@RunWith(classOf[JUnitRunner])
class TestFood
        extends FunSuite
        with BeforeAndAfter
        with MockitoSugar {

    test("test food touched by mob") {
        val game = mock[Game]
        val body = mock[EntityBody]
        val engine = mock[PhysicsEngine]
        val cleaner = new WorldCleaner(game, engine)
        val food = new Food(body, game, 10)
        val mob = mock[Mob]
        food.touchedBy(mob)
        assert(food.isRemoved)
    }

}