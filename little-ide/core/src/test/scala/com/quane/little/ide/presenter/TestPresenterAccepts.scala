package com.quane.little.ide.presenter

import com.quane.little.data.model.{PrimitiveId, CodeRecord, Id}
import com.quane.little.data.service.{BasicPrimitiveService, PrimitiveService}
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

  // TODO rewrite tests focused not on get/set/print but on returnType directly

  "SetterView" should {
    "accept get" in {
      val get = primitive(PrimitiveService.Get)
      val presenter = new SetterPresenter(mock[SetterView])
      val accepts = PresenterAccepts.accepts(presenter, get.code.returnType)
      accepts should be(right = true)
    }
    "not accept set" in {
      val set = primitive(PrimitiveService.Set)
      val presenter = new SetterPresenter(mock[SetterView])
      val accepts = PresenterAccepts.accepts(presenter, set.code.returnType)
      accepts should be(right = false)
    }
    "not accept print" in {
      val print = primitive(PrimitiveService.Print)
      val presenter = new SetterPresenter(mock[SetterView])
      val accepts = PresenterAccepts.accepts(presenter, print.code.returnType)
      accepts should be(right = false)
    }
    "not accept conditional" in {
      val conditional = primitive(PrimitiveService.Conditional)
      val presenter = new SetterPresenter(mock[SetterView])
      val accepts = PresenterAccepts.accepts(presenter, conditional.code.returnType)
      accepts should be(right = true)
    }
  }

  // TODO test more recipients!!

  private def primitive(id: String): CodeRecord =
    new BasicPrimitiveService().findRecord(new PrimitiveId(id))

}
