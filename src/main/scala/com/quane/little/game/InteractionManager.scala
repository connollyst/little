package com.quane.little.game

import com.quane.little.game.entity.Mob
import com.quane.little.game.entity.Food
import com.quane.little.language.event.GlassEvent

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

    def mobApproachesFood(mob: Mob, food: Food) = {
        game.eventBus.report(mob, GlassEvent.OnFoodNearby)
    }

    def mobContactsImmovableObject(mob: Mob) = {
        game.eventBus.report(mob, GlassEvent.OnContact);
    }

}