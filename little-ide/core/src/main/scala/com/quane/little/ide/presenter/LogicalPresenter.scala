package com.quane.little.ide.presenter

import com.quane.little.ide.view.{LogicalViewPresenter, LogicalView}
import com.quane.little.language.Logical
import com.escalatesoft.subcut.inject.{Injectable, BindingModule}
import com.quane.little.language.LogicalOperation.LogicalOperation
import com.quane.little.language.LogicalOperation
import com.quane.little.data.service.{FunctionService, ExpressionService}

class LogicalPresenter[V <: LogicalView](protected val view: V)(implicit val bindingModule: BindingModule)
  extends LogicalViewPresenter
  with PresenterOfLeftAndRightExpressions
  with Injectable {

  protected val presenterFactory = inject[PresenterFactory]
  protected val expressionService = inject[ExpressionService]
  protected val functionService = inject[FunctionService]

  private var _operation = LogicalOperation.Equals

  view.registerViewPresenter(this)

  private[presenter] def initialize(logical: Logical): LogicalPresenter[V] = {
    operation = logical.operation
    left = logical.left
    right = logical.right
    this
  }

  private[presenter] def operation: LogicalOperation = _operation

  private[presenter] def operation_=(o: LogicalOperation): Unit = {
    _operation = o
    view.setOperation(o)
  }

  override def onOperationChange(operation: LogicalOperation) =
    _operation = operation

  /** Compile the presented data to a
    * [[com.quane.little.language.Logical]].
    *
    * @return the compiled logical operation
    */
  override def compile() = new Logical(left.compile(), operation, right.compile())

}
