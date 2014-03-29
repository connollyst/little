package com.quane.little.game.engine

import com.quane.little.game.entity.Mob
import com.quane.little.game.entity.Food
import com.quane.little.language.event.LittleEvent
import com.quane.little.game.LittleGameEngine

class InteractionManager(game: LittleGameEngine) {

  def mobApproachesFood(mob: Mob, food: Food): Unit = {
    game.eventBus.report(mob, LittleEvent.OnFoodNearby)
  }

  /** Interaction between a mob and food.
    *
    * Paper beats rock son.
    *
    * @param mob the mob consuming the food
    * @param food the food being consumed
    */
  def mobConsumesFood(mob: Mob, food: Food): Unit = {
    game.eventBus.report(mob, LittleEvent.OnFoodConsumed)
    game.cleaner.remove(food)
    mob.heal(food.health)
  }

  def mobApproachesImmovableObject(mob: Mob): Unit = {
    // TODO
  }

  def mobContactsImmovableObject(mob: Mob): Unit = {
    game.eventBus.report(mob, LittleEvent.OnContact)
  }

  def mobApproachesMob(mob: Mob): Unit = {
    game.eventBus.report(mob, LittleEvent.OnMobNearby)
  }

  def mobContactsMob(mob: Mob): Unit = {
    game.eventBus.report(mob, LittleEvent.OnContact)
    game.eventBus.report(mob, LittleEvent.OnMobTouching)
  }


}