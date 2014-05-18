package com.quane.little.ide.view

import com.quane.little.data.model.RecordId
import com.quane.little.data.model.CodeSubcategory._
import com.quane.little.data.model.CodeCategory.CodeCategory

trait ToolboxView extends View[ToolboxViewPresenter] {

  def setSelectedTab(subcategory: CodeSubcategory): Unit

  def createToolboxTab(subcategory: CodeSubcategory): Unit

  def createToolboxItem(category: CodeCategory, subcategory: CodeSubcategory, title: String, functionId: RecordId): Unit

}

trait ToolboxViewPresenter extends ViewPresenter {

  def requestNewFunctionDefinition()

  def openFunctionDefinition(functionId: RecordId): Unit

}
