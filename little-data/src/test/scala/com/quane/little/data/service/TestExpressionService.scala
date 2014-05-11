package com.quane.little.data.service

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import com.quane.little.language.{Conditional, GetStatement}
import com.quane.little.data.model.RecordId
import com.quane.little.language.data.Value

/** Test cases for the [[com.quane.little.data.service.ListenerService]]
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestExpressionService extends FlatSpec with ShouldMatchers {

  val service = new StatementService

  "ExpressionService" should "support all expressions" in {
    ExpressionService.Primitives foreach {
      expression => service.findPrimitive(new RecordId(expression))
    }
  }
  it should "find get expressions" in {
    val id = new RecordId(ExpressionService.PrimitiveGet)
    val expression = service.findPrimitive(id)
    expression should be(new GetStatement(""))
  }
  it should "find if expressions" in {
    val id = new RecordId(ExpressionService.PrimitiveConditional)
    val expression = service.findPrimitive(id)
    expression should be(new Conditional(Value("")))
  }


}
