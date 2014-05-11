package com.quane.little.data.service

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import com.quane.little.language.{SetStatement, GetStatement, PrintStatement}
import com.quane.little.data.model.RecordId
import com.quane.little.language.data.Value

/** Test cases for the [[com.quane.little.data.service.ListenerService]]
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestPrimitiveService extends FlatSpec with ShouldMatchers {

  val service = new PrimitiveService

  "PrimitiveService" should "support all primitives" in {
    PrimitiveService.Primitives foreach {
      primitive => service.findPrimitive(new RecordId(primitive))
    }
  }
  it should "find get primitive" in {
    val id = new RecordId(PrimitiveService.PrimitiveGet)
    val primitive = service.findPrimitive(id)
    primitive should be(new GetStatement(""))
  }
  it should "find set primitive" in {
    val id = new RecordId(PrimitiveService.PrimitiveSet)
    val primitive = service.findPrimitive(id)
    primitive should be(new SetStatement("", Value("")))
  }
  it should "find print primitive" in {
    val id = new RecordId(PrimitiveService.PrimitivePrint)
    val primitive = service.findPrimitive(id)
    primitive should be(new PrintStatement(Value("")))
  }


}
