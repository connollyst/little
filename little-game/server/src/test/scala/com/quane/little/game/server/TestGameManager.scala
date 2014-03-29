package com.quane.little.game.server

import com.quane.little.game.Game
import com.quane.little.game.entity.Entity
import com.smartfoxserver.v2.mmo.MMOItem
import org.junit.Assert._
import org.junit.runner.RunWith
import org.mockito.Matchers._
import org.mockito.Mockito._
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.scalatest.mock.MockitoSugar
import scala.collection.mutable

/** Test cases for [[LittleExtension]]
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestGameManager
  extends FunSuite
  with MockitoSugar {

  test("test game initialized") {
    val game = mock[Game]
    when(game.entities).thenReturn(mutable.Map[String, Entity]())
    val manager = new GameManager(mock[ClientCommunicator], game)
    manager.init()
    verify(game).initialize()
  }

  test("test game items initialized") {
    val entityA = mockEntity("a")
    val entityB = mockEntity("b")
    val entityC = mockEntity("c")
    val game = mock[Game]
    when(game.entities).thenReturn(
      mutable.HashMap[String, Entity](
        "a" -> entityA,
        "b" -> entityB,
        "c" -> entityC)
    )
    val manager = new GameManager(mock[ClientCommunicator], game)
    manager.init()
    assertEquals(3, manager.items.size)
  }

  test("test item removed from client") {
    val client = mock[ClientCommunicator]
    val manager = new GameManager(client)
    val id = "abcd"
    manager.items += (id -> mock[MMOItem])
    manager.entityRemoved(mockEntity(id))
    verify(client).removeItem(any())
  }

  private def mockEntity(id: String): Entity = {
    val entity = mock[Entity]
    when(entity.id).thenReturn(id)
    entity
  }

}
