package com.quane.glass.game

import com.quane.glass.game.entity.Mob
import com.quane.glass.game.entity.Food
import com.quane.glass.language.event.GlassEvent

trait InteractionManager {

    /** Interaction between a mob and food.
      *
      * Paper beats rock son.
      *
      * @param mob
      * @param food
      */
    def mobConsumesFood(mob: Mob, food: Food)

    def mobContactsImmovableObject(mob: Mob)

}

class InteractionManagerImpl(game: Game)
        extends InteractionManager {

    def mobConsumesFood(mob: Mob, food: Food) = {
        game.eventBus.report(mob, GlassEvent.OnFoodConsumed)
        game.cleaner.remove(food)
        mob.heal(food.health)
    }

    def mobContactsImmovableObject(mob: Mob) = {
        game.eventBus.report(mob, GlassEvent.OnContact);
    }
}