package com.quane.little.ide.presenter

import com.quane.little.ide.view.{ExpressionViewPresenter, MathViewPresenter, MathView}
import com.quane.little.language.math.{AdditionOperation, BasicMathOperation, BasicMath}
import com.quane.little.data.model.RecordId
import com.quane.little.language.{FunctionReference, Getter, Expression}
import com.quane.little.language.data.Value
import com.escalatesoft.subcut.inject.{Injectable, BindingModule}
import com.quane.little.data.service.{FunctionService, ExpressionService}

/**
 *
 *
 * @author Sean Connolly
 */
class MathPresenter[V <: MathView](view: V)(implicit val bindingModule: BindingModule)
  extends MathViewPresenter
  with Injectable {

  private val presenterFactory = inject[PresenterFactory]
  private val expressionService = inject[ExpressionService]
  private val functionService = inject[FunctionService]

  private var _left: Option[ExpressionViewPresenter] = None
  private var _operation: BasicMathOperation = new AdditionOperation
  private var _right: Option[ExpressionViewPresenter] = None

  view.registerViewPresenter(this)
  view.setOperation(_operation)

  private[presenter] def initialize(math: BasicMath): MathPresenter[V] = {
    // operation = math.operation
    left = math.l
    right = math.r
    this
  }

  private[presenter] def operation: BasicMathOperation = _operation

  private[presenter] def operation_=(o: BasicMathOperation): Unit = {
    _operation = o
    view.setOperation(o)
  }

  private[presenter] def left: ExpressionViewPresenter = {
    _left match {
      case Some(e) => e
      case None => throw new IllegalAccessException("No left operand expression set.")
    }
  }

  private[presenter] def right: ExpressionViewPresenter = {
    _right match {
      case Some(e) => e
      case None => throw new IllegalAccessException("No right operand expression set.")
    }
  }

  /** Set the left operand expression.
    *
    * @param e the new left operand expression
    */
  private[presenter] def left_=(e: Expression): Unit = {
    val presenter =
      e match {
        case g: Getter =>
          presenterFactory.createGetPresenter(view.createLeftGetStatement()).initialize(g)
        case v: Value =>
          presenterFactory.createValuePresenter(view.createLeftValueStatement()).initialize(v)
        case f: FunctionReference =>
          presenterFactory.createFunctionReference(view.createLeftFunctionReference()).initialize(f)
        case _ => throw new IllegalArgumentException("Expression not supported: " + e)
      }
    // TODO skip if the presenter type hasn't changed (?)
    _left = Some(presenter)
  }

  /** Set the left operand expression.
    *
    * @param e the new left operand expression
    */
  private[presenter] def right_=(e: Expression): Unit = {
    val presenter =
      e match {
        case g: Getter =>
          presenterFactory.createGetPresenter(view.createRightGetStatement()).initialize(g)
        case v: Value =>
          presenterFactory.createValuePresenter(view.createRightValueStatement()).initialize(v)
        case f: FunctionReference =>
          presenterFactory.createFunctionReference(view.createRightFunctionReference()).initialize(f)
        case _ => throw new IllegalArgumentException("Expression not supported: " + e)
      }
    // TODO skip if the presenter type hasn't changed (?)
    _right = Some(presenter)
  }


  /** Called when the math operation changes in the view.
    *
    * @param operation the new math operation
    */
  override def onOperationChange(operation: BasicMathOperation) =
    _operation = operation

  override def requestAddExpression(id: RecordId, index: Int) =
    index match {
      case 0 => left = expressionService.findExpression(id)
      case 1 => right = expressionService.findExpression(id)
    }


  // TODO skip if already this function reference
  override def requestAddFunctionReference(id: RecordId, index: Int) =
    index match {
      case 0 => left = functionService.findReference(id)
      case 1 => right = functionService.findReference(id)
    }

  /** Compile the presented data to a [[com.quane.little.language.math.BasicMath]]
    * expression.
    *
    * @return the compiled expression
    */
  override def compile(): BasicMath = BasicMath(left.compile(), _operation, right.compile())

}
