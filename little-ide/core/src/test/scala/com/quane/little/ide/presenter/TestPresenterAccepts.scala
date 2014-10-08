package com.quane.little.ide.presenter

import com.quane.little.data.model.{PrimitiveRecord, RecordId}
import com.quane.little.data.service.{BasicCodeService, CodeService}
import com.quane.little.ide.MockIDEBindingModule
import com.quane.little.ide.view._
import org.junit.runner.RunWith
import org.scalatest.WordSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.mock.MockitoSugar

@RunWith(classOf[JUnitRunner])
class TestPresenterAccepts extends WordSpec with ShouldMatchers with MockitoSugar {

  implicit val bindingModule = MockIDEBindingModule

  "SetterView" should {
    "accept get" in {
      val get = primitive(CodeService.Get)
      val presenter = new SetterPresenter(mock[SetterView])
      val accepts = PresenterAccepts.acceptsPrimitive(presenter, get)
      accepts should be(right = true)
    }
    "not accept set" in {
      val set = primitive(CodeService.Set)
      val presenter = new SetterPresenter(mock[SetterView])
      val accepts = PresenterAccepts.acceptsPrimitive(presenter, set)
      accepts should be(right = false)
    }
    "not accept print" in {
      val print = primitive(CodeService.Print)
      val presenter = new SetterPresenter(mock[SetterView])
      val accepts = PresenterAccepts.acceptsPrimitive(presenter, print)
      accepts should be(right = false)
    }
    "not accept conditional" in {
      val conditional = primitive(CodeService.Conditional)
      val presenter = new SetterPresenter(mock[SetterView])
      val accepts = PresenterAccepts.acceptsPrimitive(presenter, conditional)
      accepts should be(right = true)
    }
  }

  // TODO test more cases!!

  private def primitive(id: String): PrimitiveRecord = new BasicCodeService().findRecord(new RecordId(id))

}
