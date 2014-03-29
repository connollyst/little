package com.quane.little.game.server

import org.junit.runner.RunWith
import org.junit.Assert._
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito._
import org.mockito.Matchers._
import scala.collection.mutable
import com.quane.little.game.Game
import com.quane.little.game.entity.Entity
import com.smartfoxserver.v2.mmo.MMOItem

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
    val game = mock[Game]
    when(game.entities).thenReturn(
      mutable.HashMap[String, Entity](
        "a" -> mock[Entity],
        "b" -> mock[Entity],
        "c" -> mock[Entity])
    )
    val manager = new GameManager(mock[ClientCommunicator], game)
    manager.init()
    assertEquals(3, manager.items.size)
  }

  test("test item removed from client") {
    val client = mock[ClientCommunicator]
    val manager = new GameManager(client)
    val id = "abcd"
    val entity = mock[Entity]
    when(entity.id).thenReturn(id)
    val item = mock[MMOItem]
    manager.items += (id -> item)
    manager.entityRemoved(entity)
    verify(client).removeItem(any())
  }

}
