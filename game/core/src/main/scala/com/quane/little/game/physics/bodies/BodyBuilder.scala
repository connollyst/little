package com.quane.little.game.physics.bodies

import java.util.UUID

import scala.util.Random


import com.quane.little.game.Little
import com.badlogic.gdx.physics.box2d._
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType
import org.eintr.loglady.Logging

object BodyBuilder {

  val MobBodySize = 10
  val MobSensorSize = 100

}

class BodyBuilder(game: Little, world: World)
  extends Logging {

  def buildBody(): EntityBody = {
    val bodyShape = new CircleShape
    bodyShape.setRadius(BodyBuilder.MobBodySize)
    val sensorShape = new CircleShape
    sensorShape.setRadius(BodyBuilder.MobSensorSize)
    val sensorFixture = buildBodySensor(sensorShape)
    val bodyDef = new BodyDef
    bodyDef.`type` = BodyType.DynamicBody
    bodyDef.angle = randomAngle
    log.info("random angle: " + bodyDef.angle)
    bodyDef.position.set(randomX, randomY)
    bodyDef.allowSleep = false
    bodyDef.linearDamping = 0.2.toFloat
    val body = world.createBody(bodyDef)
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

  def buildFood(): EntityBody = {
    val uuid = UUID.randomUUID.toString
    val foodBodyDef = new BodyDef
    foodBodyDef.`type` = BodyType.StaticBody
    foodBodyDef.allowSleep = true
    foodBodyDef.position.set(randomX, randomY)
    val foodShape = new PolygonShape
    val foodFixture = new FixtureDef
    foodFixture.shape = foodShape
    foodFixture.filter.groupIndex
    val foodBody = world.createBody(foodBodyDef)
    foodShape.setAsBox(5, 5)
    foodBody.createFixture(foodFixture)
    new EntityBody(foodBody)
  }

  def buildWalls(): List[StaticBody] = {
    List(buildCeiling(), buildGround(), buildLeftWall(), buildRightWall())
  }

  def buildCeiling(): StaticBody = buildWall(510, 10, 490, 2)

  def buildGround(): StaticBody = buildWall(510, 750, 490, 2)

  def buildLeftWall(): StaticBody = buildWall(24, 380, 2, 365)

  def buildRightWall(): StaticBody = buildWall(1000, 380, 2, 365)

  def buildWall(x: Float, y: Float, halfWidth: Float, halfHeight: Float): StaticBody = {
    val wallBodyDef = new BodyDef
    wallBodyDef.`type` = BodyType.StaticBody
    wallBodyDef.allowSleep = true
    wallBodyDef.position.set(x, y)
    val wallShape = new PolygonShape
    val wallFixture = new FixtureDef
    wallFixture.shape = wallShape
    val wallBody = world.createBody(wallBodyDef)
    wallShape.setAsBox(halfWidth, halfHeight)
    wallBody.createFixture(wallFixture)
    new StaticBody(
      wallBody,
      x - halfWidth,
      y - halfHeight,
      halfWidth * 2,
      halfHeight * 2
    )
  }

  private def randomAngle: Float = (Random.nextInt(360) * (math.Pi / 180)).toFloat

  private def randomX: Int = Random.nextInt(900) + 50

  private def randomY: Int = Random.nextInt(700) + 50
}