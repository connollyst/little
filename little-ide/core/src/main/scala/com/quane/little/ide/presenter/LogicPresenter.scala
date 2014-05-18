package com.quane.little.ide.presenter

import com.quane.little.ide.view.{LogicViewPresenter, LogicView}
import com.quane.little.language.Logic
import com.escalatesoft.subcut.inject.{Injectable, BindingModule}
import com.quane.little.language.LogicOperation.LogicOperation
import com.quane.little.language.LogicOperation
import com.quane.little.data.service.{FunctionService, ExpressionService}

class LogicPresenter[V <: LogicView](protected val view: V)(implicit val bindingModule: BindingModule)
  extends LogicViewPresenter
  with PresenterOfLeftAndRightExpressions
  with Injectable {

  protected val presenterFactory = inject[PresenterFactory]
  protected val expressionService = inject[ExpressionService]
  protected val functionService = inject[FunctionService]

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

  override def onOperationChange(operation: LogicOperation) =
    _operation = operation

  /** Compile the presented data to a [[com.quane.little.language.Logic]].
    *
    * @return the compiled logical operation
    */
  override def compile() = new Logic(left.compile(), operation, right.compile())

}
