import com.quane.little.game.Game
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

/** Test cases for the [[com.quane.little.game.Game]].
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestLittleGameEngine
  extends FunSuite {

  test("test engine initialization") {
    new Game().initialize()
  }

}
