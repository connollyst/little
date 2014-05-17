package com.quane.little.ide.presenter

import com.quane.little.ide.view.{LogicalViewPresenter, LogicalView}
import com.quane.little.language.LogicalOperation
import com.escalatesoft.subcut.inject.{Injectable, BindingModule}
import com.quane.little.language.EvaluationOperator.EvaluationOperator
import com.quane.little.language.EvaluationOperator
import com.quane.little.data.service.{FunctionService, ExpressionService}

class LogicalPresenter[V <: LogicalView](protected val view: V)(implicit val bindingModule: BindingModule)
  extends LogicalViewPresenter
  with HasLeftAndRightExpressions
  with Injectable {

  protected val presenterFactory = inject[PresenterFactory]
  protected val expressionService = inject[ExpressionService]
  protected val functionService = inject[FunctionService]

  private var _operation = EvaluationOperator.Equals

  private[presenter] def initialize(logical: LogicalOperation): LogicalPresenter[V] = {
    operation = logical.operator
    left = logical.left
    right = logical.right
    this
  }

  private[presenter] def operation: EvaluationOperator = _operation

  private[presenter] def operation_=(o: EvaluationOperator): Unit = {
    _operation = o
    view.setOperation(o)
  }

  override def onOperationChange(operation: EvaluationOperator) =
    _operation = operation

  /** Compile the presented data to a
    * [[com.quane.little.language.LogicalOperation]].
    *
    * @return the compiled logical operation
    */
  override def compile() = new LogicalOperation(left.compile(), operation, right.compile())

}
