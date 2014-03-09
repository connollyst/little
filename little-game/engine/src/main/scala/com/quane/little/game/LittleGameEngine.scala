package com.quane.little.game

import com.quane.little.game.entity.{EntityCleaner, Mob, Entity, EntityFactory}
import scala.collection.mutable.ListBuffer
import com.quane.little.game.engine.EventBus

/** The little game engine maintains the state of the world and allows us to
  * step through it, advancing the physics simulation and evaluating little code
  * as appropriate.
  *
  * @author Sean Connolly
  */
class LittleGameEngine {

  var eventBus: EventBus = _
  var cleaner: EntityCleaner = _
  var entityFactory: EntityFactory = _
  var entities: ListBuffer[Entity] = _
  var players: List[Mob] = _

  private def initEntities(): ListBuffer[Entity] = {
    val all = new ListBuffer[Entity]
    all ++= entityFactory.foodList(20)
    all
  }

  /** Create a new game engine.
    */
  def create() {
    eventBus = new EventBus
    cleaner = new EntityCleaner(this)
    entityFactory = new EntityFactory(this)
    entities = initEntities()
  }

  /** Update the game engine's state.
    */
  def update() {
    cleaner.cleanAll()
    eventBus.evaluateAll()
  }

}
