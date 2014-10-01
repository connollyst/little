package com.quane.little.ide.presenter

import org.scalatest.WordSpec
import org.scalatest.mock.MockitoSugar
import com.quane.little.ide.view._
import org.mockito.Mockito._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import com.quane.little.ide.MockIDEBindingModule

/** Test cases for the [[com.quane.little.ide.presenter.ConditionalPresenter]].
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestConditionalPresenter extends WordSpec with MockitoSugar {

  implicit val bindingModule = MockIDEBindingModule

  "ConditionalPresenter" should {
    "register itself with its view" in {
      val view = MockConditionalView.mocked()
      val presenter = new ConditionalPresenter(view)
      verify(view).registerViewPresenter(presenter)
    }
  }

}
