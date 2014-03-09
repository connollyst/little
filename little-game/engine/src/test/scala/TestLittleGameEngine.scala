import com.quane.little.game.LittleGameEngine
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

/**
 *
 *
 * @author Sean Connolly
 */
@RunWith(classOf[JUnitRunner])
class TestLittleGameEngine extends FunSuite {

  test("test function reference with return from print statement") {
    val game = new LittleGameEngine
    game.initialize()
  }

}
