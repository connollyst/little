package com.quane.little.ide.view.html

import _root_.java.util
import com.vaadin.ui.{Label, DragAndDropWrapper}
import com.quane.little.data.model.RecordId
import com.quane.little.ide.view.html.dnd.FunctionTransferable

class ToolboxItemComponent(val name: String, val functionId: RecordId)
  extends DragAndDropWrapper(new Label(name)) {

  setSizeUndefined()
  setStyleName(ExpressionLayout.Style)
  setDragStartMode(DragAndDropWrapper.DragStartMode.WRAPPER)

  // TODO should look like the real expression

  override def getTransferable(rawVariables: util.Map[String, AnyRef]) =
    new FunctionTransferable(this, functionId)

}
