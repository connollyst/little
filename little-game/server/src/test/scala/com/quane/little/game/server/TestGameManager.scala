package com.quane.little.game.server

import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.scalatest.mock.MockitoSugar
import com.smartfoxserver.v2.mmo.MMOItem
import com.quane.little.game.entity.{WorldEdge, Mob, Entity}

import org.junit.runner.RunWith

import org.mockito.Matchers.any
import org.mockito.Mockito.verify
import org.mockito.Mockito._
import com.quane.little.game.LittleGameEngine
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
    val game = mock[LittleGameEngine]
    when(game.walls).thenReturn(mutable.Map[String, WorldEdge]())
    when(game.players).thenReturn(mutable.Map[String, Mob]())
    when(game.entities).thenReturn(mutable.Map[String, Entity]())
    val manager = new GameManager(mock[ClientCommunicator], game)
    manager.init()
    verify(game).initialize()
  }

  test("test item removed from client") {
    val client = mock[ClientCommunicator]
    val manager = new GameManager(client)
    val id = "abcd"
    val entity = mock[Entity]
    when(entity.uuid).thenReturn(id)
    val item = mock[MMOItem]
    manager.addItem(id, item)
    manager.entityRemoved(entity)
    verify(client).removeItem(any())
  }

}
