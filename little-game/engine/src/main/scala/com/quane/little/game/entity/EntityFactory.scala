package com.quane.little.game.entity

import scala.Option.option2Iterable
import scala.collection.mutable.ListBuffer
import scala.util.Random

import com.quane.little.game.LittleGameEngine
import com.quane.little.language.event.{EventListener, LittleEvent}
import com.quane.little.language.{FunctionReference, Functions}
import com.quane.little.language.data.Value
import com.quane.little.game.engine.InteractionManagerImpl

class EntityFactory(game: LittleGameEngine) {

  val manager = new InteractionManagerImpl(game)

  def createMobs(number: Int): List[Mob] = {
    val mobs = new ListBuffer[Mob]
    for (i <- 0 until number) {
      mobs += createMob
    }
    mobs.toList
  }

  def createMob: Mob = {
    val mob = new Mob(manager)
    game.eventBus.report(mob, LittleEvent.OnSpawn)
    mob.operator.runtime.saveFunction(Functions.move)
    mob.operator.runtime.saveFunction(Functions.stop)
    mob.operator.addEventListener(
      new EventListener(
        LittleEvent.OnSpawn,
        new FunctionReference(mob.operator, "move")
          .addArg("speed", new Value(100))
      )
    )
    mob.operator.addEventListener(
      new EventListener(
        LittleEvent.OnContact,
        new FunctionReference(mob.operator, "move")
          .addArg("degrees", new Value(260))
      )
    )
    mob.operator.addEventListener(
      new EventListener(
        LittleEvent.OnFoodNearby,
        new FunctionReference(mob.operator, "stop")
      )
    )
    mob.operator.addEventListener(
      new EventListener(
        LittleEvent.OnFoodConsumed,
        new FunctionReference(mob.operator, "stop")
      )
    )
    mob
  }

  def foodList(): List[Food] = {
    foodList(Random.nextInt(10) + 10)
  }

  def foodList(number: Int): List[Food] = {
    val foods = new ListBuffer[Food]
    for (i <- 0 until number) {
      val food = new Food(manager, Random.nextInt(20))
      foods ++= Option(food)
    }
    foods.toList
  }

}