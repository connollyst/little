package com.quane.little.data.service

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.WordSpec
import org.scalatest.matchers.ShouldMatchers
import com.quane.little.language.{LogicOperation, Logic, Conditional, Getter}
import com.quane.little.data.model.RecordId
import com.quane.little.language.data.Value

/** Test cases for the [[com.quane.little.data.service.PrimitiveService]]
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestPrimitiveService extends WordSpec with ShouldMatchers {

  val service = new BasicPrimitiveService

  "ExpressionService" should {
    "support all expressions" in {
      PrimitiveService.All foreach {
        expression => service.find(new RecordId(expression))
      }
    }
    "find get expressions" in {
      val id = new RecordId(PrimitiveService.Get)
      val expression = service.find(id)
      expression should be(new Getter(""))
    }
    "find if/else expressions" in {
      val id = new RecordId(PrimitiveService.Conditional)
      val expression = service.find(id)
      expression should be(new Conditional(new Logic(Value(1), LogicOperation.Equals, Value(1))))
    }
  }

}
