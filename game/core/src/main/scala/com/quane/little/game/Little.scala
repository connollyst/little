package com.quane.little.game

import com.badlogic.gdx.{Gdx, ApplicationListener}
import com.badlogic.gdx.graphics.{GL10, Texture}
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.quane.little.game.physics.PhysicsEngine
import com.quane.little.game.physics.bodies.BodyBuilder
import com.quane.little.game.entity.{Mob, Entity, EntityFactory}
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
  var eventBus: EventBus = _
  var engine: PhysicsEngine = _
  var builder: BodyBuilder = _
  var drawer: LittleDrawer = _
  var cleaner: LittleCleaner = _
  var entityFactory: EntityFactory = _
  var entities: ListBuffer[Entity] = _
  var players: List[Mob] = _

  def initEntities(): ListBuffer[Entity] = {
    val all = new ListBuffer[Entity]
    all ++= entityFactory.worldEdges
    all ++= entityFactory.foodList(20)
    all
  }

  override def create() {
    batch = new SpriteBatch()
    eventBus = new EventBus
    engine = new PhysicsEngine
    builder = new BodyBuilder(this, engine.world)
    drawer = new LittleDrawer
    cleaner = new LittleCleaner(this, engine)
    entityFactory = new EntityFactory(this)
    entities = initEntities()
    players = entityFactory.createMobs(10)
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
