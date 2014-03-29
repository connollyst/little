package com.quane.little.game.server

import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.scalatest.mock.MockitoSugar
import com.smartfoxserver.v2.mmo.MMOItem
import com.quane.little.game.entity.Entity

import org.junit.runner.RunWith

import org.mockito.Matchers.any
import org.mockito.Mockito.verify
import org.mockito.Mockito

/** Test cases for [[LittleExtension]]
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestGameManager
  extends FunSuite
  with MockitoSugar {

  test("test item removed") {
    val client = mock[ClientCommunicator]
    val manager = new GameManager(client)
    val id = "abcd"
    val entity = mock[Entity]
    Mockito.when(entity.uuid).thenReturn(id)
    val item = mock[MMOItem]
    manager.addItem(id, item)
    manager.entityRemoved(entity)
    verify(client).removeItem(any())
  }

}
