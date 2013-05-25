package com.quane.little.game.entity

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