package com.quane.little.ide.view.html

import com.quane.little.ide.view.ToolboxView
import com.vaadin.ui.Accordion
import scala.collection.mutable
import com.quane.little.data.model.RecordId
import com.vaadin.ui.TabSheet.Tab
import com.quane.little.data.model.FunctionCategory.FunctionCategory
import com.quane.little.data.model.CodeCategory.CodeCategory

object ToolboxLayout {
  val Style = "l-toolbox"
  val StyleAccordion = Style + "-accordion"
}

class ToolboxLayout
  extends Accordion
  with ToolboxView
  with RemovableComponent {

  private val tabContents: mutable.Map[String, ToolboxSectionComponent] = mutable.Map()

  setSizeFull()
  //  setStyleName(ToolboxLayout.Style)
  setPrimaryStyleName(ToolboxLayout.StyleAccordion)

  override def createToolboxTab(category: CodeCategory) =
    addTab(category, new ToolboxSectionComponent())

  override def createToolboxTab(category: CodeCategory, subcategory: FunctionCategory) =
    addTab(category, subcategory, new ToolboxSectionComponent())

  override def createToolboxItem(category: CodeCategory, title: String, functionId: RecordId) =
    addToolboxItem(category, new ToolboxItemComponent(title, category, functionId))

  override def createToolboxItem(category: CodeCategory, subcategory: FunctionCategory, title: String, functionId: RecordId) =
    addToolboxItem(category, subcategory, new ToolboxItemComponent(title, category, functionId))

  private def addToolboxItem(category: CodeCategory, item: ToolboxItemComponent) = {
    val tab = getTab(category)
    val content = getTabContents(category)
    content.add(item)
    tab.setCaption(category.toString + " (" + content.count + ")")
  }

  private def addToolboxItem(category: CodeCategory, subcategory: FunctionCategory, item: ToolboxItemComponent) = {
    val tab = getTab(category, subcategory)
    val content = getTabContents(category, subcategory)
    content.add(item)
    tab.setCaption(subcategory.toString + " (" + content.count + ")")
  }

  private def addTab(category: CodeCategory, tabContent: ToolboxSectionComponent): Unit = {
    val id = category.toString
    tabContents += (id -> tabContent)
    addTab(tabContent, category.toString)
  }

  private def addTab(category: CodeCategory, subcategory: FunctionCategory, tabContent: ToolboxSectionComponent): Unit = {
    val id = category.toString + subcategory.toString
    tabContents += (id -> tabContent)
    addTab(tabContent, subcategory.toString)
  }

  private def getTab(category: CodeCategory): Tab =
    getTab(getTabContents(category))

  private def getTab(category: CodeCategory, subcategory: FunctionCategory): Tab =
    getTab(getTabContents(category, subcategory))

  private def getTabContents(category: CodeCategory): ToolboxSectionComponent =
    tabContents(category.toString)

  private def getTabContents(category: CodeCategory, subcategory: FunctionCategory): ToolboxSectionComponent =
    tabContents(category.toString + subcategory.toString)

}