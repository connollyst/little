import com.quane.little.game.LittleGameEngine

/**
 *
 *
 * @author Sean Connolly
 */
object GameRunner {

  def main(args: Array[String]) {
    val game = new LittleGameEngine()
    game.initialize()
    game.start()
  }

}
