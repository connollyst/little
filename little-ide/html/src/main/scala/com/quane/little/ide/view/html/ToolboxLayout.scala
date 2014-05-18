package com.quane.little.ide.view.html

import com.quane.little.ide.view.ToolboxView
import com.vaadin.ui.{Label, DragAndDropWrapper, Accordion}
import scala.collection.mutable
import com.quane.little.data.model.RecordId
import com.quane.little.data.model.CodeSubcategory.CodeSubcategory
import com.quane.little.data.model.CodeCategory.CodeCategory
import java.util
import com.quane.little.ide.view.html.dnd.CodeTransferable
import com.vaadin.ui.TabSheet.Tab

object ToolboxLayout {
  val Style = "l-toolbox"
  val StyleAccordion = Style + "-accordion"
}

/** An HTML layout view for the toolbox of code the user can work with.
  *
  * @author Sean Connolly
  */
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
    addToolboxItem(subcategory, new ToolboxItem(title, category, functionId))

  private def addToolboxItem(subcategory: CodeSubcategory, item: ToolboxItem) = {
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

/** An item in the toolbox.
  *
  * @param title the item title
  * @param category the code category
  * @param codeId the id for the represented code
  */
class ToolboxItem(val title: String, val category: CodeCategory, val codeId: RecordId)
  extends DragAndDropWrapper(new Label(title)) {

  setSizeUndefined()
  setStyleName(ExpressionLayout.Style)
  setDragStartMode(DragAndDropWrapper.DragStartMode.WRAPPER)

  // TODO should look something like the real expression/statement

  override def getTransferable(rawVariables: util.Map[String, AnyRef]) =
    new CodeTransferable(this, category, codeId)

}
