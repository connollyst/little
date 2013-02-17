package com.quane.glass.game

import java.lang.Override

import scala.collection.mutable.ListBuffer

import org.newdawn.slick.BasicGame
import org.newdawn.slick.GameContainer
import org.newdawn.slick.Graphics
import org.newdawn.slick.SlickException

import com.quane.glass.game.entity.Entity
import com.quane.glass.game.entity.Mob
import com.quane.glass.game.physics.PhysicsEngine
import com.quane.glass.game.physics.WorldBuilder
import com.quane.glass.game.physics.WorldCleaner
import com.quane.glass.game.view.GameDrawer
import com.quane.glass.language.data.Number
import com.quane.glass.language.event.EventListener

/** The Glass game.
  *
  * @author Sean Connolly
  */
class Game
        extends BasicGame("Glass") {

    val eventBus = new EventBus
    val engine = new PhysicsEngine
    val builder = new WorldBuilder(this, engine.world)
    val cleaner = new WorldCleaner(this, engine)
    val mobFactory = new MobFactory(this)
    val entities = initEntities()
    val player = mobFactory.createGuy

    def initEntities(): ListBuffer[Entity] = {
        val all = new ListBuffer[Entity]
        all ++= mobFactory.worldEdges
        all ++= mobFactory.foodList
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

    /** Update to the next state of the game:<br/>
      * Remove any entities to be deleted.<br/>
      * Execute all queued events.<br/>
      * Run a step of the engine and draw the next state.
      *
      * @param container
      * @param delta
      * @throws SlickException
      */
    @Override
    @throws(classOf[SlickException])
    override def update(container: GameContainer, delta: Int) {
        cleaner.cleanAll
        engine.updateAll(List(player))
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
        entities foreach (
            entity =>
                entity.render(graphics)
        )
        // TODO guy can be rendered in the same way
        GameDrawer.drawGuy(graphics, player)
    }

}