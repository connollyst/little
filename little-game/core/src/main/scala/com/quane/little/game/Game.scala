package com.quane.little.game

import com.quane.little.game.engine.EventBus
import com.quane.little.game.entity._
import com.quane.little.game.physics.PhysicsEngine
import com.quane.little.game.physics.bodies.BodyBuilder
import com.quane.little.tools.Logging
import scala.collection.mutable
import com.quane.little.data.service.{ListenerService, FunctionService}
import com.escalatesoft.subcut.inject.{Injectable, BindingModule}

/** The game maintains the state of the world and all entities, tying together
  * the physics simulator with the Little data model and language evaluation.
  *
  * @author Sean Connolly
  */
class Game(implicit val bindingModule: BindingModule)
  extends EntityRemovalListener
  with Injectable
  with Logging {

  private val functionService = inject[FunctionService]
  private val listenerService = inject[ListenerService]

  val eventBus: EventBus = new EventBus
  val engine: PhysicsEngine = new PhysicsEngine
  val cleaner: EntityRemover = new EntityRemover
  val builder = new BodyBuilder(this, engine.world)
  val entityFactory: EntityFactory = new EntityFactory(this)

  val users: mutable.Map[String, String] = mutable.Map()
  val entities: mutable.Map[String, Entity] = mutable.Map()

  val stateUpdater = new TimedUpdater(30.0, updateState)
  val codeUpdate = new TimedUpdater(0.5, updateCode)

  cleaner.register(this)
  cleaner.register(engine)

  def initialize() = {
    entityFactory.createMobs(5) foreach {
      mob => entities += (mob.id -> mob)
    }
    entityFactory.createFoods() foreach {
      food => entities += (food.id -> food)
    }
    entityFactory.worldEdges() foreach {
      wall => entities += (wall.id -> wall)
    }
  }

  def start() = {
    if (!stateUpdater.isRunning) {
      new Thread(stateUpdater).start()
    } else {
      warn("Skipping start game, already running.")
    }
  }

  def stop() = {
    stateUpdater.stop()
    info("Game stopped..")
  }

  def entity(id: String): Entity = {
    if (entities.contains(id)) {
      entities(id)
    } else {
      throw new IllegalArgumentException("No entity with id '" + id + "'")
    }
  }

  /** Spawn a mob for a user.
    *
    * @param username the name of the user for whom to spawn the mob
    * @return the newly spawned mob
    * @throws IllegalArgumentException if this user already has a mob
    */
  def spawnPlayer(username: String): Entity = {
    if (users.contains(username)) {
      //      throw new IllegalArgumentException(
      //        "User '" + username + "' already has a mob: " + users(username)
      //      )
      entity(users(username))
    } else {
      val mob = entityFactory.createMob()
      entities += (mob.id -> mob)
      users += (username -> mob.id)
      mob
    }
  }

  def removePlayer(username: String): Unit = {
    val mob = entity(users(username))
    cleaner.remove(mob)
    users.remove(username)
  }

  /** Update the game engine's state.
    */
  def updateState(): Unit = {
    cleaner.cleanAll()
    engine.updateAll(entities.values)
    eventBus.evaluateAll()
  }

  def updateCode(): Unit = {
    functionService.init() // TODO this is temporary
    listenerService.init() // TODO this is temporary
    users foreach {
      case (username, entityId) =>
        val functions = functionService.findDefinitionsByUser(username)
        val listeners = listenerService.findListenersByUser(username)
        entity(entityId) match {
          case mob: Mob =>
            functions foreach {
              function => mob.operator.runtime.saveFunction(function)
            }
            listeners foreach {
              listener => mob.operator.addEventListener(listener)
            }
          case other: Entity => throw new IllegalAccessException(
            "Expected user's Entity to be Mob but was " + other.getClass
          )
        }
    }
  }

  override def entityRemoved(entity: Entity) = entities -= entity.id

}
