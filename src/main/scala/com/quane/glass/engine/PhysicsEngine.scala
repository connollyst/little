package com.quane.glass.engine

import org.jbox2d.collision.shapes.PolygonShape
import org.jbox2d.common.Vec2
import org.jbox2d.dynamics.Body
import org.jbox2d.dynamics.BodyDef
import org.jbox2d.dynamics.BodyType
import org.jbox2d.dynamics.Fixture
import org.jbox2d.dynamics.FixtureDef
import org.jbox2d.dynamics.World
import com.quane.glass.entities.WorldEdge
import net.phys2d.raw.shapes.Circle
import org.jbox2d.collision.shapes.CircleShape
import org.jbox2d.collision.shapes.ShapeType
import org.eintr.loglady.Logging

class PhysicsEngine()
        extends Logging {

    val gravity = new Vec2(0, 0);
    val doSleep = true;
    val world = new World(gravity, doSleep);

    val timeStep = 1 / 60f; //the length of time passed to simulate (seconds)
    val velocityIterations = 8; //how strongly to correct velocity
    val positionIterations = 3; //how strongly to correct position

    def update() = {
        world.step(timeStep, velocityIterations, positionIterations);
    }

    def createBody(): Body = {
        log.info("Creating a body..")
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

    def createWalls(): List[WorldEdge] = {
        List(createCeiling, createGround, createLeftWall, createRightWall);
    }

    def createCeiling(): WorldEdge = {
        createWall(510, 10, 490, 2);
    }

    def createGround(): WorldEdge = {
        createWall(510, 750, 490, 2);
    }

    def createLeftWall(): WorldEdge = {
        createWall(24, 380, 2, 365);
    }

    def createRightWall(): WorldEdge = {
        createWall(1000, 380, 2, 365);
    }

    def createWall(x: Float, y: Float, halfWidth: Float, halfHeight: Float): WorldEdge = {
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
        new WorldEdge(x - halfWidth, y - halfHeight, halfWidth * 2, halfHeight * 2);
    }
}