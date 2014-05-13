package com.quane.little.data.service

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import com.quane.little.language.{SetStatement, PrintStatement}
import com.quane.little.data.model.RecordId
import com.quane.little.language.data.Value

/** Test cases for the [[com.quane.little.data.service.StatementService]]
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestStatementService extends FlatSpec with ShouldMatchers {

  val service = new MongoStatementService

  "StatementService" should "support all statements" in {
    StatementService.All foreach {
      primitive => service.findStatement(new RecordId(primitive))
    }
  }
  it should "find set statement" in {
    val id = new RecordId(StatementService.Set)
    val primitive = service.findStatement(id)
    primitive should be(new SetStatement("", Value("")))
  }
  it should "find print statement" in {
    val id = new RecordId(StatementService.Print)
    val primitive = service.findStatement(id)
    primitive should be(new PrintStatement(Value("")))
  }


}
