package com.quane.little.ide.view

import com.quane.little.data.model.CodeCategory._
import com.quane.little.data.model.{FunctionId, Id}

trait ToolboxView extends View[ToolboxViewPresenter] {

  def setSelectedTab(category: CodeCategory): Unit

  def createToolboxTab(category: CodeCategory): Unit

  def createToolboxItem(category: CodeCategory, title: String, codeId: Id): Unit

}

trait ToolboxViewPresenter extends ViewPresenter {

  def requestNewFunctionDefinition(category: CodeCategory)

  def openFunctionDefinition(id: FunctionId): Unit

}
