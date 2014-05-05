package com.quane.little.ide.view.html

import com.quane.little.ide.view.ToolboxView
import com.vaadin.ui.Accordion
import scala.collection.mutable
import com.quane.little.data.model.RecordId

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

  override def createToolboxTab(title: String) = {
    val tabContent = new ToolboxSectionComponent()
    tabContents += (title -> tabContent)
    addTab(tabContent, title)
  }

  override def createToolboxItem(tabTitle: String, title: String, functionId: RecordId) =
    tabContents(tabTitle).addComponent(new ToolboxItemComponent(title, functionId))

}