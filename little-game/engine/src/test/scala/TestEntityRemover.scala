import com.quane.little.game.entity.{Entity, EntityRemovalListener, EntityRemover}
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito._

/** Test cases for the [[com.quane.little.game.entity.EntityRemover]].
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestEntityRemover
  extends FunSuite
  with MockitoSugar {

  test("listener is notified of removal") {
    val remover = new EntityRemover()
    val listener = mock[EntityRemovalListener]
    val entity = mock[Entity]
    remover.add(listener)
    remover.remove(entity)
    remover.cleanAll()
    verify(listener).entityRemoved(entity)
  }

}
