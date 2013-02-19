package com.quane.little.game.view
import com.quane.little.game.entity.Mob
import org.newdawn.slick.geom.Rectangle
import org.newdawn.slick.geom.Circle
import org.newdawn.slick.geom.Line
import org.newdawn.slick.Graphics
import org.jbox2d.dynamics.Body
import com.quane.little.game.entity.Food
import com.quane.little.game.entity.WorldEdge
import org.newdawn.slick.fills.GradientFill
import org.newdawn.slick.Color

/** A static set of drawing utilities.
  */
object GameDrawer {

    private val BACKGROUND = new Rectangle(0, 0, 1024 + 1, 768 + 1)
    private val BACKGROUND_COLOR_1 = new Color(5, 90, 145)
    private val BACKGROUND_COLOR_2 = new Color(31, 53, 92)
    private val BACKGROUND_GRADIENT = new GradientFill(0, 0, BACKGROUND_COLOR_1, 5, 100, BACKGROUND_COLOR_2)

    def drawBackground(graphics: Graphics) {
        graphics.fill(BACKGROUND, BACKGROUND_GRADIENT)
    }

    def drawGuy(graphics: Graphics, mob: Mob) {
        drawBody(graphics, mob.body.physicalBody)
        graphics.drawString("GUY", 700, 25)
        graphics.drawString("Speed: " + mob.speed, 700, 50)
        graphics.drawString("Direction: " + mob.direction, 700, 75)
    }

    def drawBody(graphics: Graphics, body: Body) {
        val x = body.getPosition().x
        val y = body.getPosition().y
        val shape = new Circle(x, y, 20)
        val sensor = new Circle(x, y, 60)
        val angle = body.getAngle()
        val x2 = x + 10 * math.cos(angle) toFloat
        val y2 = y + 10 * math.sin(angle) toFloat
        val line = new Line(x, y, x2, y2)
        graphics.draw(shape)
        graphics.draw(sensor)
        graphics.draw(line)
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