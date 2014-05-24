package com.quane.little.ide.view

object MockWorkspaceView {
  def apply(): MockWorkspaceView = new MockWorkspaceView
}

/** An implementation of [[com.quane.little.ide.view.WorkspaceView]] for testing
  * purposes.
  *
  * @author Sean Connolly
  */
class MockWorkspaceView extends WorkspaceView with MockView {

  override def createFunctionDefinition(): FunctionDefinitionView = new MockFunctionDefinitionView

  override def createEventListener(): EventListenerView = new MockEventListenerView

}
