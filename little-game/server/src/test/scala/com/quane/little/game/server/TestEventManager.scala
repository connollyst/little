package com.quane.little.game.server

import com.quane.little.game.server.events.{JoinEventHandler, ServerReadyEventHandler, LittleEvents}
import org.junit.runner.RunWith
import org.mockito.Matchers._
import org.mockito.Mockito._
import org.scalatest.WordSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.mock.MockitoSugar
import com.quane.little.data.MockDataBindingModule

/** Test cases for [[com.quane.little.game.server.EventManager]].
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestEventManager extends WordSpec with MockitoSugar {

  implicit val bindingModule = MockDataBindingModule

  "EventManager" should {
    "event handler added: user join room" in {
      val client = mock[ClientCommunicator]
      val manager = new EventManager(client)
      manager.init()
      verify(client).addHandler(
        same(LittleEvents.SERVER_READY),
        any[JoinEventHandler]
      )
    }
    "event handler added: server ready" in {
      val client = mock[ClientCommunicator]
      val manager = new EventManager(client)
      manager.init()
      verify(client).addHandler(
        same(LittleEvents.SERVER_READY),
        any[ServerReadyEventHandler]
      )
    }
  }

}
