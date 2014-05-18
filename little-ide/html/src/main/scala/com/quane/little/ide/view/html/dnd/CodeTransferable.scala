package com.quane.little.ide.view.html.dnd

import java.util
import scala.collection.mutable
import scala.collection.JavaConversions._
import com.vaadin.event.Transferable
import com.quane.little.data.model.RecordId
import com.vaadin.ui.Component
import com.quane.little.data.model.CodeType._
import com.google.common.base.Objects

object CodeTransferable {
  val CODE_CATEGORY = "little-code-category"
  val CODE_ID = "little-code-id"
}

class CodeTransferable(source: Component, val category: CodeType, val codeId: RecordId) extends Transferable {

  private var data = mutable.Map[String, AnyRef](
    CodeTransferable.CODE_CATEGORY -> category,
    CodeTransferable.CODE_ID -> codeId
  )

  override def getSourceComponent = source

  override def setData(dataFlavor: String, value: AnyRef) = data += dataFlavor -> value

  override def getDataFlavors: util.Collection[String] = data.keys

  override def getData(dataFlavor: String): AnyRef = data(dataFlavor)

  override def toString: String =
    Objects.toStringHelper(getClass)
      .add("category", category)
      .add("id", codeId)
      .add("source", source)
      .toString
}