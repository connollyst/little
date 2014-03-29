package com.quane.little.game.server

import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.scalatest.mock.MockitoSugar
import com.smartfoxserver.v2.mmo.MMOItem
import com.quane.little.game.entity.Entity

import org.junit.runner.RunWith

import org.mockito.Matchers.any
import org.mockito.Mockito.verify
import org.mockito.Mockito._
import com.quane.little.game.Game

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
    val manager = new GameManager(mock[ClientCommunicator], game)
    manager.init()
    verify(game).initialize()
  }

  test("test item removed from client") {
    val client = mock[ClientCommunicator]
    val manager = new GameManager(client)
    val id = "abcd"
    val entity = mock[Entity]
    when(entity.id).thenReturn(id)
    val item = mock[MMOItem]
    manager.addItem(id, item)
    manager.entityRemoved(entity)
    verify(client).removeItem(any())
  }

}
