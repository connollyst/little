package com.quane.glass.engine

import java.util.UUID

import scala.Option.option2Iterable
import scala.collection.mutable.ListBuffer
import scala.util.Random

import org.eintr.loglady.Logging
import org.jbox2d.collision.shapes.CircleShape
import org.jbox2d.collision.shapes.PolygonShape
import org.jbox2d.common.Vec2
import org.jbox2d.dynamics.Body
import org.jbox2d.dynamics.BodyDef
import org.jbox2d.dynamics.BodyType
import org.jbox2d.dynamics.FixtureDef
import org.jbox2d.dynamics.World

import com.quane.glass.entity.Food
import com.quane.glass.entity.WorldEdge

class PhysicsEngine()
        extends Logging {

    val gravity = new Vec2(0, 0)
    val doSleep = true
    val world = new World(gravity, doSleep)
    val builder = new WorldBuilder(world)
    val cleaner = new WorldCleaner(world)

    val timeStep = 1 / 60f //the length of time passed to simulate (seconds)
    val velocityIterations = 8 //how strongly to correct velocity
    val positionIterations = 3 //how strongly to correct position

    def update() = {
        world.step(timeStep, velocityIterations, positionIterations)
    }

}