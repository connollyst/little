package com.quane.little.game.server

import org.junit.runner.RunWith
import org.junit.Assert._
import org.mockito.Mockito._
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.scalatest.mock.MockitoSugar
import com.quane.little.game.entity.Mob
import com.smartfoxserver.v2.mmo.IMMOItemVariable

/** Test cases for the [[ItemSerializer]].
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestItemSerializer
  extends FunSuite
  with MockitoSugar {

  val serializer = new ItemSerializer

  test("test mob id serialized") {
    val id = "abcd"
    val mob = mock[Mob]
    when(mob.id).thenReturn(id)
    val variables = serializer.serialize(mob)
    getMMOItemVariable(variables, "id") match {
      case None => fail("expected id")
      case Some(variable) => assertEquals(id, variable.getStringValue)
    }
  }

  test("test mob speed serialized") {
    val speed = 1234
    val mob = mock[Mob]
    when(mob.speed).thenReturn(speed)
    val variables = serializer.serialize(mob)
    getMMOItemVariable(variables, "s") match {
      case None => fail("expected speed")
      case Some(variable) => assertEquals(speed, variable.getIntValue)
    }
  }

  test("test mob direction serialized") {
    val direction = 1234
    val mob = mock[Mob]
    when(mob.direction).thenReturn(direction)
    val variables = serializer.serialize(mob)
    getMMOItemVariable(variables, "d") match {
      case None => fail("expected direction")
      case Some(variable) => assertEquals(direction, variable.getIntValue)
    }
  }

  private def getMMOItemVariable(variables: List[IMMOItemVariable], name: String): Option[IMMOItemVariable] = {
    variables.foreach {
      variable =>
        if (variable.getName == name) {
          return Some(variable)
        }
    }
    None
  }

}
