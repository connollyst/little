package com.quane.little.game

import java.lang.Override

import scala.collection.mutable.ListBuffer

import org.newdawn.slick.BasicGame
import org.newdawn.slick.GameContainer
import org.newdawn.slick.Graphics
import org.newdawn.slick.SlickException

import com.quane.little.game.entity.Entity
import com.quane.little.game.entity.EntityFactory
import com.quane.little.game.physics.bodies.BodyBuilder
import com.quane.little.game.physics.PhysicsEngine

/** The Glass game.
  *
  * @author Sean Connolly
  */
class Game
        extends BasicGame("Glass") {

    val eventBus = new EventBus
    val engine = new PhysicsEngine
    val builder = new BodyBuilder(this, engine.world)
    val cleaner = new GameCleaner(this, engine)
    val entityFactory = new EntityFactory(this)
    val entities = initEntities()
    val players = entityFactory.createMobs(10)

    def initEntities(): ListBuffer[Entity] = {
        val all = new ListBuffer[Entity]
        all ++= entityFactory.worldEdges
        all ++= entityFactory.foodList(20)
        all
    }

    /** Initialize the game.
      *
      * @param container
      * @throws SlickException
      */
    @Override
    @throws(classOf[SlickException])
    def init(container: GameContainer) {
        container.setMinimumLogicUpdateInterval(-1) // Default
        container.setMaximumLogicUpdateInterval(50)
    }

    /** Update to the next state of the game.
      *
      * @param container
      * @param delta
      * @throws SlickException
      */
    @Override
    @throws(classOf[SlickException])
    override def update(container: GameContainer, delta: Int) {
        cleaner.cleanAll
        engine.updateAll(players)
        eventBus.evaluateAll
    }

    /** Render the current state of the game.
      *
      * @param container
      * @param graphics
      * @throws SlickException
      */
    @Override
    @throws(classOf[SlickException])
    override def render(container: GameContainer, graphics: Graphics) {
        entities foreach (_.render(graphics))
        players foreach (_.render(graphics))
    }

}