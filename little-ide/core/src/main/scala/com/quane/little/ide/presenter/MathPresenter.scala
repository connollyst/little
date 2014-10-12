package com.quane.little.ide.presenter

import com.quane.little.ide.view.{MathViewPresenter, MathView}
import com.quane.little.language.data.ValueType
import com.quane.little.language.data.ValueType.ValueType
import com.quane.little.language.data.ValueType.ValueType
import com.quane.little.language.math.BasicMath
import com.escalatesoft.subcut.inject.{Injectable, BindingModule}
import com.quane.little.data.service.{FunctionService, CodeService}
import com.quane.little.language.math.BasicMathOperation
import com.quane.little.language.math.BasicMathOperation.BasicMathOperation

class MathPresenter[V <: MathView](protected val view: V)(implicit val bindingModule: BindingModule)
  extends MathViewPresenter with PresenterOfLeftAndRightExpressions with Injectable {

  protected val presenterFactory = inject[PresenterFactory]
  protected val codeService = inject[CodeService]

  private var _operation = BasicMathOperation.Add

  view.registerViewPresenter(this)
  view.setOperation(_operation)

  private[presenter] def initialize(math: BasicMath): MathPresenter[V] = {
    operation = math.operation
    left = math.l
    right = math.r
    this
  }

  private[presenter] def operation: BasicMathOperation = _operation

  private[presenter] def operation_=(o: BasicMathOperation): Unit = {
    _operation = o
    view.setOperation(o)
  }

  override def acceptedValueType: ValueType = ValueType.Integer // TODO what about Double?

  override def onOperationChange(operation: BasicMathOperation) =
    _operation = operation

  /** Compile the presented data to a [[com.quane.little.language.math.BasicMath]]
    * expression.
    *
    * @return the compiled expression
    */
  override def compile(): BasicMath = BasicMath(left.compile(), operation, right.compile())

}