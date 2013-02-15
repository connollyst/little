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
import com.quane.glass.core.Guy

class PhysicsEngine(eventBus: EventBus) {

    val gravity = new Vec2(0, 0)
    val doSleep = true
    val world = new World(gravity, doSleep)

    world.setContactListener(
        new GlassContactListener(eventBus)
    )

    val timeStep = 1 / 60f //the length of time passed to simulate (seconds)
    val velocityIterations = 8 //how strongly to correct velocity
    val positionIterations = 3 //how strongly to correct position

    def updateAll(mobs: List[Guy]) {
        mobs foreach (
            mob => {
                accelerateGuyToSpeed(mob)
                rotateGuyToDirection(mob)
            }
        )
        world.step(timeStep, velocityIterations, positionIterations)
    }

    /** Apply forces to the guy's physical body in order to maintain correct
      * speed.<br/>
      * The body will be accelerated or decelerated to keep it's physical
      * velocity in sync with 'speed' of the {@code Guy}. If the speed is
      * supposed to be greater than the body is actually moving, a force is
      * applied to increase the speed gradually. If the speed is supposed to be
      * lower than the body is actually moving, a force is applied to slow it
      * down.
      *
      * @param container
      * @param graphics
      */
    def accelerateGuyToSpeed(guy: Guy) {
        // Translate the 'speed' change, if any, to an applied force
        val targetVelocity = guy.speed
        val actualVelocity = guy.body.getLinearVelocity
        val changeVelocity = math.max(0, targetVelocity - actualVelocity.x)
        val impulseStrength = guy.body.getMass * changeVelocity
        val impulseAngle = guy.body.getAngle
        val impulseX = targetVelocity * math.cos(impulseAngle) toFloat;
        val impulseY = targetVelocity * math.sin(impulseAngle) toFloat;
        val impulse = new Vec2(impulseX, impulseY)
        guy.body.applyLinearImpulse(impulse, guy.body.getWorldCenter())
    }

    def rotateGuyToDirection(guy: Guy) {
        // Translate the 'direction' change, if any, to an applied force
        val desiredAngle = math.toRadians(guy.direction);
        val bodyAngle = guy.body.getAngle;
        val nextAngle = bodyAngle + guy.body.getAngularVelocity / 4.5 toFloat;
        var totalRotation = desiredAngle - nextAngle toFloat;
        while (totalRotation < math.toRadians(-180))
            totalRotation += math.toRadians(360).toFloat;
        while (totalRotation > math.toRadians(180))
            totalRotation -= math.toRadians(360).toFloat;
        var desiredAngularVelocity = totalRotation * 60;
        val change = math.toRadians(1) toFloat; //allow 1 degree rotation per time step
        desiredAngularVelocity = math.min(change, math.max(-change, desiredAngularVelocity));
        val impulse = guy.body.getInertia * desiredAngularVelocity;
        guy.body.applyAngularImpulse(impulse);
    }

}