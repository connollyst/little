package com.quane.little.game.physics.bodies

import com.quane.little.game.Game
import org.jbox2d.collision.shapes.{PolygonShape, CircleShape}
import org.jbox2d.dynamics.{FixtureDef, BodyType, BodyDef, World}
import scala.util.Random

object BodyBuilder {
  val MobBodySize = 1
  val MobSensorSize = 10
}

class BodyBuilder(game: Game, world: World) {

  def buildBody(x: Float, y: Float): EntityBody = {
    val bodyDef = new BodyDef
    bodyDef.`type` = BodyType.DYNAMIC
    bodyDef.angle = randomAngle
    bodyDef.position.set(x, y)
    bodyDef.allowSleep = false
    bodyDef.linearDamping = 0.2.toFloat
    val body = world.createBody(bodyDef)
    val bodyShape = new CircleShape
    val sensorShape = new CircleShape
    val sensorFixture = buildBodySensor(sensorShape)
    bodyShape.setRadius(BodyBuilder.MobBodySize)
    sensorShape.setRadius(BodyBuilder.MobSensorSize)
    body.createFixture(bodyShape, 1.0f)
    body.createFixture(sensorFixture)
    new EntityBody(body)
  }

  def buildBodySensor(shape: CircleShape): FixtureDef = {
    val sensorFixture = new FixtureDef
    sensorFixture.isSensor = true
    sensorFixture.shape = shape
    sensorFixture
  }

  def buildFood(x: Float, y: Float): EntityBody = {
    val foodBodyDef = new BodyDef
    foodBodyDef.`type` = BodyType.STATIC
    foodBodyDef.angle = randomAngle
    foodBodyDef.position.set(x, y)
    foodBodyDef.allowSleep = true
    val foodBody = world.createBody(foodBodyDef)
    val foodShape = new PolygonShape
    val foodFixture = new FixtureDef
    foodFixture.shape = foodShape
    foodFixture.filter.groupIndex
    foodShape.setAsBox(0.5f, 0.5f)
    foodBody.createFixture(foodFixture)
    new EntityBody(foodBody)
  }

  def buildWalls(): List[StaticBody] = {
    List(buildCeiling(), buildGround(), buildLeftWall(), buildRightWall())
  }

  def buildCeiling(): StaticBody = buildWall(50, 100, 100, 1)

  def buildGround(): StaticBody = buildWall(50, 0, 100, 1)

  def buildLeftWall(): StaticBody = buildWall(0, 50, 1, 100)

  def buildRightWall(): StaticBody = buildWall(100, 50, 1, 100)

  def buildWall(x: Float, y: Float, w: Float, h: Float): StaticBody = {
    val wallBodyDef = new BodyDef
    wallBodyDef.`type` = BodyType.STATIC
    wallBodyDef.allowSleep = true
    wallBodyDef.position.set(x, y)
    val wallBody = world.createBody(wallBodyDef)
    val wallShape = new PolygonShape
    val wallFixture = new FixtureDef
    wallFixture.shape = wallShape
    wallShape.setAsBox(w, h)
    wallBody.createFixture(wallFixture)
    new StaticBody(wallBody, x, y, w, h)
  }

  private def randomAngle: Float = (Random.nextInt(360) * (math.Pi / 180)).toFloat

}