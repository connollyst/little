import com.quane.little.game.Game

/**
 *
 *
 * @author Sean Connolly
 */
object GameRunner {

  def main(args: Array[String]) {
    val game = new Game()
    game.initialize()
    game.start()
  }

}
