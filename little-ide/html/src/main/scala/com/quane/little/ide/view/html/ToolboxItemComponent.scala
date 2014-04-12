package com.quane.little.ide.view.html

import _root_.java.util
import com.quane.little.language.data.Value
import com.vaadin.event.Transferable
import com.vaadin.ui.{Component, Label, DragAndDropWrapper}
import scala.collection.JavaConversions._

class ToolboxItemComponent(name: String)
  extends DragAndDropWrapper(new Label(name)) {

  setDragStartMode(DragAndDropWrapper.DragStartMode.WRAPPER)
  setSizeUndefined()


  // TODO should look like the real expression
  // TODO should carry the real expression as d&d payload


  //  def getStep: View[_] = {
  //    component
  //  }
  override def getTransferable(rawVariables: util.Map[String, AnyRef]) = {
    println("Getting ToolboxItem transferable w/ raws: " + rawVariables.toString)
    new ExpressionTransferable(this)
  }

}

class ExpressionTransferable(source: Component) extends Transferable {

  println("Creating new ExpressionTransferable..")

  override def getSourceComponent = source

  override def setData(dataFlavor: String, value: scala.Any) =
    println("Setting data: " + dataFlavor + " -> " + value)

  override def getDataFlavors: util.Collection[String] = List("little-expression")

  override def getData(dataFlavor: String): AnyRef = {
    println("Getting data for " + dataFlavor)
    dataFlavor match {
      case "little-expression" => Value(1)
      case _ => None
    }
  }

}