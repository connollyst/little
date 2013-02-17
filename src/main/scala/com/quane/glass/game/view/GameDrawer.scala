package com.quane.glass.game.view
import com.quane.glass.game.entity.Mob
import org.newdawn.slick.geom.Rectangle
import org.newdawn.slick.geom.Circle
import org.newdawn.slick.geom.Line
import org.newdawn.slick.Graphics
import org.jbox2d.dynamics.Body
import com.quane.glass.game.entity.Food
import com.quane.glass.game.entity.WorldEdge

/** A static set of drawing utilities.
  */
object GameDrawer {

    def drawGuy(graphics: Graphics, mob: Mob) {
        drawBody(graphics, mob.body.physicalBody);
        graphics.drawString("GUY", 700, 25);
        graphics.drawString("Speed: " + mob.speed, 700, 50)
        graphics.drawString("Direction: " + mob.direction, 700, 75)
    }

    def drawBody(graphics: Graphics, body: Body) {
        val x = body.getPosition().x;
        val y = body.getPosition().y;
        val shape = new Circle(x, y, 20);
        val sensor = new Circle(x, y, 60);
        val angle = body.getAngle();
        val x2 = x + 10 * math.cos(angle) toFloat;
        val y2 = y + 10 * math.sin(angle) toFloat;
        val line = new Line(x, y, x2, y2);
        graphics.draw(shape);
        graphics.draw(sensor);
        graphics.draw(line);
    }

    def drawWall(graphics: Graphics, wall: WorldEdge) {
        val body = wall.body
        val x = body.coords.x
        val y = body.coords.y
        val w = body.w
        val h = body.h
        val shape = new Rectangle(x, y, w, h)
        graphics.draw(shape);
    }

    def drawFood(graphics: Graphics, food: Food) {
        val x = food.x;
        val y = food.y;
        val shape = new Rectangle(x, y, 5, 5);
        graphics.draw(shape);
    }

}