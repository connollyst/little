package com.quane.little.ide.presenter

import com.escalatesoft.subcut.inject.{BindingModule, Injectable}
import com.quane.little.data.service.{CodeService, FunctionService}
import com.quane.little.ide.view.{LogicView, LogicViewPresenter}
import com.quane.little.language.LogicOperation.LogicOperation
import com.quane.little.language.data.ValueType
import com.quane.little.language.data.ValueType.ValueType
import com.quane.little.language.{Logic, LogicOperation}

class LogicPresenter[V <: LogicView](protected val view: V)(implicit val bindingModule: BindingModule)
  extends LogicViewPresenter with PresenterOfLeftAndRightExpressions with Injectable {

  protected val presenterFactory = inject[PresenterFactory]
  protected val codeService = inject[CodeService]

  private var _operation = LogicOperation.Equals

  view.registerViewPresenter(this)

  private[presenter] def initialize(logic: Logic): LogicPresenter[V] = {
    operation = logic.operation
    left = logic.left
    right = logic.right
    this
  }

  private[presenter] def operation: LogicOperation = _operation

  private[presenter] def operation_=(o: LogicOperation): Unit = {
    _operation = o
    view.setOperation(o)
  }

  /** Returns the accepted value type for this expression.<br/>
    * Note: any value type is accepted for both the left or right arguments,
    * but they need to be the same type.
    *
    * @return the accepted value type
    */
  override def acceptedValueType: ValueType = ValueType.Anything

  override def onOperationChange(operation: LogicOperation) =
    _operation = operation

  /** Compile the presented data to a [[com.quane.little.language.Logic]].
    *
    * @return the compiled logical operation
    */
  override def compile() = new Logic(left.compile(), operation, right.compile())

}
