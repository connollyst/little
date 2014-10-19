package com.quane.little.data.model

import com.quane.little.language._
import com.quane.little.tools.json.LittleJSON
import org.junit.runner.RunWith
import org.scalatest.WordSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import org.skyscreamer.jsonassert.JSONAssert
import java.io.{FileNotFoundException, File}
import java.nio.charset.Charset
import java.net.URL
import com.google.common.io.Files
import com.quane.little.language.event.{EventListener, Event}
import com.quane.little.language.math.Addition
import com.quane.little.language.data.NumberValue

/** Test cases for the [[com.quane.little.data.model.ListenerRecord]].
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestListenerRecord extends WordSpec with ShouldMatchers {

  val littleJSON = new LittleJSON()
  val userId = new UserId("UserRecordID")
  val category = CodeCategory.Misc
  // A blank event listener
  val blankListener = new EventListener(Event.OnSpawn)
  val blankListenerId = new ListenerId("BlankListenerRecordID")
  val blankRecord = new ListenerRecord(userId, blankListener)
  blankRecord.id = blankListenerId
  // A more complex event listener
  val complexListener = new EventListener(Event.OnSpawn)
  complexListener.addStep(new Setter(
    Operable.DIRECTION,
    new Addition(
      new Getter(Operable.DIRECTION),
      new NumberValue(180)
    ))
  )
  val complexListenerId = new ListenerId("ComplexListenerRecordID")
  val complexRecord = new ListenerRecord(userId, complexListener)
  complexRecord.id = complexListenerId

  classOf[ListenerRecord].getSimpleName should {
    "serialize blank record to JSON" in {
      val expected = getJSON("listener_blank")
      val actual = littleJSON.serialize(blankRecord)
      JSONAssert.assertEquals(expected, actual, true)
    }
    "deserialize record id from JSON" in {
      val actual = littleJSON.deserialize[ListenerRecord](getJSON("listener_blank"))
      actual.id should be(blankListenerId)
    }
    "deserialize owner record id from JSON" in {
      val actual = littleJSON.deserialize[ListenerRecord](getJSON("listener_blank"))
      actual.ownerId should be(userId)
    }
    "deserialize listener from JSON" in {
      val actual = littleJSON.deserialize[ListenerRecord](getJSON("listener_blank"))
      actual.listener should be(blankListener)
    }
    "deserialize from JSON" in {
      val actual = littleJSON.deserialize[ListenerRecord](getJSON("listener_blank"))
      actual should be(blankRecord)
    }
    "serialize complex record to JSON" in {
      val expected = getJSON("listener_complex")
      val actual = littleJSON.serialize(complexRecord)
      println(actual)
      JSONAssert.assertEquals(expected, actual, true)
    }
  }

  private[data] def getJSON(name: String): String = {
    val path = "json/" + name + ".json"
    getClass.getClassLoader.getResource(path) match {
      case url: URL =>
        Files.toString(new File(url.getFile), Charset.defaultCharset())
      case _ =>
        throw new FileNotFoundException(path)
    }
  }

}
