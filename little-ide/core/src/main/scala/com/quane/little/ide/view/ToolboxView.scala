package com.quane.little.ide.view

import com.quane.little.data.model.RecordId
import com.quane.little.data.model.CodeCategory.CodeCategory

trait ToolboxView extends View[ToolboxViewPresenter] {

  def createToolboxTab(category: CodeCategory)

  def createToolboxItem(category: CodeCategory, title: String, functionId: RecordId)

}

trait ToolboxViewPresenter extends ViewPresenter
