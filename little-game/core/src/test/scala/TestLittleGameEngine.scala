import com.quane.little.game.Game
import org.junit.runner.RunWith
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner

/** Test cases for the [[com.quane.little.game.Game]].
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestLittleGameEngine extends FlatSpec with ShouldMatchers {

  "Game" should "initialize without error" in {
    val game = new Game()
    game.initialize()
  }

  it should "update without error" in {
    val game = new Game()
    game.initialize()
    game.updateState()
  }

}
