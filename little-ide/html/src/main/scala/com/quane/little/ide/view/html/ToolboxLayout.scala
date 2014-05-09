package com.quane.little.ide.view.html

import com.quane.little.ide.view.ToolboxView
import com.vaadin.ui.Accordion
import scala.collection.mutable
import com.quane.little.data.model.RecordId
import com.vaadin.ui.TabSheet.Tab

object ToolboxLayout {
  val Style = "l-toolbox"
}

class ToolboxLayout
  extends Accordion
  with ToolboxView
  with RemovableComponent {

  private val tabContents: mutable.Map[String, ToolboxSectionComponent] = mutable.Map()

  setSizeFull()
  setStyleName(ToolboxLayout.Style)

  override def createToolboxTab(category: String) = {
    val tabContent = new ToolboxSectionComponent()
    tabContents += (category -> tabContent)
    addTab(tabContent, category.toString)
  }

  override def createToolboxItem(category: String, title: String, functionId: RecordId) =
    addToolboxItem(category, new ToolboxItemComponent(title, functionId))

  private def addToolboxItem(category: String, item: ToolboxItemComponent) = {
    val tab = getTab(category)
    val content = tabContents(category)
    content.add(item)
    tab.setCaption(category.toString + " (" + content.count + ")")
  }

  private def getTab(category: String): Tab = getTab(tabContents(category))

}