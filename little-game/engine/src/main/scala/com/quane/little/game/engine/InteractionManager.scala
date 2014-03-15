package com.quane.little.game.engine

import com.quane.little.game.entity.Mob
import com.quane.little.game.entity.Food
import com.quane.little.language.event.LittleEvent
import com.quane.little.game.LittleGameEngine

class InteractionManager(game: LittleGameEngine) {

  /** Interaction between a mob and food.
    *
    * Paper beats rock son.
    *
    * @param mob
    * @param food
    */
  def mobConsumesFood(mob: Mob, food: Food) {
    game.eventBus.report(mob, LittleEvent.OnFoodConsumed)
    game.cleaner.remove(food)
    mob.heal(food.health)
  }

  def mobApproachesFood(mob: Mob, food: Food) {
    game.eventBus.report(mob, LittleEvent.OnFoodNearby)
  }

  def mobContactsImmovableObject(mob: Mob) {
    game.eventBus.report(mob, LittleEvent.OnContact)
  }

}