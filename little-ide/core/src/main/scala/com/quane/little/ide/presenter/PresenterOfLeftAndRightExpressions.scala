package com.quane.little.ide.presenter

import com.quane.little.ide.view._
import com.quane.little.language.{LogicalOperation, FunctionReference, Getter, Expression}
import com.quane.little.language.data.Value
import com.quane.little.data.model.RecordId
import com.quane.little.data.service.{FunctionService, ExpressionService}
import com.quane.little.language.math.BasicMath
import scala.Some

/** A trait shared by presenters who manage two expressions, one being thought
  * of as the 'left' and the other as the 'right'. For example, a mathematical
  * operation with left and right operands.
  *
  * @author Sean Connolly
  */
trait PresenterOfLeftAndRightExpressions {

  private var _left: Option[ExpressionViewPresenter] = None
  private var _right: Option[ExpressionViewPresenter] = None

  protected def presenterFactory: PresenterFactory

  protected def expressionService: ExpressionService

  protected def functionService: FunctionService

  protected def view: ViewOfLeftAndRightExpressions

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
        case m: BasicMath =>
          presenterFactory.createMathPresenter(view.createLeftMath()).initialize(m)
        case g: Getter =>
          presenterFactory.createGetPresenter(view.createLeftGetStatement()).initialize(g)
        case v: Value =>
          presenterFactory.createValuePresenter(view.createLeftValueStatement()).initialize(v)
        case l: LogicalOperation =>
          presenterFactory.createLogicalPresenter(view.createLeftLogicOperation()).initialize(l)
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
        case m: BasicMath =>
          presenterFactory.createMathPresenter(view.createRightMath()).initialize(m)
        case g: Getter =>
          presenterFactory.createGetPresenter(view.createRightGetStatement()).initialize(g)
        case v: Value =>
          presenterFactory.createValuePresenter(view.createRightValueStatement()).initialize(v)
        case l: LogicalOperation =>
          presenterFactory.createLogicalPresenter(view.createRightLogicOperation()).initialize(l)
        case f: FunctionReference =>
          presenterFactory.createFunctionReference(view.createRightFunctionReference()).initialize(f)
        case _ => throw new IllegalArgumentException("Expression not supported: " + e)
      }
    // TODO skip if the presenter type hasn't changed (?)
    _right = Some(presenter)
  }


  // TODO skip if already this expression
  def requestAddExpression(id: RecordId, index: Int) =
    index match {
      case 0 => left = expressionService.findExpression(id)
      case 1 => right = expressionService.findExpression(id)
    }

  // TODO skip if already this function reference
  def requestAddFunctionReference(id: RecordId, index: Int) =
    index match {
      case 0 => left = functionService.findReference(id)
      case 1 => right = functionService.findReference(id)
    }

}
