package com.quane.little.ide.view

import com.quane.little.data.model.RecordId
import com.quane.little.data.model.FunctionCategory._
import com.quane.little.data.model.CodeCategory.CodeCategory

trait ToolboxView extends View[ToolboxViewPresenter] {

  def createToolboxTab(category: CodeCategory)

  def createToolboxTab(category: CodeCategory, subcategory: FunctionCategory)

  def createToolboxItem(category: CodeCategory, title: String, functionId: RecordId)

  def createToolboxItem(category: CodeCategory, subcategory: FunctionCategory, title: String, functionId: RecordId)

}

trait ToolboxViewPresenter extends ViewPresenter
