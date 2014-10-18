package com.quane.little.ide.view.html.dnd

import java.util

import com.google.common.base.Objects
import com.quane.little.data.model.RecordId
import com.vaadin.event.Transferable
import com.vaadin.ui.Component

import scala.collection.JavaConversions._
import scala.collection.mutable

object IDETransferable {
  val CATEGORY = "little-code-category"
  val ID = "little-code-id"
}

sealed trait IDETransferable extends Transferable {

  def source: Component

  def id: RecordId

  val data = mutable.Map[String, AnyRef](IDETransferable.ID -> id)

  override def getSourceComponent = source

  override def setData(dataFlavor: String, value: AnyRef) = data += dataFlavor -> value

  override def getDataFlavors: util.Collection[String] = data.keys

  override def getData(dataFlavor: String): AnyRef = data(dataFlavor)

  override def toString: String =
    Objects.toStringHelper(getClass)
      .add("id", id)
      .add("source", source)
      .toString

}

class EventListenerTransferable(val source: Component, val id: RecordId) extends IDETransferable

class CodeTransferable(val source: Component, val id: RecordId) extends IDETransferable