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

  test("entity is marked as removed after clean") {
    val remover = new EntityRemover()
    val entity = mock[Entity]
    remover.remove(entity)
    remover.cleanAll()
    verify(entity).isRemoved_=(true)
  }

  test("entity is not marked as removed until cleaned") {
    val remover = new EntityRemover()
    val entity = mock[Entity]
    remover.remove(entity)
    verify(entity, never).isRemoved_=(true)
  }

  test("listener is notified of removal") {
    val remover = new EntityRemover()
    val listener = mock[EntityRemovalListener]
    val entity = mock[Entity]
    remover.add(listener)
    remover.remove(entity)
    remover.cleanAll()
    verify(listener).entityRemoved(entity)
  }

  test("listener is not notified of removal until cleaned") {
    val remover = new EntityRemover()
    val listener = mock[EntityRemovalListener]
    val entity = mock[Entity]
    remover.add(listener)
    remover.remove(entity)
    verify(listener, never).entityRemoved(entity)
  }

  test("listeners are notified of many removals") {
    val remover = new EntityRemover()
    val listener1 = mock[EntityRemovalListener]
    val listener2 = mock[EntityRemovalListener]
    val listener3 = mock[EntityRemovalListener]
    val entityA = mock[Entity]
    val entityB = mock[Entity]
    val entityC = mock[Entity]
    remover.add(listener1)
    remover.add(listener2)
    remover.add(listener3)
    remover.remove(entityA)
    remover.remove(entityB)
    remover.remove(entityC)
    remover.cleanAll()
    verify(listener1).entityRemoved(entityA)
    verify(listener1).entityRemoved(entityA)
    verify(listener1).entityRemoved(entityA)
    verify(listener2).entityRemoved(entityB)
    verify(listener2).entityRemoved(entityB)
    verify(listener2).entityRemoved(entityB)
    verify(listener3).entityRemoved(entityC)
    verify(listener3).entityRemoved(entityC)
    verify(listener3).entityRemoved(entityC)
  }

}
