package com.quane.little.game.server

import com.quane.little.game.{TimedUpdater, Game}
import com.quane.little.game.entity.{EntityRemover, Entity}
import com.smartfoxserver.v2.mmo.{Vec3D, MMOItem}
import org.junit.Assert._
import org.junit.runner.RunWith
import org.mockito.Matchers._
import org.mockito.Mockito._
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.mock.MockitoSugar
import scala.collection.mutable
import org.scalatest.matchers.ShouldMatchers
import org.mockito.ArgumentCaptor
import org.mockito.Matchers.{eq => mockEq}

/** Test cases for [[GameManager]]
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestGameManager extends FlatSpec with ShouldMatchers with MockitoSugar {

  "GameManager" should "register as entity removal listener" in {
    val client = mock[ClientCommunicator]
    val game = mockGame()
    val manager = new GameManager(client, game)
    verify(game.cleaner).register(manager)
  }

  it should "initialize game" in {
    val client = mock[ClientCommunicator]
    val game = mockGame()
    val manager = new GameManager(client, game)
    manager.init()
    verify(game).initialize()
  }

  it should "initialize game entity" in {
    val client = mock[ClientCommunicator]
    val game = mockGame("a", "b", "c")
    val manager = new GameManager(client, game)
    manager.init()
    assertEquals(3, manager.items.size)
  }

  it should "remove entity from client" in {
    val client = mock[ClientCommunicator]
    val manager = new GameManager(client, mockGame())
    val id = "a"
    val item = mock[MMOItem]
    val entity = mockEntity(id)
    manager.items += (id -> item)
    manager.entityRemoved(entity)
    verify(client).removeItem(item)
  }

  it should "not remove unknown entity from client" in {
    val client = mock[ClientCommunicator]
    val manager = new GameManager(client, mockGame())
    manager.entityRemoved(mockEntity("unknown"))
    verify(client, never).removeItem(any[MMOItem])
  }

  it should "send entity to client" in {
    val client = mock[ClientCommunicator]
    val game = mockGame("a", "b", "c")
    val manager = new GameManager(client, game)
    manager.items += ("a" -> mock[MMOItem])
    manager.items += ("b" -> mock[MMOItem])
    manager.items += ("c" -> mock[MMOItem])
    manager.sendItems()
    verify(client, times(3)).setItemPosition(any[MMOItem], any[Vec3D])
  }

  it should "send entity coordinates to client" in {
    val client = mock[ClientCommunicator]
    val game = mockGame("a")
    val manager = new GameManager(client, game)
    val x = 12.34f
    val y = 45.67f
    val id = "a"
    val item = mock[MMOItem]
    val entity = game.entity(id)
    when(entity.x).thenReturn(x)
    when(entity.y).thenReturn(y)
    manager.items += (id -> item)
    manager.sendItems()
    val position = ArgumentCaptor.forClass(classOf[Vec3D])
    verify(client).setItemPosition(mockEq(item), position.capture)
    assertEquals(x, position.getValue.floatX, 0)
    assertEquals(y, position.getValue.floatY, 0)
  }

  it should "gracefully handle exceptions while sending items" in {
    val client = mock[ClientCommunicator]
    val game = mockGame("a")
    val manager = new GameManager(client, game)
    manager.items += ("a" -> mock[MMOItem])
    when(client.setItemPosition(any[MMOItem], any[Vec3D])).thenThrow(classOf[Exception])
    // Exception should be caught safely..
    manager.sendItems()
  }

  it should "start updater when starting" in {
    val client = mock[ClientCommunicator]
    val thread = mock[TimedUpdater]
    val manager = new GameManager(client, mockGame()) {
      override val updater = thread
    }
    manager.start()
    Thread.sleep(100)
    verify(thread).run()
  }

  it should "stop updater when stopping" in {
    val client = mock[ClientCommunicator]
    val thread = mock[TimedUpdater]
    val manager = new GameManager(client, mockGame()) {
      override val updater = thread
    }
    manager.start()
    Thread.sleep(100)
    manager.stop()
    verify(thread).stop()
  }


  private def mockGame(ids: String*): Game = {
    val game = mock[Game]
    when(game.cleaner).thenReturn(mock[EntityRemover])
    val entities = mockEntities(ids: _*)
    when(game.entities).thenReturn(entities)
    when(game.entity(anyString())).thenCallRealMethod()
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
