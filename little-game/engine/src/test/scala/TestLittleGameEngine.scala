import com.quane.little.game.LittleGameEngine
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

/** Test cases for the [[com.quane.little.game.LittleGameEngine]].
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestLittleGameEngine extends FunSuite {

  test("test engine initialization") {
    new LittleGameEngine().initialize()
  }

}
