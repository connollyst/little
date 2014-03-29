package com.quane.little.game.physics

import com.quane.little.game.entity.{Entity, EntityRemovalListener, Mob}
import org.jbox2d.dynamics.World
import org.jbox2d.common.Vec2

/** The physics simulation engine takes care of moving entities about and
  * detecting collisions.
  *
  * @author Sean Connolly
  */
class PhysicsEngine
  extends EntityRemovalListener {

  // the length of time passed to simulate (seconds)
  private val timeStep = 1 / 30f
  // how strongly to correct velocity
  private val velocityIterations = 8
  // how strongly to correct position
  private val positionIterations = 3
  // zero gravity for a top-down game
  private val gravity = new Vec2(0, 0)
  // let sleeping dogs lie
  private val doSleep = true

  val world = new World(gravity)
  world.setAllowSleep(doSleep)
  world.setContactListener(new PhysicalContactListener)

  /** Move the physics simulation forward one time step.
    *
    * @param entities the list of entities to update
    */
  def updateAll(entities: Iterable[Entity]) {
    entities foreach {
      case mob: Mob =>
        setMobVelocity(mob)
      // accelerateGuyToSpeed(mob)
      // rotateGuyToDirection(mob)
    }
    world.step(timeStep, velocityIterations, positionIterations)
  }

  /** Removes the specified entity from the physics simulation.
    *
    * @param entity the entity to remove
    */
  override def entityRemoved(entity: Entity): Unit = {
    world.destroyBody(entity.body.physicalBody)
  }

  /** DEPRECATED: this is simpler but by using setLinearVelocity we are
    * essentially bypassing the physics simulation
    *
    * @param mob the mob to move
    * @deprecated replace with [[accelerateGuyToSpeed]] and [[rotateGuyToDirection]]
    */
  private def setMobVelocity(mob: Mob): Unit = {
    val mag = mob.speed
    val dir = mob.direction
    val x = mag * math.cos(dir)
    val y = mag * math.sin(dir)
    val velocity = new Vec2(x.toFloat, y.toFloat)
    mob.body.physicalBody.setLinearVelocity(velocity)
  }

  /** Apply forces to the mob's physical body in order to maintain correct
    * speed.<br/>
    * The body will be accelerated or decelerated to keep it's physical
    * velocity in sync with 'speed' of the
    * [[com.quane.little.game.entity.Mob]]. If the speed is supposed to be
    * greater than the body is actually moving, a force is applied to
    * increase the speed gradually. If the speed is supposed to be lower
    * than the body is actually moving, a force is applied to slow it down.
    *
    * @param mob the mob being accelerated
    */
  private def accelerateGuyToSpeed(mob: Mob) {
    // Translate the 'speed' change, if any, to an applied force
    val targetVelocity = mob.speed
    val actualVelocity = mob.body.physicalBody.getLinearVelocity
    val changeVelocity = math.max(0, targetVelocity - actualVelocity.x)
    val impulseStrength = mob.body.physicalBody.getMass * changeVelocity
    val impulseAngle = mob.body.physicalBody.getAngle
    val impulseX = targetVelocity * math.cos(impulseAngle).toFloat
    val impulseY = targetVelocity * math.sin(impulseAngle).toFloat
    val impulse = new Vec2(impulseX, impulseY)
    mob.body.physicalBody.applyLinearImpulse(impulse,
      mob.body.physicalBody.getWorldCenter)
  }

  private def rotateGuyToDirection(mob: Mob) {
    // Translate the 'direction' change, if any, to an applied force
    val desiredAngle = math.toRadians(mob.direction)
    val bodyAngle = mob.body.physicalBody.getAngle
    val nextAngle = (bodyAngle + mob.body.physicalBody.getAngularVelocity / 4.5).toFloat
    var totalRotation = (desiredAngle - nextAngle).toFloat
    while (totalRotation < math.toRadians(-180))
      totalRotation += math.toRadians(360).toFloat
    while (totalRotation > math.toRadians(180))
      totalRotation -= math.toRadians(360).toFloat
    var desiredAngularVelocity = totalRotation * 60
    val change = math.toRadians(1).toFloat //allow 1 degree rotation per time step
    desiredAngularVelocity = math.min(change, math.max(-change, desiredAngularVelocity))
    val impulse = mob.body.physicalBody.getInertia * desiredAngularVelocity
    mob.body.physicalBody.applyAngularImpulse(impulse)
  }

}
