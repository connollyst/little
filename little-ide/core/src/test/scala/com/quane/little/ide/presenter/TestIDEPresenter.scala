package com.quane.little.ide.presenter

import org.scalatest.WordSpec
import org.scalatest.mock.MockitoSugar
import com.quane.little.ide.view.MockIDEView
import org.mockito.Mockito._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import com.quane.little.ide.MockIDEBindingModule

/** Test cases for the [[com.quane.little.ide.presenter.IDEPresenter]].
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestIDEPresenter extends WordSpec with MockitoSugar {

  implicit val bindingModule = MockIDEBindingModule

  "IDEPresenter" should {
    "register itself with view immediately" in {
      val view = MockIDEView.mocked()
      val presenter = new IDEPresenter(view)
      verify(view).registerViewPresenter(presenter)
    }
    "create the toolbox immediately" in {
      val view = MockIDEView.mocked()
      new IDEPresenter(view)
      verify(view).createToolbox()
    }
    "create the workspace immediately" in {
      val view = MockIDEView.mocked()
      new IDEPresenter(view)
      verify(view).createWorkspace()
    }
  }

}
