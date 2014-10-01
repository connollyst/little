package com.quane.little.ide.presenter

import org.scalatest.WordSpec
import org.scalatest.mock.MockitoSugar
import com.quane.little.ide.view._
import org.mockito.Mockito._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import com.quane.little.ide.MockIDEBindingModule

/** Test cases for the [[com.quane.little.ide.presenter.CodeMenuPresenter]].
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestCodeMenuPresenter extends WordSpec with MockitoSugar {

  implicit val bindingModule = MockIDEBindingModule

  "CodeMenu" should {
    "register itself with its view" in {
      val view = mock[CodeMenuView]
      val context = mock[PresenterAccepts]
      val presenter = new CodeMenuPresenter(view, context)
      verify(view).registerViewPresenter(presenter)
    }
  }

}
