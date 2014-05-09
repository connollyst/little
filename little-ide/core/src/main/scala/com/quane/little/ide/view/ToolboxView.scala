package com.quane.little.ide.view

import com.quane.little.data.model.RecordId

trait ToolboxView extends View[ToolboxViewPresenter] {

  def createToolboxTab(category: String)

  def createToolboxItem(category: String, title: String, functionId: RecordId)

}

trait ToolboxViewPresenter extends ViewPresenter
