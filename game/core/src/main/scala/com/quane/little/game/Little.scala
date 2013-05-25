package com.quane.little.game

import com.badlogic.gdx.{Gdx, ApplicationListener}
import com.badlogic.gdx.graphics.{GL10, Texture}
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.quane.little.game.physics.PhysicsEngine
import com.quane.little.game.physics.bodies.BodyBuilder
import com.quane.little.game.entity.{Entity, EntityFactory}
import scala.collection.mutable.ListBuffer
import com.quane.little.game.view.LittleDrawer

/**
 *
 * @author Sean Connolly
 */
class Little
  extends ApplicationListener {

  var elapsed: Float = 0
  var batch: SpriteBatch = _

  val eventBus = new EventBus
  val engine = new PhysicsEngine
  val builder = new BodyBuilder(this, engine.world)
  val drawer = new LittleDrawer
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

  override def create() {
    drawer.create()
    batch = new SpriteBatch()
  }

  override def resize(width: Int, height: Int) {}

  override def render() {
    elapsed += Gdx.graphics.getDeltaTime
    Gdx.gl.glClearColor(0, 0, 0.2f, 1)
    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT)
    cleaner.cleanAll()
    engine.updateAll(players)
    eventBus.evaluateAll()
    batch.begin()
    entities foreach (_.render(batch, drawer))
    players foreach (_.render(batch, drawer))
    batch.end()
  }

  override def pause() {}

  override def resume() {}

  override def dispose() {}

}
