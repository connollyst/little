package com.quane.little.ide.view.html.dnd

import java.util
import scala.collection.mutable
import scala.collection.JavaConversions._
import com.vaadin.event.Transferable
import com.quane.little.data.model.RecordId
import com.vaadin.ui.Component

object FunctionTransferable {
  val FUNCTION_ID = "little-function-id"
}

/**
 *
 *
 * @author Sean Connolly
 */
class FunctionTransferable(source: Component, val functionId: RecordId) extends Transferable {

  private var data = mutable.Map[String, AnyRef](
    FunctionTransferable.FUNCTION_ID -> functionId
  )

  override def getSourceComponent = source

  override def setData(dataFlavor: String, value: AnyRef) = data += dataFlavor -> value

  override def getDataFlavors: util.Collection[String] = data.keys

  override def getData(dataFlavor: String): AnyRef = data(dataFlavor)

}