package com.quane.little.ide.view

import com.quane.little.data.model.CodeCategory.CodeCategory
import com.quane.little.data.model.Id

object MockToolboxView {
  def apply(): MockToolboxView = new MockToolboxView
}

/** An implementation of [[com.quane.little.ide.view.ToolboxView]] for testing purposes.
  *
  * @author Sean Connolly
  */
class MockToolboxView extends ToolboxView with MockView {

  override def setSelectedTab(category: CodeCategory) = Unit

  override def createToolboxTab(category: CodeCategory) = Unit

  override def createToolboxItem(category: CodeCategory, title: String, codeId: Id) = Unit
}