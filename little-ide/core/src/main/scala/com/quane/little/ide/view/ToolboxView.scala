package com.quane.little.ide.view

import com.quane.little.data.model.RecordId

trait ToolboxView extends View[ToolboxViewPresenter] {

  def createToolboxTab(title: String)

  def createToolboxItem(tabTitle: String, title: String, functionId: RecordId)

}

trait ToolboxViewPresenter extends ViewPresenter
