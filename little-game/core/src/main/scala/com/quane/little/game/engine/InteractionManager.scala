package com.quane.little.game.engine

import com.quane.little.game.Game
import com.quane.little.game.entity.{Entity, Food, Mob}
import com.quane.little.language.event.Event

class InteractionManager(game: Game) {

  def mobApproachesFood(mob: Mob, food: Food): Unit = {
    game.eventBus.report(Event.OnFoodNearby, mob, food)
  }

  /** Interaction between a mob and food.
    *
    * Paper beats rock son.
    *
    * @param mob the mob consuming the food
    * @param food the food being consumed
    */
  def mobConsumesFood(mob: Mob, food: Food): Unit = {
    game.eventBus.report(Event.OnFoodConsumed, mob, food)
    game.cleaner.remove(food)
    mob.heal(food.health)
  }

  def mobApproachesImmovableObject(mob: Mob, entity: Entity): Unit = {
    // TODO
  }

  def mobContactsImmovableObject(mob: Mob, entity: Entity): Unit = {
    game.eventBus.report(Event.OnContact, mob, entity)
  }

  def mobApproachesMob(mob: Mob, entity: Entity): Unit = {
    game.eventBus.report(Event.OnMobNearby, mob, entity)
  }

  def mobContactsMob(mob: Mob, entity: Entity): Unit = {
    game.eventBus.report(Event.OnContact, mob, entity)
    game.eventBus.report(Event.OnMobTouching, mob, entity)
  }


}