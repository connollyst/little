package com.quane.glass.engine

import java.util.UUID

import scala.Option.option2Iterable
import scala.collection.mutable.ListBuffer
import scala.util.Random

import org.jbox2d.collision.shapes.CircleShape
import org.jbox2d.collision.shapes.PolygonShape
import org.jbox2d.dynamics.Body
import org.jbox2d.dynamics.BodyDef
import org.jbox2d.dynamics.BodyType
import org.jbox2d.dynamics.FixtureDef
import org.jbox2d.dynamics.World

import com.quane.glass.entity.Food
import com.quane.glass.entity.WorldEdge

class WorldBuilder(game: Game, world: World) {

    def buildBody(): Body = {
        val polygonShape = new CircleShape
        polygonShape.m_radius = 20
        val bodyDef = new BodyDef
        bodyDef.`type` = BodyType.DYNAMIC
        bodyDef.angle = (math.Pi / 8) toFloat;
        bodyDef.allowSleep = false
        bodyDef.position.set(50, 500)
        bodyDef.linearDamping = 0.2 toFloat
        val body = world.createBody(bodyDef)
        body.createFixture(polygonShape, 1.0f)
        body
    }

    def buildFoodList(): List[Food] = {
        val food = new ListBuffer[Food]
        var foodCount = Random.nextInt(10)
        for (i <- 0 until foodCount) {
            food ++= Option(buildFood)
        }
        food.toList
    }

    def buildFood(): Food = {
        val uuid = UUID.randomUUID toString
        val randX = Random.nextInt(900) + 50
        val randY = Random.nextInt(700) + 50
        val foodBodyDef = new BodyDef
        foodBodyDef.`type` = BodyType.STATIC
        foodBodyDef.allowSleep = true
        foodBodyDef.position.set(randX, randY)
        val foodShape = new PolygonShape
        val foodFixture = new FixtureDef
        foodFixture.shape = foodShape
        foodFixture.filter.groupIndex
        val foodBody = world.createBody(foodBodyDef)
        foodShape.setAsBox(5, 5)
        foodBody.createFixture(foodFixture)
        new Food(foodBody, game, Random.nextInt(20))
    }

    def buildWalls(): List[WorldEdge] = {
        List(buildCeiling, buildGround, buildLeftWall, buildRightWall);
    }

    def buildCeiling(): WorldEdge = {
        buildWall(510, 10, 490, 2);
    }

    def buildGround(): WorldEdge = {
        buildWall(510, 750, 490, 2);
    }

    def buildLeftWall(): WorldEdge = {
        buildWall(24, 380, 2, 365);
    }

    def buildRightWall(): WorldEdge = {
        buildWall(1000, 380, 2, 365);
    }

    def buildWall(x: Float, y: Float, halfWidth: Float, halfHeight: Float): WorldEdge = {
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
        new WorldEdge(wallBody, game, x - halfWidth, y - halfHeight, halfWidth * 2, halfHeight * 2);
    }

}