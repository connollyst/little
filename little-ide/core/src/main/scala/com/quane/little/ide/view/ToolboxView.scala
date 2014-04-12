package com.quane.little.ide.view

import com.quane.little.language.Expression


trait ToolboxView extends View[ToolboxViewPresenter] {

  def createToolboxTab(title: String)

  def createToolboxItem(tabTitle: String, title: String, expression: Expression)

}

trait ToolboxViewPresenter extends ViewPresenter
