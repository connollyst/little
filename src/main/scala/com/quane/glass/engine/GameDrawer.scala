package com.quane.glass.engine

import com.quane.glass.entities.Food
import com.quane.glass.entities.WorldEdge
import com.quane.glass.core.Guy
import org.newdawn.slick.geom.Rectangle
import org.newdawn.slick.geom.Circle
import org.newdawn.slick.geom.Line
import org.newdawn.slick.Graphics
import org.jbox2d.dynamics.Body

object GameDrawer {

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

    def drawFood(graphics: Graphics, food: Food): Unit = {
        val x = food.x;
        val y = food.y;
        val shape = new Rectangle(x, y, 5, 5);
        graphics.draw(shape);
    }

}