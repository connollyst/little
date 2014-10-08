package com.quane.little.ide.presenter

import com.quane.little.data.model.{PrimitiveRecord, RecordId}
import com.quane.little.data.service.{BasicCodeService, CodeService}
import com.quane.little.ide.MockIDEBindingModule
import com.quane.little.ide.view._
import org.junit.runner.RunWith
import org.mockito.Mockito._
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.mock.MockitoSugar

@RunWith(classOf[JUnitRunner])
class TestPresenterAccepts extends FlatSpec with ShouldMatchers with MockitoSugar {

  implicit val bindingModule = MockIDEBindingModule

  "SetterView" should "accept get" in {
    val get = primitive(CodeService.Get)
    val view = mock[SetterView]
    val presenter = new SetterPresenter(view)
    when(view.presenter).thenReturn(presenter)

    val accepts = PresenterAccepts.accepts(view, get)
    accepts should be(right = true)
  }
  it should "not accept set" in {
    val set = primitive(CodeService.Set)
    val view = mock[SetterView]
    val presenter = new SetterPresenter(view)
    when(view.presenter).thenReturn(presenter)
    val accepts = PresenterAccepts.accepts(view, set)
    accepts should be(right = false)
  }
  it should "not accept print" in {
    val print = primitive(CodeService.Print)
    val view = mock[SetterView]
    val presenter = new SetterPresenter(view)
    when(view.presenter).thenReturn(presenter)
    val accepts = PresenterAccepts.accepts(view, print)
    accepts should be(right = false)
  }
  it should "not accept conditional" in {
    val conditional = primitive(CodeService.Conditional)
    val view = mock[SetterView]
    val presenter = new SetterPresenter(view)
    when(view.presenter).thenReturn(presenter)
    val accepts = PresenterAccepts.accepts(view, conditional)
    accepts should be(right = true)
  }

  private def primitive(id: String): PrimitiveRecord = new BasicCodeService().findRecord(new RecordId(id))

}
