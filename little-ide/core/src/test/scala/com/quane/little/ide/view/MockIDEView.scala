package com.quane.little.ide.view

import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito._
import com.quane.little.ide.view.MockView._

object MockIDEView extends MockitoSugar {

  def apply(): MockIDEView = new MockIDEView

  def mocked(): IDEView = {
    val view = mock[IDEView]
    when(view.createToolbox()).then(answer(MockToolboxView.apply))
    when(view.createWorkspace()).then(answer(MockWorkspaceView.apply))
    when(view.createGamespace()).then(answer(MockGamespaceView.apply))
    view
  }

}

/** An implementation of [[com.quane.little.ide.view.IDEView]] for testing purposes.
  *
  * @author Sean Connolly
  */
class MockIDEView extends IDEView with MockView {

  override def createToolbox(): ToolboxView = new MockToolboxView

  override def createWorkspace(): WorkspaceView = new MockWorkspaceView

  override def createGamespace(): GamespaceView = new MockGamespaceView

}
