package com.quane.little.ide.presenter

import com.quane.little.data.model.CodeCategory.CodeCategory
import com.quane.little.data.model.{ListenerId, FunctionId, Id}
import com.quane.little.ide.view.ViewPresenter
import com.quane.little.language.data.ValueType
import com.quane.little.language.data.ValueType._
import com.quane.little.tools.Logging

object PresenterAccepts extends Logging {

  def accepts[P <: PresenterAcceptsCode](presenter: P, returnType: ValueType): Boolean = {
    val acceptedType = presenter.acceptedValueType
    info("Checking if " + presenter + " accepts " + returnType + " in " + acceptedType + "?..")
    acceptedType match {
      case ValueType.Anything => true
      case ValueType.Something => returnType != ValueType.Nothing
      case _ => acceptedType == returnType
    }
  }

}

sealed trait PresenterAccepts extends ViewPresenter

trait PresenterAcceptsCode extends PresenterAccepts {

  def acceptedValueType: ValueType

  def requestAddCode(id: Id, index: Int): Unit

}

trait PresenterAcceptsFunctionDefinition extends PresenterAccepts {

  def requestAddFunctionDefinition(id: FunctionId, index: Int): Unit

  def requestAddBlankFunctionDefinition(category: CodeCategory, index: Int): Unit

}

trait PresenterAcceptsEventListener extends PresenterAccepts {

  def requestAddEventListener(id: ListenerId, index: Int): Unit

  def requestAddBlankEventListener(index: Int): Unit

}