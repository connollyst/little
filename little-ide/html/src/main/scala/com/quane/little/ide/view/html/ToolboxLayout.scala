package com.quane.little.ide.view.html

import com.quane.little.ide.view.ToolboxView
import com.vaadin.ui._
import scala.collection.mutable
import com.quane.little.data.model.{CodeCategory, RecordId}
import com.quane.little.data.model.CodeSubcategory.CodeSubcategory
import java.util
import com.quane.little.ide.view.html.dnd.CodeTransferable
import com.quane.little.data.model.CodeCategory.CodeCategory
import com.porotype.iconfont.FontAwesome.{IconVariant, Icon}
import com.vaadin.ui.Button.{ClickEvent, ClickListener}
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
    addToolboxItem(subcategory, new ToolboxItem(this, title, category, functionId))

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
  * @param view the root toolbox view
  * @param title the item title
  * @param category the code category
  * @param codeId the id for the represented code
  */
class ToolboxItem(view: ToolboxLayout, title: String, category: CodeCategory, codeId: RecordId)
  extends DragAndDropWrapper(new ToolboxItemContent(view, title, category, codeId)) {

  setSizeUndefined()
  // TODO should look something like the real expression/statement
  setStyleName(ExpressionLayout.Style)
  setDragStartMode(DragAndDropWrapper.DragStartMode.WRAPPER)

  override def getTransferable(rawVariables: util.Map[String, AnyRef]) =
    new CodeTransferable(this, category, codeId)

}

private class ToolboxItemContent(view: ToolboxLayout, title: String, category: CodeCategory, codeId: RecordId)
  extends HorizontalLayout {

  setSpacing(true)

  addComponent(new Label(title))

  // If this is a function, add a button for editing it
  if (category.equals(CodeCategory.Function)) {
    val button = new NativeButton(Icon.edit.variant(IconVariant.SIZE_LARGE),
      new ClickListener() {
        override def buttonClick(event: ClickEvent) =
          view.presenter.openFunctionDefinition(codeId)
      }
    )
    button.setHtmlContentAllowed(true)
    addComponent(button)
  }

}
