package com.quane.little.game.server

import com.quane.little.game.Game
import com.quane.little.game.entity.{EntityRemover, Entity}
import com.smartfoxserver.v2.mmo.{Vec3D, MMOItem}
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

  test("game initialized") {
    val client = mock[ClientCommunicator]
    val game = mockGame()
    val manager = new GameManager(client, game)
    manager.init()
    verify(game).initialize()
  }

  test("game items initialized") {
    val client = mock[ClientCommunicator]
    val game = mockGame("x", "y", "z")
    val manager = new GameManager(client, game)
    manager.init()
    assertEquals(3, manager.items.size)
  }

  test("item removed from client") {
    val client = mock[ClientCommunicator]
    val manager = new GameManager(client)
    val id = "a"
    val item = mock[MMOItem]
    val entity = mockEntity(id)
    manager.items += (id -> item)
    manager.entityRemoved(entity)
    verify(client).removeItem(item)
  }

  test("send items") {
    val client = mock[ClientCommunicator]
    val game = mockGame("x", "y", "z")
    val manager = new GameManager(client, game)
    manager.items += ("x" -> mock[MMOItem])
    manager.items += ("y" -> mock[MMOItem])
    manager.items += ("z" -> mock[MMOItem])
    manager.sendItems()
    verify(client, times(3)).setItemPosition(any[MMOItem], any[Vec3D])
  }

  private def mockGame(ids: String*): Game = {
    val game = mock[Game]
    val entities = mockEntities(ids: _*)
    when(game.entities).thenReturn(entities)
    when(game.entity(anyString())).thenCallRealMethod()
    when(game.cleaner).thenReturn(mock[EntityRemover])
    game
  }

  private def mockEntities(ids: String*): mutable.Map[String, Entity] = {
    val entities = mutable.HashMap[String, Entity]()
    ids foreach {
      id =>
        val entity = mockEntity(id)
        entities += (id -> entity)
    }
    entities
  }

  private def mockEntity(id: String): Entity = {
    val entity = mock[Entity]
    when(entity.id).thenReturn(id)
    entity
  }

}
