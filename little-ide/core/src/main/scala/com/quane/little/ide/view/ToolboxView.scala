package com.quane.little.ide.view

import com.quane.little.data.model.RecordId
import com.quane.little.data.model.FunctionCategory._
import com.quane.little.data.model.CodeCategory.CodeCategory

trait ToolboxView extends View[ToolboxViewPresenter] {

  def setSelectedTab(category: CodeCategory): Unit

  def createToolboxTab(category: CodeCategory): Unit

  def createToolboxTab(category: CodeCategory, subcategory: FunctionCategory): Unit

  def createToolboxItem(category: CodeCategory, title: String, functionId: RecordId): Unit

  def createToolboxItem(category: CodeCategory, subcategory: FunctionCategory, title: String, functionId: RecordId): Unit

}

trait ToolboxViewPresenter extends ViewPresenter
