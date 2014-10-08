package com.quane.little.ide.presenter

import com.quane.little.ide.MockIDEBindingModule
import com.quane.little.language.data.ValueType
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import com.quane.little.data.service.{BasicStatementService, BasicExpressionService, ExpressionService, StatementService}
import com.quane.little.data.model.{PrimitiveRecord, RecordId}
import com.quane.little.ide.view._
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito._

@RunWith(classOf[JUnitRunner])
class TestPresenterAccepts extends FlatSpec with ShouldMatchers with MockitoSugar {

  implicit val bindingModule = MockIDEBindingModule

  "SetterView" should "accept get" in {
    val get = expression(ExpressionService.Get)
    val view = mock[SetterView]
    val presenter = new SetterPresenter(view)
    when(view.presenter).thenReturn(presenter)

    val accepts = PresenterAccepts.accepts(view, get)
    accepts should be(right = true)
  }
  it should "not accept set" in {
    val set = statement(StatementService.Set)
    val view = mock[SetterView]
    val presenter = new SetterPresenter(view)
    when(view.presenter).thenReturn(presenter)
    val accepts = PresenterAccepts.accepts(view, set)
    accepts should be(right = false)
  }
  it should "not accept print" in {
    val print = statement(StatementService.Print)
    val view = mock[SetterView]
    val presenter = new SetterPresenter(view)
    when(view.presenter).thenReturn(presenter)
    val accepts = PresenterAccepts.accepts(view, print)
    accepts should be(right = false)
  }
  it should "not accept conditional" in {
    val conditional = expression(ExpressionService.Conditional)
    val view = mock[SetterView]
    val presenter = new SetterPresenter(view)
    when(view.presenter).thenReturn(presenter)
    val accepts = PresenterAccepts.accepts(view, conditional)
    accepts should be(right = true)
  }

  private def expression(id: String): PrimitiveRecord = new BasicExpressionService().find(new RecordId(id))

  private def statement(id: String): PrimitiveRecord = new BasicStatementService().find(new RecordId(id))

}
