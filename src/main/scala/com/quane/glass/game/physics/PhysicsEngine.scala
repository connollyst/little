package com.quane.glass.game.physics

import org.jbox2d.common.Vec2
import org.jbox2d.dynamics.World
import com.quane.glass.game.entity.Mob
import com.quane.glass.game.physics.bodies.EntityBody

class PhysicsEngine {

    val gravity = new Vec2(0, 0)
    val doSleep = true
    val world = new World(gravity, doSleep)

    world.setContactListener(new GlassContactListener)

    val timeStep = 1 / 60f //the length of time passed to simulate (seconds)
    val velocityIterations = 8 //how strongly to correct velocity
    val positionIterations = 3 //how strongly to correct position

    def updateAll(mobs: List[Mob]) {
        mobs foreach (
            mob => {
                accelerateGuyToSpeed(mob)
                rotateGuyToDirection(mob)
            }
        )
        world.step(timeStep, velocityIterations, positionIterations)
    }

    /** Apply forces to the mob's physical body in order to maintain correct
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
    def accelerateGuyToSpeed(mob: Mob) {
        // Translate the 'speed' change, if any, to an applied force
        val targetVelocity = mob.speed
        val actualVelocity = mob.body.physicalBody.getLinearVelocity
        val changeVelocity = math.max(0, targetVelocity - actualVelocity.x)
        val impulseStrength = mob.body.physicalBody.getMass * changeVelocity
        val impulseAngle = mob.body.physicalBody.getAngle
        val impulseX = targetVelocity * math.cos(impulseAngle) toFloat;
        val impulseY = targetVelocity * math.sin(impulseAngle) toFloat;
        val impulse = new Vec2(impulseX, impulseY)
        mob.body.physicalBody.applyLinearImpulse(impulse, mob.body.physicalBody.getWorldCenter())
    }

    def rotateGuyToDirection(mob: Mob) {
        // Translate the 'direction' change, if any, to an applied force
        val desiredAngle = math.toRadians(mob.direction);
        val bodyAngle = mob.body.physicalBody.getAngle;
        val nextAngle = bodyAngle + mob.body.physicalBody.getAngularVelocity / 4.5 toFloat;
        var totalRotation = desiredAngle - nextAngle toFloat;
        while (totalRotation < math.toRadians(-180))
            totalRotation += math.toRadians(360).toFloat;
        while (totalRotation > math.toRadians(180))
            totalRotation -= math.toRadians(360).toFloat;
        var desiredAngularVelocity = totalRotation * 60;
        val change = math.toRadians(1) toFloat; //allow 1 degree rotation per time step
        desiredAngularVelocity = math.min(change, math.max(-change, desiredAngularVelocity));
        val impulse = mob.body.physicalBody.getInertia * desiredAngularVelocity;
        mob.body.physicalBody.applyAngularImpulse(impulse);
    }

    def removeEntity(entity: EntityBody) {
        world.destroyBody(entity.physicalBody)
    }

}
