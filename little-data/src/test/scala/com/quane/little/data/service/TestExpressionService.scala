package com.quane.little.data.service

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import com.quane.little.language.{LogicOperation, Logic, Conditional, Getter}
import com.quane.little.data.model.RecordId
import com.quane.little.language.data.Value

/** Test cases for the [[com.quane.little.data.service.ListenerService]]
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestExpressionService extends FlatSpec with ShouldMatchers {

  val service = new BasicCodeService

  "ExpressionService" should "support all expressions" in {
    CodeService.All foreach {
      expression => service.find(new RecordId(expression))
    }
  }
  it should "find get expressions" in {
    val id = new RecordId(CodeService.Get)
    val expression = service.find(id)
    expression should be(new Getter(""))
  }
  it should "find if/else expressions" in {
    val id = new RecordId(CodeService.Conditional)
    val expression = service.find(id)
    expression should be(new Conditional(new Logic(Value(1), LogicOperation.Equals, Value(1))))
  }


}
