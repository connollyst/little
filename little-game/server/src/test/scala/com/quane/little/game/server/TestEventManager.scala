package com.quane.little.game.server

import com.quane.little.game.server.events.{JoinEventHandler, ServerReadyEventHandler, IDEConnectionHandler, LittleEvents}
import org.junit.runner.RunWith
import org.mockito.Matchers._
import org.mockito.Mockito._
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.scalatest.mock.MockitoSugar

/** Test cases for [[EventManager]].
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestEventManager
  extends FunSuite
  with MockitoSugar {

  test("event handler added: user join room") {
    val client = mock[ClientCommunicator]
    val manager = new EventManager(client)
    manager.init()
    verify(client).addHandler(
      same(LittleEvents.SERVER_READY),
      any[JoinEventHandler]
    )
  }

  test("event handler added: server ready") {
    val client = mock[ClientCommunicator]
    val manager = new EventManager(client)
    manager.init()
    verify(client).addHandler(
      same(LittleEvents.SERVER_READY),
      any[ServerReadyEventHandler]
    )
  }

  test("event handler added: ide auth") {
    val client = mock[ClientCommunicator]
    val manager = new EventManager(client)
    manager.init()
    verify(client).addHandler(
      same(LittleEvents.IDE_AUTH),
      any[IDEConnectionHandler]
    )
  }

}
