package com.quane.little.game.view
import com.quane.little.game.entity.Mob
import org.jbox2d.dynamics.Body
import com.quane.little.game.entity.Food
import com.quane.little.game.entity.WorldEdge
import com.badlogic.gdx.graphics.g2d.SpriteBatch

/** A static set of drawing utilities.
  */
object GameDrawer {

//    private val BACKGROUND = new Rectangle(0, 0, 1024 + 1, 768 + 1)
//    private val BACKGROUND_COLOR_1 = new Color(5, 90, 145)
//    private val BACKGROUND_COLOR_2 = new Color(31, 53, 92)
//    private val BACKGROUND_GRADIENT = new GradientFill(0, 0, BACKGROUND_COLOR_1, 5, 100, BACKGROUND_COLOR_2)

//    private val MOB_BODY_COLOR = Color.white
//    private val MOB_SENSOR_COLOR = new Color(255, 255, 255, 0.03f)
    
//    private val MOB_BODY_FILL = new GradientFill(0, 0, MOB_BODY_COLOR, 1, 1, MOB_BODY_COLOR)
//    private val MOB_SENSOR_FILL = new GradientFill(0, 0, MOB_SENSOR_COLOR, 1, 1, MOB_SENSOR_COLOR)
    
//    def drawBackground(graphics: Graphics) {
//        graphics.fill(BACKGROUND, BACKGROUND_GRADIENT)
//    }

    def drawMob(graphics: SpriteBatch, mob: Mob) {
        drawMobBody(graphics, mob.body.physicalBody)
        //graphics.drawString("GUY", 700, 25)
        //graphics.drawString("Speed: " + mob.speed, 700, 50)
        //graphics.drawString("Direction: " + mob.direction, 700, 75)
    }

    private def drawMobBody(graphics: SpriteBatch, body: Body) {
//        val x = body.getPosition().x
//        val y = body.getPosition().y
//        val shape = new Circle(x, y, BodyBuilder.MOB_BODY_SIZE)
//        val sensor = new Circle(x, y, BodyBuilder.MOB_SENSOR_SIZE)
//        val angle = body.getAngle()
//        val x2 = x + 10 * math.cos(angle) toFloat
//        val y2 = y + 10 * math.sin(angle) toFloat
////        val line = new Line(x, y, x2, y2)
//        graphics.draw(shape, MOB_BODY_FILL)
////        graphics.draw(line, MOB_BODY_FILL)
//        graphics.fill(sensor, MOB_SENSOR_FILL)
    }

    def drawWall(graphics: SpriteBatch, wall: WorldEdge) {
//        val body = wall.body
//        val x = body.coords.x
//        val y = body.coords.y
//        val w = body.w
//        val h = body.h
//        val shape = new Rectangle(x, y, w, h)
//        graphics.draw(shape);
    }

    def drawFood(graphics: SpriteBatch, food: Food) {
//        val x = food.x;
//        val y = food.y;
//        val shape = new Rectangle(x, y, 5, 5);
//        graphics.draw(shape);
    }

}