package com.quane.little.game

import com.quane.little.game.physics.PhysicsEngine
import com.quane.little.game.physics.bodies.BodyBuilder
import com.quane.little.game.entity.{Mob, Entity, EntityFactory}
import scala.collection.mutable.ListBuffer
import com.quane.little.game.view.{LineDrawer, ShapeDrawer, MeshDrawer, SpriteDrawer}

/** The little game engine maintains the state of the world and allows us to
  * step through it, advancing the physics simulation and evaluating little code
  * as appropriate.
  *
  * @author Sean Connolly
  */
class LittleGameEngine {

  var eventBus: EventBus = _
  var engine: PhysicsEngine = _
  var builder: BodyBuilder = _
  var meshDrawer: MeshDrawer = _
  var lineDrawer: LineDrawer = _
  var shapeDrawer: ShapeDrawer = _
  var spriteDrawer: SpriteDrawer = _
  var cleaner: LittleCleaner = _
  var entityFactory: EntityFactory = _
  var entities: ListBuffer[Entity] = _
  var players: List[Mob] = _

  private def initEntities(): ListBuffer[Entity] = {
    val all = new ListBuffer[Entity]
    all ++= entityFactory.worldEdges
    all ++= entityFactory.foodList(20)
    all
  }

  /** Create a new game engine.
    */
  def create() {
    eventBus = new EventBus
    engine = new PhysicsEngine
    builder = new BodyBuilder(this, engine.world)
    meshDrawer = new MeshDrawer
    lineDrawer = new LineDrawer
    shapeDrawer = new ShapeDrawer
    spriteDrawer = new SpriteDrawer
    cleaner = new LittleCleaner(this, engine)
    entityFactory = new EntityFactory(this)
    entities = initEntities()
    players = entityFactory.createMobs(10)
  }

  /** Update the game engine's state.
    */
  def update() {
    cleaner.cleanAll()
    engine.updateAll(players)
    eventBus.evaluateAll()
  }

  //  private def renderBackground() {
  //    Gdx.gl.glClearColor(0, 0.62f, 0.89f, 1)
  //    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT)
  //  }
  //
  //  private def renderMeshes() {
  //    meshDrawer.begin()
  //    entities foreach (_.render(meshDrawer))
  //    players foreach (_.render(meshDrawer))
  //    meshDrawer.end()
  //  }
  //
  //  private def renderLines() {
  //    lineDrawer.begin()
  //    entities foreach (_.render(lineDrawer))
  //    players foreach (_.render(lineDrawer))
  //    lineDrawer.end()
  //  }
  //
  //  private def renderShapes() {
  //    shapeDrawer.begin()
  //    entities foreach (_.render(shapeDrawer))
  //    players foreach (_.render(shapeDrawer))
  //    shapeDrawer.end()
  //  }
  //
  //  private def renderSprites() {
  //    spriteDrawer.begin()
  //    entities foreach (_.render(spriteDrawer))
  //    players foreach (_.render(spriteDrawer))
  //    spriteDrawer.end()
  //  }
  //
  //  override def dispose() {
  //    meshDrawer.dispose()
  //    lineDrawer.dispose()
  //    shapeDrawer.dispose()
  //    spriteDrawer.dispose()
  //  }

}
