package com.quane.little.game.physics.bodies

import java.util.UUID

import scala.util.Random

import org.jbox2d.collision.shapes.CircleShape
import org.jbox2d.collision.shapes.PolygonShape
import org.jbox2d.dynamics.BodyDef
import org.jbox2d.dynamics.BodyType
import org.jbox2d.dynamics.FixtureDef
import org.jbox2d.dynamics.World

import com.quane.little.game.Game

object BodyBuilder {

    val MOB_BODY_SIZE = 10;
    val MOB_SENSOR_SIZE = 100;

}

class BodyBuilder(game: Game, world: World) {

    def buildBody(): EntityBody = {
        val bodyShape = new CircleShape
        bodyShape.m_radius = BodyBuilder.MOB_BODY_SIZE
        val sensorShape = new CircleShape
        sensorShape.m_radius = BodyBuilder.MOB_SENSOR_SIZE
        val sensorFixture = buildBodySensor(sensorShape)
        val bodyDef = new BodyDef
        bodyDef.`type` = BodyType.DYNAMIC
        bodyDef.angle = randomAngle
        println("random angle: " + bodyDef.angle)
        bodyDef.position.set(randomX, randomY)
        bodyDef.allowSleep = false
        bodyDef.linearDamping = 0.2 toFloat
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
        val uuid = UUID.randomUUID toString
        val foodBodyDef = new BodyDef
        foodBodyDef.`type` = BodyType.STATIC
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
        List(buildCeiling, buildGround, buildLeftWall, buildRightWall);
    }

    def buildCeiling(): StaticBody = {
        buildWall(510, 10, 490, 2);
    }

    def buildGround(): StaticBody = {
        buildWall(510, 750, 490, 2);
    }

    def buildLeftWall(): StaticBody = {
        buildWall(24, 380, 2, 365);
    }

    def buildRightWall(): StaticBody = {
        buildWall(1000, 380, 2, 365);
    }

    def buildWall(x: Float, y: Float, halfWidth: Float, halfHeight: Float): StaticBody = {
        val wallBodyDef = new BodyDef;
        wallBodyDef.`type` = BodyType.STATIC;
        wallBodyDef.allowSleep = true;
        wallBodyDef.position.set(x, y);
        val wallShape = new PolygonShape;
        val wallFixture = new FixtureDef;
        wallFixture.shape = wallShape;
        val wallBody = world.createBody(wallBodyDef);
        wallShape.setAsBox(halfWidth, halfHeight);
        wallBody.createFixture(wallFixture);
        new StaticBody(
            wallBody,
            x - halfWidth,
            y - halfHeight,
            halfWidth * 2,
            halfHeight * 2
        );
    }

    private def randomAngle: Float = Random.nextInt(360) * (math.Pi / 180) toFloat

    private def randomX: Int = Random.nextInt(900) + 50

    private def randomY: Int = Random.nextInt(700) + 50
}