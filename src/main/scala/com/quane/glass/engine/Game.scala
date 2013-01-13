package com.quane.glass.engine

import java.lang.Override
import scala.collection.mutable.HashMap
import scala.collection.mutable.MultiMap
import scala.collection.mutable.Set
import org.jbox2d.common.Vec2
import org.jbox2d.dynamics.Body
import org.newdawn.slick.BasicGame
import org.newdawn.slick.GameContainer
import org.newdawn.slick.Graphics
import org.newdawn.slick.SlickException
import org.newdawn.slick.geom.Circle
import org.newdawn.slick.geom.Line
import org.newdawn.slick.geom.Rectangle
import com.quane.glass.core.event.GlassEvent
import com.quane.glass.core.event.EventListener
import com.quane.glass.entities.WorldEdge
import com.quane.glass.core.Guy
import com.quane.glass.core.Programs
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class Game extends BasicGame("Glass") {

    var engine = new PhysicsEngine;
    val guy = new Guy(engine.createBody);
    val walls = engine.createWalls;

    val eventQueue = new HashMap[String, Set[GlassEvent]]() with MultiMap[String, GlassEvent]

    @Override
    @throws(classOf[SlickException])
    def init(container: GameContainer): Unit = {
        println("Initializing")
        val listener = new GlassContactListener(this)
        engine.world.setContactListener(listener)
        container.setMinimumLogicUpdateInterval(20)
        container.setMaximumLogicUpdateInterval(50)
        guy.addEventListener(new EventListener(guy, GlassEvent.OnSpawn, Programs.move(guy, 10)))
        guy.addEventListener(new EventListener(guy, GlassEvent.OnContact, Programs.stop(guy)))
        guy.getEventListeners(GlassEvent.OnSpawn) foreach (
            listener =>
                listener.fire()
        );
        Programs.inTime(4, Programs.stop(guy));
        Programs.inTime(4, Programs.turn(guy, 10))
        Programs.inTime(5, Programs.move(guy, 50))
        Programs.inTime(6, Programs.turn(guy, -60))
    }

    @Override
    @throws(classOf[SlickException])
    def update(container: GameContainer, delta: Int): Unit = {
    }

    @Override
    @throws(classOf[SlickException])
    def render(container: GameContainer, graphics: Graphics): Unit = {
        // Fire any events on the guy
        // TODO check events; mod nearby, food nearby, etc

        accelerateGuyToSpeed(graphics)
        rotateGuyToDirection(graphics)

        // Run a step of the engine and draw the next state
        engine.update
        drawGuy(graphics, guy)
        walls.foreach(wall => drawWall(graphics, wall))

        // See which events need to be fired
        eventQueue.keys foreach (
            uuid => {
                val events = eventQueue.get(uuid).orNull // TODO replace with orElse?
                events foreach (
                    event => {
                        guy.getEventListeners(event) foreach (
                            listener =>
                                listener.fire()
                        )
                    })
            })
        eventQueue.clear
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
    def accelerateGuyToSpeed(graphics: Graphics): Unit = {
        // Translate the 'speed' change, if any, to an applied force
        val targetVelocity = guy.speed
        val actualVelocity = guy.body.getLinearVelocity
        val changeVelocity = math.max(0, targetVelocity - actualVelocity.x)
        val impulseStrength = guy.body.getMass * changeVelocity
        val impulseAngle = guy.body.getAngle
        val impulseX = targetVelocity * math.cos(impulseAngle) toFloat;
        val impulseY = targetVelocity * math.sin(impulseAngle) toFloat;
        val impulse = new Vec2(impulseX, impulseY)
        graphics.drawString("ACCELERATION:", 25, 150)
        graphics.drawString("Impulse Strength: " + impulseStrength, 50, 175)
        graphics.drawString("Impulse Angle: " + math.toDegrees(impulseAngle), 50, 200)
        graphics.drawString("Impulse x: " + impulse.x, 50, 225)
        graphics.drawString("Impulse y: " + impulse.y, 50, 250)
        graphics.drawString("Actual Velocity.x: " + actualVelocity.x, 50, 275)
        graphics.drawString("Actual Velocity.y: " + actualVelocity.y, 50, 300)
        guy.body.applyLinearImpulse(impulse, guy.body.getWorldCenter())
    }

    def rotateGuyToDirection(graphics: Graphics): Unit = {
        // Translate the 'direction' change, if any, to an applied force
        val desiredAngle = math.toRadians(guy.direction);
        val bodyAngle = guy.body.getAngle;
        val nextAngle = bodyAngle + guy.body.getAngularVelocity / 4.5 toFloat;
        var totalRotation = desiredAngle - nextAngle toFloat;
        while (totalRotation < math.toRadians(-180)) totalRotation += math.toRadians(360).toFloat;
        while (totalRotation > math.toRadians(180)) totalRotation -= math.toRadians(360).toFloat;
        var desiredAngularVelocity = totalRotation * 60;
        val change = math.toRadians(1) toFloat; //allow 1 degree rotation per time step
        desiredAngularVelocity = math.min(change, math.max(-change, desiredAngularVelocity));
        val impulse = guy.body.getInertia * desiredAngularVelocity;
        guy.body.applyAngularImpulse(impulse);

        // View these in real time
        graphics.drawString("ROTATION", 25, 25);
        graphics.drawString("Target Angle: " + desiredAngle, 50, 50);
        graphics.drawString("Body Angle: " + math.toDegrees(bodyAngle), 50, 75);
        graphics.drawString("Next Angle: " + math.toDegrees(nextAngle), 50, 100);
    }

    def drawGuy(graphics: Graphics, guy: Guy): Unit = {
        drawBody(graphics, guy.body);
        graphics.drawString("GUY", 700, 25);
        graphics.drawString("Speed: " + guy.speed, 700, 50)
        graphics.drawString("Direction: " + guy.direction, 700, 75)
    }

    def drawBody(graphics: Graphics, body: Body): Unit = {
        val x = body.getPosition().x;
        val y = body.getPosition().y;
        val shape = new Circle(x, y, 20);
        val angle = body.getAngle();
        val x2 = x + 10 * math.cos(angle) toFloat;
        val y2 = y + 10 * math.sin(angle) toFloat;
        val line = new Line(x, y, x2, y2);
        graphics.draw(shape);
        graphics.draw(line);
    }

    def drawWall(graphics: Graphics, wall: WorldEdge): Unit = {
        graphics.draw(new Rectangle(wall.x, wall.y, wall.w, wall.h));
    }

    def queueEvent(event: GlassEvent, uuid: String) = {
        eventQueue.addBinding(uuid, event)
    }
}