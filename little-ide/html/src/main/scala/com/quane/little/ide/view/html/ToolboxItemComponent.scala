package com.quane.little.ide.view.html

import _root_.java.util
import com.vaadin.event.Transferable
import com.vaadin.ui.{Label, DragAndDropWrapper}
import scala.collection.JavaConversions._
import com.quane.little.data.model.RecordId
import scala.collection.mutable

class ToolboxItemComponent(val name: String, val functionId: RecordId)
  extends DragAndDropWrapper(new Label(name)) {

  setDragStartMode(DragAndDropWrapper.DragStartMode.WRAPPER)
  setSizeUndefined()

  // TODO should look like the real expression
  // TODO should carry the function id

  override def getTransferable(rawVariables: util.Map[String, AnyRef]) =
    new ExpressionTransferable(this)

}

object ExpressionTransferable {
  val FUNCTION_ID = "little-function-id"
}

class ExpressionTransferable(source: ToolboxItemComponent) extends Transferable {

  private var data = mutable.Map[String, AnyRef](
    ExpressionTransferable.FUNCTION_ID -> source.functionId
  )

  override def getSourceComponent = source

  override def setData(dataFlavor: String, value: AnyRef) = data += dataFlavor -> value

  override def getDataFlavors: util.Collection[String] = data.keys

  override def getData(dataFlavor: String): AnyRef = data(dataFlavor)

  def getFunctionId: RecordId = source.functionId

}