package com.quane.little.ide.presenter

import com.quane.little.data.model.{FunctionRecord, PrimitiveRecord, RecordId}
import com.quane.little.ide.view.{View, ViewPresenter}
import com.quane.little.language.data.ValueType
import com.quane.little.language.data.ValueType._
import com.quane.little.tools.Logging

object PresenterAccepts extends Logging {

  def acceptsView[P <: PresenterAccepts](view: View[_ <: P], record: PrimitiveRecord)(implicit m: Manifest[P]): Boolean =
    acceptsPrimitive(view.presenter, record)

  def acceptsPrimitive[P <: PresenterAccepts](presenter: P, record: PrimitiveRecord)(implicit m: Manifest[P]): Boolean =
    acceptsReturnType(presenter, record.expression.returnType)

  def acceptsFunction[P <: PresenterAccepts](presenter: P, record: FunctionRecord)(implicit m: Manifest[P]): Boolean =
    acceptsReturnType(presenter, record.definition.returnType)

  // TODO do we need the manifest check?

  def acceptsReturnType[P <: ViewPresenter](presenter: P, returnType: ValueType)(implicit m: Manifest[P]): Boolean =
    if (m <:< manifest[PresenterAcceptsCode]) {
      val acceptedType = presenter.asInstanceOf[PresenterAcceptsCode].acceptedValueType
      info("Checking if " + presenter + " accepts " + returnType + " in " + acceptedType + "?..")
      acceptedType match {
        case ValueType.Anything => true
        case ValueType.Something => returnType != ValueType.Nothing
        case _ => acceptedType == returnType
      }
    } else {
      false
    }

}

sealed trait PresenterAccepts extends ViewPresenter

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