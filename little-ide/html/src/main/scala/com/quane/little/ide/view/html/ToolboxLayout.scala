package com.quane.little.ide.view.html

import java.util

import com.porotype.iconfont.FontAwesome.{Icon, IconVariant}
import com.quane.little.data.model.CodeCategory.CodeCategory
import com.quane.little.data.model.{FunctionId, Id, PrimitiveId}
import com.quane.little.ide.view.ToolboxView
import com.quane.little.ide.view.html.dnd.CodeTransferable
import com.quane.vaadin.scala.VaadinMixin
import com.vaadin.ui.Button.{ClickEvent, ClickListener}
import com.vaadin.ui.TabSheet.Tab
import com.vaadin.ui._

import scala.collection.mutable

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

  override def setSelectedTab(category: CodeCategory) =
    setSelectedTab(getTab(category))

  override def createToolboxTab(category: CodeCategory) = {
    addTab(category, new ToolboxSectionComponent())
    getTabContents(category).add(new ToolboxNewButton(this, category))
  }

  override def createToolboxItem(category: CodeCategory, title: String, codeId: Id) =
    addToolboxItem(category, new ToolboxItem(this, title, codeId))

  private def addToolboxItem(category: CodeCategory, item: ToolboxItem) = {
    val tab = getTab(category)
    val content = getTabContents(category)
    content.add(item)
    tab.setCaption(category.toString + " (" + content.count + ")")
  }

  private def addTab(category: CodeCategory, tabContent: ToolboxSectionComponent): Tab = {
    val id = category.toString
    tabContents += (id -> tabContent)
    addTab(tabContent, category.toString)
  }

  private def getTab(category: CodeCategory): Tab =
    getTab(getTabContents(category))


  private def getTabContents(category: CodeCategory): ToolboxSectionComponent =
    tabContents(category.toString)

}

/** An item in the toolbox.
  *
  * @param view the root toolbox view
  * @param title the item title
  * @param codeId the id for the represented code
  */
class ToolboxItem(view: ToolboxLayout, title: String, codeId: Id)
  extends DragAndDropWrapper(new ToolboxItemContent(view, title, codeId)) {

  setSizeUndefined()
  setStyleName(CodeLayout.Style)
  setDragStartMode(DragAndDropWrapper.DragStartMode.WRAPPER)

  override def getTransferable(rawVariables: util.Map[String, AnyRef]) =
    new CodeTransferable(this, codeId)

}

private class ToolboxItemContent(view: ToolboxLayout, title: String, id: Id)
  extends HorizontalLayout
  with VaadinMixin {

  setSpacing(true)

  add(new Label(title))
  id match {
    case f: FunctionId => add(new ToolboxEditButton(view, f))
    case p: PrimitiveId => // primitives can't be edited
    case _ => throw new IllegalArgumentException(
      "Cannot create " + classOf[ToolboxItemContent] + " for " + id.getClass
    )
  }

}

private class ToolboxNewButton(view: ToolboxLayout, category: CodeCategory)
  extends NativeButton {

  setCaption(Icon.plus.variant(IconVariant.SIZE_LARGE))
  setHtmlContentAllowed(true)
  addClickListener(new ClickListener {
    def buttonClick(event: ClickEvent) = view.presenter.requestNewFunctionDefinition(category)
  })

}

private class ToolboxEditButton(view: ToolboxLayout, id: FunctionId)
  extends NativeButton {

  setCaption(Icon.edit.variant(IconVariant.SIZE_LARGE))
  setHtmlContentAllowed(true)
  addClickListener(new ClickListener {
    def buttonClick(event: ClickEvent) = view.presenter.openFunctionDefinition(id)
  })

}