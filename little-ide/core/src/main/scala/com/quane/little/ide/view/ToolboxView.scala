package com.quane.little.ide.view

import com.quane.little.data.model.RecordId
import com.quane.little.data.model.CodeCategory._
import com.quane.little.data.model.CodeType.CodeType

trait ToolboxView extends View[ToolboxViewPresenter] {

  def setSelectedTab(category: CodeCategory): Unit

  def createToolboxTab(category: CodeCategory): Unit

  def createToolboxItem(category: CodeCategory, title: String, codeType: CodeType, codeId: RecordId): Unit

}

trait ToolboxViewPresenter extends ViewPresenter {

  def requestNewFunctionDefinition()

  def openFunctionDefinition(functionId: RecordId): Unit

}
