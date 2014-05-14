import com.quane.little.data.DataBindingModule
import com.quane.little.game.Game

/** A runnable headless game simulation client.
  *
  * @author Sean Connolly
  */
object GameRunner {

  def main(args: Array[String]) {
    val game = new Game()(DataBindingModule)
    game.initialize()
    game.start()
  }

}
