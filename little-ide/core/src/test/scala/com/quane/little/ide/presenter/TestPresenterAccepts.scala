package com.quane.little.ide.presenter

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import com.quane.little.data.service.{ExpressionService, StatementService}
import com.quane.little.data.model.{PrimitiveRecord, RecordId}
import com.quane.little.ide.view._
import org.scalatest.mock.MockitoSugar

@RunWith(classOf[JUnitRunner])
class TestPresenterAccepts extends FlatSpec with ShouldMatchers with MockitoSugar {

  "SetStatementView" should "accept get" in {
    val get = primitive(ExpressionService.PrimitiveGet)
    val view = mock[SetStatementView]
    val accepts = PresenterAccepts.acceptsPrimitive(view, get)
    accepts should be(right = true)
  }
  it should "not accept set" in {
    val set = primitive(StatementService.PrimitiveSet)
    val view = mock[SetStatementView]
    val accepts = PresenterAccepts.acceptsPrimitive(view, set)
    accepts should be(right = false)
  }
  it should "not accept print" in {
    val print = primitive(StatementService.PrimitivePrint)
    val view = mock[SetStatementView]
    val accepts = PresenterAccepts.acceptsPrimitive(view, print)
    accepts should be(right = false)
  }
  it should "not accept conditional" in {
    val conditional = primitive(ExpressionService.PrimitiveConditional)
    val view = mock[SetStatementView]
    val accepts = PresenterAccepts.acceptsPrimitive(view, conditional)
    accepts should be(right = false)
  }

  private def primitive(id: String): PrimitiveRecord =
    StatementService().find(new RecordId(id))

}
