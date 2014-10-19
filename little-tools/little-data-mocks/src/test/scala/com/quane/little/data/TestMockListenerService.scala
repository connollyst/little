package com.quane.little.data

import org.scalatest.{BeforeAndAfterEach, WordSpec}
import com.quane.little.data.service.{ListenerService, UserService}
import com.escalatesoft.subcut.inject.Injectable
import org.scalatest.matchers.ShouldMatchers
import com.quane.little.data.model.{ListenerId, Id}
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import com.quane.little.language.event.{Event, EventListener}

/** Test cases for the [[com.quane.little.data.MockListenerService]].
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestMockListenerService extends WordSpec with ShouldMatchers with BeforeAndAfterEach with Injectable {

  implicit val bindingModule = MockDataBindingModule
  private val listenerService = inject[ListenerService]
  private val userService = inject[UserService]
  private val testUsernameA = "UserA"
  private val testUsernameB = "UserB"

  /**
   * Before each test, the services are wiped clean. The user service is
   * reinitialized with two mock users.
   */
  override def beforeEach() {
    listenerService.init()
    userService.init()
    userService.upsert(testUsernameA)
    userService.upsert(testUsernameB)
  }

  "MockDataBindingModule" should {
    "inject MockListenerService" in {
      listenerService.getClass should be(classOf[MockListenerService])
    }
  }

  "MockListenerService" should {
    "assign id on insert" in {
      val listener = listenerService.insert(
        testUsernameA, new EventListener(Event.OnSpawn)
      )
      listener.id should not be null
    }
    "assign unique id on insert" in {
      val listenerA1 = listenerService.insert(
        testUsernameA, new EventListener(Event.OnSpawn)
      )
      val listenerA2 = listenerService.insert(
        testUsernameA, new EventListener(Event.OnSpawn)
      )
      listenerA1.id should not be listenerA2.id
    }
    "maintain id on update" in {
      val originalListener = listenerService.insert(
        testUsernameA, new EventListener(Event.OnSpawn)
      )
      val updatedListener = listenerService.update(
        originalListener.id, new EventListener(Event.OnSpawn)
      )
      originalListener.id should be(updatedListener.id)
    }
    "error when updating with unknown id" in {
      val badId = "SomeFakeId"
      val error = intercept[IllegalArgumentException] {
        listenerService.update(
          new ListenerId(badId), new EventListener(Event.OnSpawn)
        )
      }
      error.getMessage should be("No listener: " + badId)
    }
    "remove all records on init" in {
      listenerService.insert(
        testUsernameA, new EventListener(Event.OnSpawn)
      )
      listenerService.insert(
        testUsernameA, new EventListener(Event.OnSpawn)
      )
      listenerService.insert(
        testUsernameB, new EventListener(Event.OnSpawn)
      )
      listenerService.insert(
        testUsernameB, new EventListener(Event.OnSpawn)
      )
      listenerService.init()
      listenerService.findByUser(testUsernameA).size should be(0)
      listenerService.findByUser(testUsernameB).size should be(0)
    }
    "find all records for each user" in {
      listenerService.insert(
        testUsernameA, new EventListener(Event.OnSpawn)
      )
      listenerService.insert(
        testUsernameA, new EventListener(Event.OnSpawn)
      )
      listenerService.insert(
        testUsernameB, new EventListener(Event.OnSpawn)
      )
      listenerService.insert(
        testUsernameB, new EventListener(Event.OnSpawn)
      )
      listenerService.findByUser(testUsernameA).size should be(2)
      listenerService.findByUser(testUsernameB).size should be(2)
    }
    "find listener by id" in {
      val record = listenerService.insert(
        testUsernameA, new EventListener(Event.OnSpawn)
      )
      val listener = listenerService.findListener(record.id)
      record.listener should be(listener)
    }
    "not find listener by unknown id" in {
      val badId = "SomeFakeId"
      val error = intercept[IllegalArgumentException] {
        listenerService.findListener(new ListenerId(badId))
      }
      error.getMessage should be("No listener: " + badId)
    }
  }

}
