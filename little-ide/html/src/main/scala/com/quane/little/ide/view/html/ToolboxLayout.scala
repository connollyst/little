package com.quane.little.ide.view.html

import com.quane.little.ide.view.ToolboxView
import com.vaadin.ui.Accordion
import scala.collection.mutable
import com.quane.little.data.model.RecordId
import com.vaadin.ui.TabSheet.Tab
import com.quane.little.data.model.CodeSubcategory.CodeSubcategory
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
  setPrimaryStyleName(ToolboxLayout.StyleAccordion)

  override def setSelectedTab(subcategory: CodeSubcategory) =
    setSelectedTab(getTab(subcategory))

  override def createToolboxTab(subcategory: CodeSubcategory) =
    addTab(subcategory, new ToolboxSectionComponent())

  override def createToolboxItem(category: CodeCategory, subcategory: CodeSubcategory, title: String, functionId: RecordId) =
    addToolboxItem(subcategory, new ToolboxItemComponent(title, category, functionId))

  private def addToolboxItem(subcategory: CodeSubcategory, item: ToolboxItemComponent) = {
    val tab = getTab(subcategory)
    val content = getTabContents(subcategory)
    content.add(item)
    tab.setCaption(subcategory.toString + " (" + content.count + ")")
  }


  private def addTab(subcategory: CodeSubcategory, tabContent: ToolboxSectionComponent): Unit = {
    val id = subcategory.toString
    tabContents += (id -> tabContent)
    addTab(tabContent, subcategory.toString)
  }

  private def getTab(subcategory: CodeSubcategory): Tab =
    getTab(getTabContents(subcategory))


  private def getTabContents(subcategory: CodeSubcategory): ToolboxSectionComponent =
    tabContents(subcategory.toString)

}