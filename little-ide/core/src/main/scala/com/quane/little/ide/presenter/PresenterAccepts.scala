package com.quane.little.ide.presenter

import com.quane.little.data.model.{PrimitiveRecord, RecordId}
import com.quane.little.ide.view.{View, ViewPresenter}
import com.quane.little.language.data.ValueType
import com.quane.little.language.data.ValueType._

object PresenterAccepts {

  // TODO do we need the manifest check?
  def accepts[P <: ViewPresenter](view: View[_ <: P], record: PrimitiveRecord)(implicit m: Manifest[P]): Boolean =
    if (m <:< manifest[PresenterAcceptsCode]) {
      val presenter = view.presenter.asInstanceOf[PresenterAcceptsCode]
      val acceptedType = presenter.acceptedValueType;
      println(acceptedType)
      val returnType = record.expression.returnType;
      println(returnType)
      acceptedType match {
        case ValueType.Anything => true
        case ValueType.Something => returnType != ValueType.Nothing
        case _ => acceptedType == returnType
      }
    } else {
      false
    }

}

sealed trait PresenterAccepts

trait PresenterAcceptsCode extends PresenterAccepts {

  def acceptedValueType: ValueType

  def requestAddCode(id: RecordId, index: Int): Unit

}

trait PresenterAcceptsFunctionDefinition extends PresenterAccepts {

  def requestAddFunctionDefinition(id: RecordId, index: Int): Unit

  def requestAddBlankFunctionDefinition(index: Int): Unit

}

trait PresenterAcceptsEventListener extends PresenterAccepts {

  def requestAddEventListener(id: RecordId, index: Int): Unit

  def requestAddBlankEventListener(index: Int): Unit

}