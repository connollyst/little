package com.quane.little.data.service

import com.quane.little.data.model.PrimitiveId
import com.quane.little.language.data.Value
import com.quane.little.language.{Conditional, Getter, Logic, LogicOperation}
import org.junit.runner.RunWith
import org.scalatest.WordSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers

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
        expression => service.find(new PrimitiveId(expression))
      }
    }
    "find get expressions" in {
      val id = new PrimitiveId(PrimitiveService.Get)
      val expression = service.find(id)
      expression should be(new Getter(""))
    }
    "find if/else expressions" in {
      val id = new PrimitiveId(PrimitiveService.Conditional)
      val expression = service.find(id)
      expression should be(new Conditional(new Logic(Value(1), LogicOperation.Equals, Value(1))))
    }
  }

}
