package com.quane.little.data.service

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import com.quane.little.language.{SetStatement, PrintStatement}
import com.quane.little.data.model.RecordId
import com.quane.little.language.data.Value

/** Test cases for the [[com.quane.little.data.service.ListenerService]]
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestPrimitiveService extends FlatSpec with ShouldMatchers {

  val service = new StatementService

  "PrimitiveService" should "support all primitives" in {
    StatementService.Primitives foreach {
      primitive => service.findPrimitive(new RecordId(primitive))
    }
  }
  it should "find set primitive" in {
    val id = new RecordId(StatementService.PrimitiveSet)
    val primitive = service.findPrimitive(id)
    primitive should be(new SetStatement("", Value("")))
  }
  it should "find print primitive" in {
    val id = new RecordId(StatementService.PrimitivePrint)
    val primitive = service.findPrimitive(id)
    primitive should be(new PrintStatement(Value("")))
  }


}
