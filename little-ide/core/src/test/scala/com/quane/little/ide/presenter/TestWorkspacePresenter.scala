package com.quane.little.ide.presenter

import org.scalatest.FunSuite
import org.scalatest.mock.MockitoSugar
import com.quane.little.ide.view.WorkspaceView
import org.mockito.Mockito._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import com.quane.little.ide.MockIDEBindingModule

@RunWith(classOf[JUnitRunner])
class TestWorkspacePresenter extends FunSuite with MockitoSugar {

  implicit val bindingModule = MockIDEBindingModule

  test("test listener registered") {
    val view = mock[WorkspaceView]
    val presenter = new WorkspacePresenter(view)
    verify(view).registerViewPresenter(presenter)
  }

}
